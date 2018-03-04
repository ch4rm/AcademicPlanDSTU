package org.scs.ap.database;

import java.sql.*;

public final class Database {
    private static String url = "jdbc:postgresql://127.0.0.1:5432/AcademicPlan?charSet=UNICODE";
    private static String name = "postgres";
    private static String password = "root";
    private static Connection connection;

    public static void setName(String name){
        Database.name = name;
    }

    public static Connection getConnection(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver не найден");
            new RuntimeException(e);
            return null;
        }
        System.out.println("PostgreSQL JDBC Driver успешно подключен");
        try {
            connection = DriverManager.getConnection(url, name, password);
        } catch (SQLException e) {
            System.out.println("Соединение провалено");
            new RuntimeException(e);
            return null;
        }
        if (connection != null) {
            System.out.println("Подключение установлено");
        } else {
            System.out.println("Подключение к базе данных завершилось неудачей");
        }
        return connection;
    }
}
