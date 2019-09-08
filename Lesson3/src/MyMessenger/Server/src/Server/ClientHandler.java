
package Server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

import static Server.AuthService.isNickInBd;
import static Server.Server.clients;

public class ClientHandler {


    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private Server server;
    private String str;
    private ArrayList blacklist;

    public String getNick() {
        return this.nick;
    }

    private String nick;

    private boolean isInChat(String str){
        Boolean res = false;
        for (ClientHandler o: clients) {

            if (o.nick.equals(str)){

                res = true;
                break;
            }
        }
        return res;
    }

    public ClientHandler(Server server, Socket socket){

        try {
            this.socket = socket;
            this.server = server;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {


                        while (true) {

                            str = in.readUTF();

                            if (str.startsWith("/auth")) {
                                String[] tokens = str.split(" ");
                                String newNick = null;

                                newNick = AuthService.getNickByLoginByPasswd(tokens[1], tokens[2]);

                                if (isInChat(newNick)) {
                                    newNick= null;
                                }

                                if (newNick != null) {
                                    nick = newNick;
                                    sendMsg("/authok " + nick);
                                    server.subscribe(ClientHandler.this);
                                    sendMsg("/sysinfo Welcome " + nick);
                                    sendMsg("/sysinfo /info - справочник команд");
                                    blacklist = new ArrayList();
                                    setBlacklist();
                                    // Создаем таблицу для хранения истории переписки или считываем историю
                                    setHistoryMsg();
                                    System.out.println("Клиент " + nick + " подключился");

                                    break;
                                   } else {
                                    sendMsg("/sysinfo Err: Неверный логин/пароль");
                                }

                            }

                        }


                        while (true) {

                            str = in.readUTF();
                            System.out.println("Client " + str);

                            if (str.startsWith("/chnick")) {
                              changeNick(str); //сменить ник
                            }
                            else

                            if (str.equals("/info")) {
                                info(); //вывести справочную информации
                              }
                            else
                            if (str.equals("/bl")) {
                                readBlackList(); // прочитать черный список

                            }
                            else
                            if (str.startsWith("/delbl")) { //удалить из черного списка
                                delBlackList(str);

                            }
                            else
                            if (str.startsWith("/addbl")) { //добавить в черный список
                                addBlackList(str);

                                      }

                            else
                           if (str.equals("/who")) { //кто на связи
                                whoOnline();

                            }

                           else
                          if (str.equals("/kolvok")) { // количество подключеный клиентов
                                out.writeUTF("/sysinfo Количество клиентов на связи : " + clients.size());
                            }
                          else
                          if (str.equals("/end")) { //отключиться
                                out.writeUTF("/serverClosed");
                                break;
                            }

                          else
                          if (str.startsWith("/w")) {  //отправить личное сообщение
                                personalMsg(str);
                            }
                          else
                          if (!str.isEmpty() && !str.startsWith("/")) {
                              server.broadcastMsg(nick + ": " + str);
                          }

                        }

                    } catch (IOException | SQLException e) {
                        //e.printStackTrace();
                        System.out.println("Нет связи с клиентом");

                    } finally {
                        CloseClient();

                    }
                }
            }).start();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setHistoryMsg() {
        // Создаем таблицу для хранения истории переписки или считываем историю
    }

    private void changeNick(String str) throws SQLException {
       // /chnick nick

        String[] strmes = str.split(" ", 2);

        if (strmes.length < 2) {
            sendMsg("/sysinfo Err: неверное значение /chnick ");
        } else {
            String newNick = strmes[1].replaceAll(" ", "");
            if (newNick.isEmpty()) {
                sendMsg("/sysinfo Err: неверное значение /chnick ");
            }
            else {
                // проверить по базе не зарегистрирован ли такой ник

                if (isNickInBd(newNick)) {
                    sendMsg("/sysinfo Err: Такой nick уже зарегистрирован (" + newNick+ ")");
                } else {
                    boolean res = AuthService.chageNickInBd(this.nick, newNick);
                    if (res){
                        this.nick = newNick;
                        sendMsg("/newNick " + newNick);
                    sendMsg("/sysinfo Ваш новый nick: (" + newNick+ ")");

                    }
                }



            }
        }
    }

    private void whoOnline() {
        sendMsg("/sysinfo On-line : ");

        int f=0;
        for (ClientHandler o: clients) {
            sendMsg("/sysinfo " + ++f + ". " + o.getNick());
        }
    }

    private void info() {
        sendMsg("/sysinfo \nСписок команд: \n");
        sendMsg("/sysinfo  /end - звершение сеанса \n");
        sendMsg("/sysinfo  /clear - очистить экран \n");
        sendMsg("/sysinfo  /w nick - личное сообщение nick \n");
        sendMsg("/sysinfo  /bl - черный список \n");
        sendMsg("/sysinfo  /addbl nick - добавить nick в черный список \n");
        sendMsg("/sysinfo  /history показать историю сообщений \n");
        sendMsg("/sysinfo  /clearhistory - очистить историю сообщений \n");
        sendMsg("/sysinfo  /delbl nick - удалить nick из черного списка \n");
        sendMsg("/sysinfo  /chnick nick - изменить nick \n");
        sendMsg("/sysinfo  /kolvok - количество онлайн \n");
        sendMsg("/sysinfo  /who - кто онлайн \n");
        sendMsg("/sysinfo  /info - справочник команд\n");
    }


    //Чтение черного списка
    private void readBlackList(){
        sendMsg("/sysinfo Черный список: " + blacklist.toString());

    }

    /*Удаление из черного списка*/
    private void delBlackList(String str) throws SQLException {

        String[] strmes = str.split(" ", 2);

        if (strmes.length < 2) {
            sendMsg("/sysinfo Err: неверное значение /delbl ");
        } else {

            if (isNickInBd(strmes[1])) {
                if (blacklist.contains(strmes[1])) {
                    boolean res = AuthService.delBlist(this.nick, strmes[1]);
                    if (res) {
                        sendMsg("/sysinfo  " + strmes[1] + " удален из черного списка");
                        setBlacklist();
                    } else {
                        sendMsg("/sysinfo Err: Запись из черного списка не удалена");
                    }
                } else {
                    sendMsg("/sysinfo Err: В черном списке нет " + strmes[1]);
                    readBlackList();
                }

            } else {
                sendMsg("/sysinfo Err: Нет такого пользователя : " + strmes[1]);
            }

        }
    }

    /*Добавление в черный список*/
    private void addBlackList(String str) throws SQLException {
        String[] strmes = str.split(" ", 2);
        if (strmes.length < 2) {
            sendMsg("/sysinfo Err: неверное значение /addbl ");
        }
        else {

        if (!this.nick.equals(strmes[1])) {
            if (isNickInBd(strmes[1])) {
                if (!blacklist.contains(strmes[1])) {
                    boolean res = AuthService.addBList(this.nick, strmes[1]);
                    if (res) {
                        sendMsg("/sysinfo Добавлен в черный список : " + strmes[1]);
                        setBlacklist();
                    } else {
                        sendMsg("/sysinfo Err: Запись в черный список не добавлена");
                    }
                } else {
                    sendMsg("/sysinfo Err: " + strmes[1] + " уже в черном списке");
                    readBlackList();
                }
            } else {
                sendMsg("/sysinfo Err: Нет такого пользователя : " + strmes[1]);
            }
        } else {
            sendMsg("/sysinfo Err: Запись в черный список не добавлена");
        }
    }
    }




    public void CloseClient(){
        try {
            in.close();
            out.close();
            out.close();
        } catch (IOException e) {


            System.out.println("Нет связи с клиентом");

        } finally {
            server.deleteClient(ClientHandler.this);
        }


        Thread.interrupted();

    }
    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
            CloseClient();
        }
    }


    public void setBlacklist() throws SQLException {
        this.blacklist = AuthService.readBlackList(this.nick);
    }

    private void personalMsg(String str){


        String[] strmes = str.split(" ", 3);

        if (strmes.length<3) {
            sendMsg("/sysinfo Err: неверное значение /w ");

        }
        else {
            String usernick = strmes[1];

            if (isInChat(usernick)) {

                try {
                    if (!AuthService.isAutorInBLUser(nick, usernick)){

                        sendMsg(nick + ": (personal for " + usernick + "): " + strmes[2]);
                        // отправляем сообщение strmes[2] клиенту с ником strmes[1]
                        server.sendMsgNick(strmes[1], nick + " (personal) : " + strmes[2] + "\n");

                    } else {
                        sendMsg("/sysinfo Err: Сообщение не отправлено");
                        sendMsg("/sysinfo Err: Вы в черном списке у " + strmes[1]);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                sendMsg("/sysinfo Err: (" + strmes[1] + ") не подключен.");
            }

        }


    }
}
