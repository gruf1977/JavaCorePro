import org.sqlite.JDBC;
import java.sql.*;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.awt.SystemColor.info;

public class Sclad {
    private static final String DB_PATH = "src\\main\\resources\\sclad.db";
    public static void main(String[] args) throws ClassNotFoundException {
        //Class.forName("org.sqlite.Driver");
        Connection conn;
        try {
            conn= DriverManager.getConnection(JDBC.PREFIX + DB_PATH);
            conn.setAutoCommit(false);
        Statement statement = conn.createStatement();
          //creatAndCleanTable(statement);  // создание и очистка таблицы
          //addProductsToDB(statement); //добавляем товар
            final Scanner sc=new Scanner(System.in);
            String value;
           info();


                    while (true) {
                        value=sc.nextLine();
                        if (value.equals("/end")) {
                            break;
                        }
                        if (value.startsWith("/цена")) {
                       findByCost(value, statement);
                        }

                        if (value.startsWith("/товарпоцене")) {
                            findByCostMaxMin(value, statement);
                        }

                        if (value.startsWith("/товар")) {
                            findNameTovar(value, statement);
                        }
                        if (value.startsWith("/сменитьцену")) {
                            changeCost(value, statement);
                        }
                        if (value.startsWith("/info")) {
                            info();
                        }

                    }


        statement.close();
        conn.setAutoCommit(true);
        conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private static void info() {
        System.out.println("/info - Справочник команд");
        System.out.println("/end - завершить программу");
        System.out.println("/товар ххх - найти товар по имени");
        System.out.println("/цена ххх - найти товар по цене");
        System.out.println("/сменитьцену yyyyy хххх - поменять цену у товара yyyyyy на новую цену ххххх ");
        System.out.println("/товарпоцене yyyy хххх - вывести товары с ценой в диапазоне от (min)yyyy до (max)хххх");
        System.out.println("Введите команду:");
    }

    private static void changeCost(String value, Statement statement) throws SQLException {
        // System.out.println("/сменитьцену yyyyy хххх - поменять цену у товара yyyyyy на новую цену ххххх ");
        String[] token = value.split(" ");
        if (token.length == 3 && token[0].equals("/сменитьцену")) {

            if (isNumeric(token[2])) {

                StringBuilder sb = new StringBuilder("UPDATE Products SET ");
                sb.append("Cost = " + token[2] + " ");
                sb.append("WHERE Title = \'" + token[1] + "\'; ");
                // System.out.println(sb.toString());

                int result = statement.executeUpdate(sb.toString());
                System.out.println("Изменено " + result + " позиций");
            } else {
                System.out.println("Неверное значение цены");
            }
        }
    }

    private static void findNameTovar(String value, Statement statement) throws SQLException {
       //System.out.println("/товар ххх - найти товар по имени");
        String[] token = value.split(" ");
        if (token.length==2 && token[0].equals("/товар")) {

                StringBuilder sb = new StringBuilder("SELECT * FROM Products where Title =");
                sb.append("\'" + token[1] + "\' ;");
                //System.out.println(sb.toString());

                ResultSet resultSet = statement.executeQuery(sb.toString());
                if (!resultSet.next()) {
                    System.out.println("Нет такого товара");
                } else {
                    do {
                        int id = resultSet.getInt("Id");
                        String prodId = resultSet.getString("ProdId");
                        String title = resultSet.getString("Title");
                        int cost = resultSet.getInt("Cost");
                        System.out.println(id + " " + prodId + " " + title + " " + cost);
                    } while (resultSet.next());

                }



        }

    }

    private static void findByCostMaxMin(String value, Statement statement) throws SQLException {
       // System.out.println("/товарпоцене yyyy хххх - вывести товары с ценой в диапазоне от yyyy до хххх");
        String[] token = value.split(" ");
        if (token.length==3 && token[0].equals("/товарпоцене")) {
            if (isNumeric(token[1]) && isNumeric(token[2])) {
                StringBuilder sb = new StringBuilder("SELECT * FROM Products where Cost BETWEEN ");
                sb.append(token[1] + " AND " + token[2]+ ";");

                //System.out.println(sb.toString());

                ResultSet resultSet = statement.executeQuery(sb.toString());

                while (resultSet.next()) {
                        int id = resultSet.getInt("Id");
                        String prodId = resultSet.getString("ProdId");
                        String title = resultSet.getString("Title");
                        int cost = resultSet.getInt("Cost");
                        System.out.println(id + " " + prodId + " " + title + " " + cost);
                    }

            } else {
                System.out.println("Не верное значение цены");
            }
        }

    }

    private static void findByCost(String value, Statement statement) throws SQLException {
        //System.out.println("/цена ххх - найти товар по цене");
        String[] token = value.split(" ");
if (token.length==2 && token[0].equals("/цена")) {
    if (isNumeric(token[1])) {
            StringBuilder sb = new StringBuilder("SELECT * FROM Products where Cost =");
            sb.append(token[1] + ";");
            //System.out.println(sb.toString());

            ResultSet resultSet = statement.executeQuery(sb.toString());
            if (!resultSet.next()) {
                System.out.println("Нет такого товара");
            } else {
                do {
                    int id = resultSet.getInt("Id");
                    String prodId = resultSet.getString("ProdId");
                    String title = resultSet.getString("Title");
                    int cost = resultSet.getInt("Cost");
                    System.out.println(id + " " + prodId + " " + title + " " + cost);
                } while (resultSet.next());

            }

    } else {
        System.out.println("Не верное значение цены");
    }
}
    }

    private static boolean isNumeric(String s) {
        boolean res =true;

        if (s.isEmpty()) {
                res =false;
        } else {
            Pattern p = Pattern.compile("[0-9]+");
            Matcher m = p.matcher(s);
            if (!m.matches()) {
                res =false;
            }
        }
        return  res;
    }

    static void creatAndCleanTable(Statement statement) throws SQLException {
        //Создание базы данных и таблицы
        boolean execute = statement.execute("CREATE TABLE IF NOT EXISTS Products (Id int , ProdId varchar(255), Title varchar(255), Cost Int);");
        //Очистка таблицы базы данных
        int result = statement.executeUpdate("DELETE FROM Products");
        //System.out.println(result);
    }
    static void addProductsToDB(Statement statement) throws SQLException {
        int res=0;
        for (int i=0; i<10000; i++){
            String prodId = UUID.randomUUID().toString(); // генерация id
            Random cost = new Random();

            StringBuilder sb = new StringBuilder("INSERT INTO Products (Id, ProdId, Title, Cost) VALUES (");
            sb.append( i+ ",");
            sb.append("\""+prodId+ "\",");
            sb.append("\"Товар" + i +  "\",");
            sb.append(cost.nextInt(100000) + ")");
           //System.out.println(sb.toString());
            int result = statement.executeUpdate(sb.toString());
            res = res+result;
        }
        System.out.println("Добавлено " + res + " позиций ");
    }


}
