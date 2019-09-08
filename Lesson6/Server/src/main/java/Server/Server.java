package Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final Logger LOGGER = LogManager.getLogger(Server.class);
    //массив подключенных клиентов
    public static  Vector<ClientHandler> clients;
    public static ExecutorService pullclients;
    final static int COUNT_CLIENTS = 3; // размер пула подключений
   public void subscribe(ClientHandler client) {
        clients.add(client);
    }
    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }
    public  void deleteClient(ClientHandler o) {

        clients.remove(o);
        LOGGER.info("Клиент удален");
        //System.out.println("Клиент удален");
    }


    public Server() {
        clients = new Vector<ClientHandler>();
        ServerSocket server = null;
        Socket socket = null;
            AuthService.connect();

        try {

            server = new ServerSocket(50105);
            pullclients = Executors.newFixedThreadPool(COUNT_CLIENTS);
            //System.out.println("Сервер запущен!");
            LOGGER.info("Сервер запущен!");
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
