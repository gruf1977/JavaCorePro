package Client;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private String logonick = "";
    boolean isconserv = true;
    List<String> historyArray= new ArrayList<>();

    @FXML
    TextField textField;
    @FXML
    Button btn1;
    @FXML
    Button btnexit;
    @FXML
    Button btnsvernut;
    @FXML
    HBox but1;
    @FXML
    HBox apperpannel;
    @FXML
    Button btn2;
    @FXML
    TextField loginfield;
    @FXML
    PasswordField passwordfield;
    @FXML
    Label logo;
    @FXML
    VBox VboxChat;
    @FXML
    ScrollPane ScrollPane1;


    private boolean isAuthorised;

    Socket socket; //инициализируем сокет
    DataInputStream in;
    DataOutputStream out;

    final String IP_ADRES = "localhost";
    final int PORT = 50105;

// Создает и печатае блоки основного экрана
    public void printMsg(String msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
               Label label = new Label(msg);
                label.setWrapText(true);
                HBox hBox = new HBox();
                hBox.setPrefWidth(ScrollPane1.getWidth() - 20);
                try {

                     if (msg.startsWith(logonick)) {
                        hBox.setAlignment(Pos.TOP_LEFT);
                        label.setId("sitemlabel");
                        String[] strmes = msg.split(":", 2);
                        label.setText("You : " + strmes[1]);
                         historyArray.add(label.getText());
                    }
                    else if (msg.startsWith("/sysinfo ")) {
                         String[] stroka = msg.split(" ", 2);
                         label.setId("sistemlabel");
                         hBox.setAlignment(Pos.TOP_CENTER);
                         label.setText(stroka[1]);
                     }

                     else if (msg.startsWith("/sysinfleft ")) {
                         String[] stroka = msg.split(" ", 2);
                         label.setId("sistemlabel");
                         hBox.setAlignment(Pos.TOP_LEFT);
                         label.setText(stroka[1]);
                     }
                    else {

                        Label label1 = new Label("   ");
                        hBox.getChildren().add(label1);
                        hBox.setAlignment(Pos.TOP_LEFT);


                        historyArray.add(label.getText());
                    }




                } catch (ArrayIndexOutOfBoundsException e) {
                  if (isconserv) {

                      label.setText("Err : неверный логин/пароль");
                      clientdisconect();
                  } else {
                      label.setText("Нет связи");
                    }

                    label.setId("sistemlabel");
                    hBox.setAlignment(Pos.TOP_CENTER);
                }



                if (!msg.equals("/serverClosed")) {
                    hBox.getChildren().add(label);


                    VboxChat.getChildren().add(hBox);


                }



            }
        });
    }

    public void setAuthorised(boolean isAuthorised) {
        this.isAuthorised = isAuthorised;

        if (!isAuthorised) {

            apperpannel.setVisible(true);
            apperpannel.setManaged(true);
            but1.setVisible(false);
            but1.setManaged(false);
            Platform.runLater(() -> logo.setText("MyChat"));


        } else {
            apperpannel.setVisible(false);
            apperpannel.setManaged(false);
            but1.setVisible(true);
            but1.setManaged(true);
            Platform.runLater(() -> logo.setText("MyChat - " + logonick));

        }
    }

    void vnutrinit() {

        try {
            socket = new Socket(IP_ADRES, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {

                @Override
                public void run() {

                    try {

                        while (true) {
                            if (!socket.isClosed()) {
                                String str = in.readUTF();
                                if (str.startsWith("/authok")) {
//
                                    String[] strlogo = str.split(" ");

                                    logonick = strlogo[1];

                                    setAuthorised(true);

                                    break;
                                } else {

                                    printMsg(str);

                                }
                            } else break;
                        }

                        while (true) {
                            if (!socket.isClosed()) {
                                String str = in.readUTF();
                                if (str.startsWith("/newNick")) {
                                    String[] strmes = str.split(" ", 2);
                                    logonick = strmes[1];
                                    Platform.runLater(() -> logo.setText("MyChat - " + logonick));
                                    str="";
                                }
                                    printMsg(str);
                                if (str.equals("/serverClosed")) {
                                    setAuthorised(false);
                                    clientdisconect();
                                }
                            } else break;
                        }


                    } catch (IOException e) {
                        //e.printStackTrace();
                        printMsg("Связь с сервером потеряна \n /end - завершить работу");

                    } finally {
                        clientdisconect();
                    }

                }

            }).start();


        } catch (IOException e) {
            isconserv = false;
            printMsg("Нет связи с сервером!");

        }
    }

    public void clear() {
        VboxChat.getChildren().clear();
        textField.clear();
        textField.requestFocus();
    }

    public void SendMsg() {

        if (textField.getText().equals("/clear")) {
            clear();
        } else

        if (textField.getText().equals("/end")) {
            try {
                        out.writeUTF("/end");

            } catch (IOException e) {
                if (!isconserv) {
                    printMsg("Нет связи ");
                }
            } finally {
                setAuthorised(false);
                clear();
                printMsg("/sysinfo Введите логин/пароль");

            }


        }
        if (isconserv) {

            try {

                out.writeUTF(textField.getText());

                textField.clear();
                textField.requestFocus();

            } catch (IOException e) {
             // printMsg("Нет связи ");


            }
        }

           //printMsg("Нет связи");
    }




    public void  sverprogram(){


            Stage stage = (Stage) btnsvernut.getScene().getWindow();
            stage.toBack();

        }


    public void closeprogram(){

    try {

        if (isconserv) {
            if (isAuthorised) {


                        out.writeUTF("/end");

                }

    }
    } catch (IOException e) {
       // e.printStackTrace();
    } finally {
        Stage stage = (Stage) btnexit.getScene().getWindow();

        stage.close();
    }

    }


    public void connect() {
        vnutrinit();
    }

    public void clientdisconect(){
    try

    {
        in.close();
        out.close();
        socket.close();
    } catch(IOException e)

    {
       // e.printStackTrace();
        printMsg("/sysinfo Нет связи с сервером!");

    }

}

    public void tryToAuth() {
if (socket == null || socket.isClosed()) {
connect();
}
        try {
            if (isconserv) {



                out.writeUTF("/auth " + loginfield.getText() + " " + passwordfield.getText().hashCode());
            }
        loginfield.clear();
        passwordfield.clear();
        } catch (IOException e) {
            //e.printStackTrace();
            printMsg("/sysinfo Нет связи с сервером!");

        }

    }


}

