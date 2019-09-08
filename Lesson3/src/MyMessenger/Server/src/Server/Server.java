package Server;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.Vector;

public class Server {
    //массив подключенных клиентов
    public static  Vector<ClientHandler> clients;

    public void subscribe(ClientHandler client) {
        clients.add(client);
    }

    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }
    public  void deleteClient(ClientHandler o) {

        clients.remove(o);
        System.out.println("Клиент удален");
    }


    public Server() {
        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;
            AuthService.connect();
        try {
            server = new ServerSocket(50105);

        System.out.println("Сервер запущен!");

            while (true) {
                socket = server.accept();
               new ClientHandler(this, socket);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        }


// рассылка всем клиентам
    public void broadcastMsg(String msg) {

        for (ClientHandler o: clients) {

            o.sendMsg(msg);
        }

    }

    //отправка персонального сообщения
    public void sendMsgNick(String namenick, String msg) {

        for (ClientHandler o: clients) {
            if (o.getNick().equals(namenick))
                o.sendMsg(msg);


        }

    }
}
