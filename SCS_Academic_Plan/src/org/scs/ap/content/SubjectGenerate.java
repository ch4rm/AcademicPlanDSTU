package org.scs.ap.content;

import org.scs.ap.view.Config;
import org.scs.ap.view.Table;
import java.sql.*;
import java.util.ArrayList;

public class SubjectGenerate {
    private Table table;
    private String backColorHead;
    private Connection connection;
    private String cycle;
    private String keypart;
    private int cols;
    private int allSum[];
    private double allZE=0;
    private String leftFields="font-size: 11pt; padding-left: 2;";

    public SubjectGenerate(Connection connection, String backColorHead){
        table = new Table();
        this.backColorHead = backColorHead;
        this.connection = connection;
        allSum=new int[50];
    }

    /**
     * Имя цикла (Б1)
     */
    public String getCycle(int cyclePk) throws SQLException {
        String s = "";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT key_cycle_let, name_c FROM cycles WHERE key_cycle_pk = "+cyclePk);
        while(rs.next()){
            cycle = rs.getString("key_cycle_let");
            s = table.getField(cycle, "cycle-"+cyclePk,
                    "width:80px; font-weight:bolder; font-size:14pt; "+backColorHead, "cycle-head");
            s+= table.getField(rs.getString("name_c"), "namec-"+cyclePk,
                    "width: 600px; font-weight:bolder; font-size:14pt; "+backColorHead);
        }
        rs.close();
        statement.close();
        return s;
    }

    /**
     * Имя части (Б1.Б)
     */
    public String getParts(int part) throws SQLException{
        String s="";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT key_parts_let, name_p FROM parts WHERE key_parts_pk = "+part);
        while(rs.next()){
            keypart=rs.getString("key_parts_let");
            s= cycle + "." + table.getField(rs.getString("key_parts_let"), "keylet-"+part,
                    "width:50px; font-weight:bold;font-size:12pt; " + backColorHead)
                    + table.getField(rs.getString("name_p"), "namep-" + part,
                    "width:600px; font-weight:bold;font-size:12pt; " + backColorHead);
        }
        rs.close();
        statement.close();
        return s;
    }

    /**
     * Предметы (Б1.Б1 История)
     */
    public String getSubjects(int keyPart) throws SQLException{
        String s="";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM get_subjects WHERE key_parts_fk = "+keyPart+" ORDER BY key_subject");
        ResultSetMetaData rsmt = rs.getMetaData();
        cols = rsmt.getColumnCount();
        double summZE=0;
        int summ[] = new int[cols-6];
        while(rs.next()){
            int pk = rs.getInt(1);
            s+="<tr>";
            s+=genSubjectsCols(rs, pk, cols);
            s+="</tr>";
            summ(rs, summ);
            //з.е.
            summZE+=rs.getDouble(6);
            allZE+=rs.getDouble(6);
        }
        s+=summTable(summ, summZE);
        rs.close();
        statement.close();
        return s;
    }

    private String genSubjectsCols(ResultSet rs, int pk, int cols) throws SQLException{
        StringBuilder s= new StringBuilder();
        int k=3;
        double num = rs.getDouble(3);
        //номер
        if((num*10)!=((int)num)*10)
            s.append("<td style=\"").append(backColorHead).append("\">").append(cycle).append(".").append(keypart).append(table.getField(num + "", k++ + "-" + pk, "width:30px;"
                    + leftFields + backColorHead)).append("</td>");
        else
            s.append("<tr><td>").append(cycle).append(".").append(keypart).append(table.getField((int) num + "", k++ + "-" + pk, "width:30px;" + leftFields)).append("</td>");
        //название дисциплины
        s.append("<td>").append(table.getField(rs.getString(4), k++ + "-" + pk, "width:300px")).append("</td>");
        //шифр кафедры
        s.append("<td>").append(rs.getString(5)).append("</td>");
        //з.е
        s.append("<td>").append(rs.getDouble(6)).append("</td>");
        //экзамены и зачёты
        s.append("<td>").append(table.getField(rs.getString(7), k++ + "-" + pk, "width:20px")).append("</td>");
        s.append("<td>").append(table.getField(rs.getString(8), k++ + "-" + pk, "width:20px")).append("</td>");
        for (int i=9; i<=16;i++)
            s.append("<td>").append(rs.getInt(i)).append("</td>");
        //SubjectAssignment
        for(int i=17;i<=cols;i++) {
            String col = rs.getString(i);
            if(col==null) {
                s.append("<td style=\"color:#b1b1b1\">0</td>");
            }else{
                s.append("<td>").append(table.getField(col, k++ + "-" + pk, "width:20px")).append("</td>");
            }
        }
        return s.toString();
    }

    private void summ(ResultSet rs, int summ[]) throws SQLException{
        int j=0;
        for(int i=7; i<summ.length;i++) {
            allSum[j] += rs.getInt(i);
            summ[j++] += rs.getInt(i);
        }
    }

    private String summTable(int summ[], double summZE){
        StringBuilder s= new StringBuilder();
        s.append("<tr style=\"").append(backColorHead).append("\"><td colspan=\"3\"><b>ИТОГО:</b></td>");
        s.append("<td>").append(summZE).append("</td>");
        for (int aSumm : summ) s.append("<td>").append(aSumm).append("</td>");
        s.append("</tr>");
        return s.toString();
    }

    public String summPage() throws SQLException{
        StringBuilder s= new StringBuilder();
        s.append("<tr style=\"").append(backColorHead).append("\"><td colspan=\"3\"><b>ИТОГО ПО ЦИКЛУ:</b></td>");
        s.append("<td>").append(allZE).append("</td>");
        for (int i=0;i<cols-6;i++)
            s.append("<td>").append(allSum[i]).append("</td>");
        s.append("</tr>");
        return s.toString();
    }

    /**
     * Заголовок ГСЭ
     */
    public String headTable(){
        StringBuilder s= new StringBuilder();
        s.append("<tr>");
        s.append("<td colspan=\"4\">Аудиторные</td><td colspan=\"3\">Самост.</td>");
        for(int i=1; i<5;i++)
            s.append("<td colspan=\"8\">").append(i).append("курс</td>");
        s.append("</tr><tr>");
        Config config = new Config();
        ArrayList<String> content = config.getArrayXml("table-hmp1");
        for(int i=0;i<7;i++)
            s.append("<td rowspan=\"2\" class=\"rotatable\">").append(content.get(i)).append("</td>");
        for(int i=1; i<9;i++)
            s.append("<td colspan=\"4\">").append(i).append(" (18)</td>");
        s.append("</tr><tr style=\"height:60px\">");
        ArrayList<String> content1 = config.getArrayXml("table-hmp2");
        for(int i=1; i<9;i++)
            for(int j=0;j<4;j++)
                s.append("<td style=\"width:10px; height: 20px;\" class=\"rotatable\">").append(content1.get(j)).append("</td>");
        s.append("</tr>");
        return s.toString();
    }
}
