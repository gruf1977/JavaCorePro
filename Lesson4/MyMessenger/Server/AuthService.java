package Server;
import java.sql.*;
import java.util.ArrayList;

public class AuthService {

    private static Connection connection;
    private static Statement stmt;



    /*Подключение к БД*/
    public static void connect(){
        try {
            Class.forName("org.sqlite.JDBC");
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:main.db");
                stmt = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*Отключение от БД*/
    public static  void disconnect(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /*Получение ник по логину и паролю*/
    public static String getNickByLoginByPasswd(String login, String passwd ) throws SQLException {
        String qry = String.format("SELECT nickname FROM main where login='%s' and password='%s'", login, passwd);
        ResultSet rs  = stmt.executeQuery(qry);
        if (rs.next()) {
            return rs.getString(1);

        }

        return null;
    }


    /*Проверка наличия записи в БД*/
    public static Boolean isNickInBd(String NickName) throws SQLException {
        boolean res=false;
        String qry = String.format("SELECT EXISTS(SELECT nickname FROM main WHERE nickname = '%s')", NickName);
        ResultSet rr  = stmt.executeQuery(qry);
        //System.out.println(rr.getInt(1));
        if (rr.getInt(1)==1){
            res = true;
        }

        return res;
    }
    // Проверка наличия пары в Черном списке
    public static Boolean isAutorInBLUser(String autor, String user) throws SQLException {
        boolean res=false;
        String qry = String.format("SELECT EXISTS(SELECT blackuser and autor FROM blaclist WHERE blackuser = '%s' and autor='%s')", autor, user);
        ResultSet rr  = stmt.executeQuery(qry);
        //System.out.println(rr.getInt(1));
        if (rr.getInt(1)==1){
            res = true;
        }

        return res;
    }

    /*Добавление записи в Черный список*/
    public static boolean addBList(String autor, String blackuser) throws SQLException {
        boolean res = false;
        String qry = String.format("INSERT INTO blaclist (autor, blackuser) VALUES ('%s', '%s')", autor, blackuser);
        int rs  = stmt.executeUpdate(qry);
        if (rs>0){
            res= true;
        }
        return res;
    }

    /*Удаление из БД*/
    public static boolean delBlist(String autor, String blackuser)throws SQLException{
        boolean res = false;
        String qry = String.format("DELETE FROM blaclist WHERE autor='%s' and blackuser='%s'", autor, blackuser);
        int rs  = stmt.executeUpdate(qry);

        if (rs>0){
            res= true;
        }
        return res;
    }

public static boolean chageNickInBd(String autor, String newNick)throws SQLException{
    boolean res = false;
    String qry = String.format("UPDATE blaclist SET blackuser='%s' WHERE blackuser='%s'", newNick, autor);
    int rs  = stmt.executeUpdate(qry);
    qry = String.format("UPDATE blaclist SET autor='%s' WHERE autor='%s'", newNick, autor);
    rs  = stmt.executeUpdate(qry);
    qry = String.format("UPDATE main SET nickname='%s' WHERE nickname='%s'", newNick, autor);
    rs  = stmt.executeUpdate(qry);
    if (rs>0){
        res= true;
    }
    return res;
}

    /*Чтение черного списка*/
    public static ArrayList readBlackList(String autor) throws SQLException {
        String qry = String.format("SELECT * FROM blaclist WHERE autor='%s'", autor);
        ResultSet rs  = stmt.executeQuery(qry);
        ArrayList BlackList = new ArrayList();
        // String BlackList[rs.getRow()];
        while (rs.next()) {
            BlackList.add(rs.getString("blackuser"));
        }


        return BlackList;
    }

}