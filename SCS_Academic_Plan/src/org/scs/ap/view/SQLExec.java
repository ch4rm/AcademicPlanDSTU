package org.scs.ap.view;

import org.scs.ap.database.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLExec {
    Database database;

    public SQLExec(Database database){
        this.database = database;
    }

    public void execute(String sql){
        Statement statement;
        ResultSet resultSet;
        Connection connection = database.getConnection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            resultSet.close();
            statement.close();
            connection.close();
        }catch (SQLException e){
            new RuntimeException(e);
        }
    }
}
