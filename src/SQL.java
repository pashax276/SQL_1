import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQL {
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_CREATE = "jdbc:mysql://localhost:3306/"; // 127.0.0.1:3306/mydb
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    static String DB_NAME;
    static Connection connection = null;
    static Statement statement = null;

    public static void main(String[] args) throws IOException {
        BufferedReader d = new BufferedReader(new InputStreamReader(System.in));

        try {
            while (true) {
                System.out.println("Нажмите:");
                System.out.println("1 - СОЗДАТЬ БД");
                System.out.println("2 - ПОДКЛЮЧИТЬСЯ И РЕДАКТИРОВАТЬ СУЩЕСТВУЮЩЮЮ БД");
                System.out.println("3 - УДАЛИТЬ");
               // System.out.println("4 - для вывода информации");
                System.out.println("что-то другое для выхода");

                String s = d.readLine();
                int ch = Integer.parseInt(s);

                switch (ch) {

                    case 1:
                        getCreateDB();
                        break;
                    case 2:
                        getConnectedDB();
                        break;
                    case 3:
                        dbDeleted();
                        break;
                    default:
                        break;
                }

                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void getConnectedDB() throws SQLException {

        BufferedReader db = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Для входа введите название базы данных: ");
        try {
            DB_NAME = db.readLine();

            Class.forName(DB_DRIVER);
            System.out.println("Connection to database " + DB_NAME.toUpperCase() + "...");
            connection = DriverManager.getConnection(DB_CREATE + DB_NAME, DB_USER, DB_PASSWORD);
            System.out.println("Connected to database " + DB_NAME.toUpperCase() + " successfully");

            dbTableCreating();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                se.getStackTrace();
            }// do nothing
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
    }

    private static void dbDeleted() throws SQLException {
        BufferedReader db = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Для удаления базы данных введите название: ");
        try {

            DB_NAME = db.readLine();

            Class.forName(DB_DRIVER);
            System.out.println("Connection to database " + DB_NAME.toUpperCase() + "...");
            connection = DriverManager.getConnection(DB_CREATE + DB_NAME, DB_USER, DB_PASSWORD);
            System.out.println("Connected to database " + DB_NAME.toUpperCase() + " successfully");

            System.out.println("Deleting database " + DB_NAME.toUpperCase() + " ...");
            statement = connection.createStatement();

            String sql = "DROP DATABASE " + DB_NAME;
            statement.executeUpdate(sql);
            System.out.println("Database " + DB_NAME.toUpperCase() + " deleted successfully...");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                se.getStackTrace();
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
    }

    private static void dbTableCreating() throws SQLException {

        System.out.println("Creating datatable " + DB_NAME.toUpperCase() + " ...");

        String createTableSQL = "CREATE TABLE " + DB_NAME + "(PRODUCT_ID INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "PRODUCT_NAME VARCHAR(30) NOT NULL," +
                "PRODUCT_MANUFACTURE CHAR(30) NOT NULL," +
                "PRODUCT_PRICE DECIMAL NOT NULL)";

        statement = connection.createStatement();
        statement.executeUpdate(createTableSQL);
        System.out.println("Connected to datatable " + DB_NAME.toUpperCase() + " successfully");
    }

    private static void getCreateDB() throws SQLException {
        BufferedReader db = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("-----------------------------------------------------");
        System.out.println("Введите название базы данных:");
        try {
            DB_NAME = db.readLine();

            Class.forName(DB_DRIVER);
            System.out.println("Connection to database...");
            connection = DriverManager.getConnection(DB_CREATE, DB_USER, DB_PASSWORD);

            System.out.println("Creating database " + DB_NAME.toUpperCase() + "...");
            statement = connection.createStatement();
            String creatingDB = "CREATE DATABASE " + DB_NAME;
            statement.executeUpdate(creatingDB);
            System.out.println("Database " + DB_NAME.toUpperCase() + " created successfully...");

            System.out.println("-----------------------------------------------------");

            getConnectedDB();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (statement != null)
                    connection.close();
            } catch (SQLException se) {
                se.getStackTrace();
            }// do nothing
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end t
    }

}
