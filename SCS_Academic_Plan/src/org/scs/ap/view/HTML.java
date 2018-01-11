package org.scs.ap.view;

import org.scs.ap.database.Database;

import java.sql.*;
import java.util.ArrayList;

public class HTML {
    private Database database;

    public HTML(){}

    public HTML(Database database){
        this.database = database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public String createTableSubjects(){
        Table table = new Table(100);
        ArrayList<String> head = new ArrayList<>();
        head.add("key_subject");
        head.add("key_ap_fk");
        head.add("key_cycle_fk");
        head.add("key_parts_fk");
        head.add("name_s");
        head.add("exams_s");
        head.add("setoff_s");
        head.add("lect_s");
        head.add("lab_s");
        head.add("pract_s");
        head.add("ksr_s");
        head.add("bsr_s");
        table.setHead(head);
        if(database == null){
            return "База данных не установлена";
        }
        Connection connection = database.getConnection();
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM subjects");
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            while (resultSet.next()){
                ArrayList<String> strings = new ArrayList<>();
                strings.add(resultSet.getString("key_subject"));
                strings.add(resultSet.getString("key_ap_fk"));
                strings.add(resultSet.getString("key_cycle_fk"));
                strings.add(resultSet.getString("key_parts_fk"));
                strings.add(resultSet.getString("name_s"));
                strings.add(resultSet.getString("exams_s"));
                strings.add(resultSet.getString("setoff_s"));
                strings.add(resultSet.getString("lect_s"));
                strings.add(resultSet.getString("lab_s"));
                strings.add(resultSet.getString("pract_s"));
                strings.add(resultSet.getString("ksr_s"));
                strings.add(resultSet.getString("bsr_s"));
            }
            resultSet.close();
            statement.close();
            connection.close();
        }catch (SQLException e){
            new RuntimeException(e);
        }
        return table.toString();
    }
}
