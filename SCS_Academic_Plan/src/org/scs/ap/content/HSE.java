package org.scs.ap.content;

import org.scs.ap.database.Database;
import org.scs.ap.view.Table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by User on 03.02.2018.
 */
public class HSE {
    private Database database;
    private String cycle="";
    private ArrayList<String> department = new ArrayList<>();

    public HSE(Database database){
        this.database = database;
        try {
            department();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getCycle() throws SQLException {
        String s = "";
        Table table = new Table();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM cycles WHERE key_cycle_pk = 1");
        while (resultSet.next()) {
            cycle = resultSet.getString("key_cycle_let");
            s = table.getField(cycle, "key_cycle_let", "width:80px; font-weight:bolder; font-size:14pt;")
                + table.getField(resultSet.getString("name_c"), "name_c", "width: 600px; font-weight:bolder; font-size:14pt;");
        }
        connection.close();
        statement.close();
        resultSet.close();
        return s;
    }

    public String getParts(int Part) throws SQLException{
        String s;
        Table table = new Table();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM parts WHERE key_cycle_fk = 1");
        ArrayList<String> keyPartsLet = new ArrayList<>();
        ArrayList<String> nameP = new ArrayList<>();
        while (resultSet.next()){
            keyPartsLet.add(resultSet.getString("key_parts_let"));
            nameP.add(resultSet.getString("name_p"));
        }
        s= cycle + "." + table.getField(keyPartsLet.get(Part), "key_parts_let"+Part, "width:50px; font-weight:bold;font-size:12pt;")
                + table.getField(nameP.get(Part), "name_p"+Part, "width:600px; font-weight:bold;font-size:12pt;");
        connection.close();
        statement.close();
        resultSet.close();
        return s;
    }

    public String getSubjects(int keyParts) throws SQLException{
        String s="";
        keyParts++;
        Table table = new Table();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM subjects WHERE key_parts_fk = " + keyParts);
        int i=0;
        while (resultSet.next()){
            s+="<tr><td>" + cycle + "." + resultSet.getString("key_subject") + "</td>"
                    + "<td>" + table.getField(resultSet.getString("name_s"), "name_s"+i,"width:300px")
                    + "</td><td>" + table.getField(department.get(Integer.parseInt(resultSet.getString("key_department_fk"))+1),
                    "key_department_fk"+i,"width:30px")
                    + "</td><td></td><td>" + table.getField(resultSet.getString("exams_s"), "exams_s"+i,"width:20px")
                    + "</td><td>" + table.getField(resultSet.getString("setoff_s"), "setoff_s"+i,"width:20px")
                    + "</td><td></td><td></td><td>" + table.getField(resultSet.getString("lect_s"), "lect_s"+i,"width:20px")
                    + "</td><td>" + table.getField(resultSet.getString("lab_s"), "lab_s"+i,"width:20px")
                    + "</td><td>" + table.getField(resultSet.getString("pract_s"), "pract_s"+i,"width:20px")
                    + "</td><td></td><td>" + table.getField(resultSet.getString("pract_s"), "ksr_s"+i,"width:20px")
                    + "</td><td>" + table.getField(resultSet.getString("pract_s"), "bsr_s"+i,"width:20px")
                    + "</td>";
            for(int j=0;j<32;j++)
                s+="<td></td>";
            s+="</tr>";
            i++;
        }
        connection.close();
        statement.close();
        resultSet.close();
        return s;
    }

    private void department() throws SQLException{
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM departments ORDER BY key_department_pk");
        while (resultSet.next())
            department.add(resultSet.getString("key_department"));
        connection.close();
        statement.close();
        resultSet.close();
    }

    public String headTable(){
        String s="";
        s+="<tr>";
        s+="<td colspan=\"4\">Аудиторные</td><td colspan=\"3\">Самост.</td>";
        for(int i=1; i<5;i++)
            s+="<td colspan=\"8\">"+ i + "курс</td>";
        s+="</tr><tr>";
        s+="<td rowspan=\"2\" class=\"rotatable\">Всего</td><td rowspan=\"2\" class=\"rotatable\">Лек</td>";
        s+="<td rowspan=\"2\" class=\"rotatable\">Лаб</td>";
        s+="<td rowspan=\"2\" class=\"rotatable\">Пр.</td><td rowspan=\"2\" class=\"rotatable\">Всего</td>";
        s+="<td rowspan=\"2\" class=\"rotatable\">КСР</td>";
        s+="<td rowspan=\"2\" class=\"rotatable\">БСР</td>";
        for(int i=1; i<9;i++)
            s+="<td colspan=\"4\">" + i + " (18)</td>";
        s+="</tr><tr style=\"height:60px\">";
        for(int i=1; i<9;i++) {
            s += "<td style=\"width:10px; height: 20px;\" class=\"rotatable\">Лек.</td>";
            s += "<td style=\"width:10px; height: 20px;\" class=\"rotatable\">Лаб.</td>";
            s += "<td style=\"width:10px; height: 20px;\" class=\"rotatable\">Пр.</td>";
            s += "<td style=\"width:10px; height: 20px;\" class=\"rotatable\">Сам.</td>";
        }
        s+="</tr>";
        return s;
    }
}
