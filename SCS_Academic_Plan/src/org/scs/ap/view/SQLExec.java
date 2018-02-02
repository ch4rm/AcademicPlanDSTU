package org.scs.ap.view;

import org.scs.ap.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLExec {
    Database database;

    public SQLExec(Database database){
        this.database = database;
    }

    public String getElement(String base, String column, int pos){
        Statement statement;
        ResultSet resultSet;
        Connection connection = database.getConnection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM " + base);
            ArrayList<String> str = new ArrayList<>();
            while (resultSet.next())
                str.add(resultSet.getString(column));
            resultSet.close();
            statement.close();
            connection.close();
            return str.get(pos).toString();
        }catch (SQLException e){
            new RuntimeException(e);
        }
        return "error";
    }

    public void setElement(){
        Statement statement;
        ResultSet resultSet;
        Connection connection = database.getConnection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("UPDATE state_certification SET semester_sc = 8 WHERE key_sc_pk = 2");
            resultSet.close();
            statement.close();
            connection.close();
        }catch (SQLException e){
            new RuntimeException(e);
        }
    }
}
