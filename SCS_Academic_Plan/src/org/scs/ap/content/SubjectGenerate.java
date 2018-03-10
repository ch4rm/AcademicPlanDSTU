package org.scs.ap.content;

import org.scs.ap.view.Config;
import org.scs.ap.view.Table;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Shishko.Arthur on 03.02.2018.
 */
public class SubjectGenerate {
    private Table table;
    private String backColorHead;
    private Connection connection;
    private String cycle;
    private String keypart;
    private String leftFields="font-size: 11pt; padding-left: 2;";
    ArrayList<String> department = new ArrayList<>();

    public SubjectGenerate(Connection connection, String backColorHead){
        table = new Table();
        this.backColorHead = backColorHead;
        this.connection = connection;
        try {
            department();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            s = table.getField(cycle, "key_cycle_let."+cyclePk,
                    "width:80px; font-weight:bolder; font-size:14pt; "+backColorHead)
                    + table.getField(rs.getString("name_c"), "name_c",
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
            s= cycle + "." + table.getField(rs.getString("key_parts_let"), "key_parts_let."+part,
                    "width:50px; font-weight:bold;font-size:12pt; " + backColorHead)
                    + table.getField(rs.getString("name_p"), "name_p." + part,
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
        statement.execute("SELECT create_sub("+keyPart+");");
        ResultSet rs = statement.executeQuery("SELECT * FROM get_subjects");
        ResultSetMetaData rsmt = rs.getMetaData();
        int cols = rsmt.getColumnCount();
        int summ[] = new int[cols-4];
        while(rs.next()){
            int pk = rs.getInt(1);
            s+="<tr>";
            //номер, шифр и департамент
            s+=subjectsBCollsGen(rs, pk);
            //остальные колонки
            s+=subjectsCollsGen(rs, cols, pk, summ);
            s+="</tr>";
        }
        s+=summTable(summ);
        rs.close();
        statement.close();
        return s;
    }

    /*номер, шифр и департамент*/
    private String subjectsBCollsGen(ResultSet rs, int pk) throws SQLException{
        String s="";
        double num = rs.getDouble(2);
        //номер
        if((num*10)!=((int)num)*10)
            s+="<td style=\""+backColorHead+"\">"+cycle+"."+keypart+table.getField(num+"", 2+"-"+pk,"width:30px;"
                    +leftFields+backColorHead)+"</td>";
        else
            s+="<tr><td>"+cycle+"."+keypart+table.getField((int)num+"", 2+"-"+pk,"width:30px;"+leftFields)+"</td>";
        //шифр кафедры
        s+="<td>"+table.getField(rs.getString(3), 3+"-"+pk,"width:300px")+"</td>";
        //департамент
        s+="<td>"+table.getField(department.get(rs.getInt(4)-1), 4+"-"+pk,"width:55px")+"</td>";
        return s;
    }

    /*остальные subject и assignment_subjects*/
    private String subjectsCollsGen(ResultSet rs, int cols, int pk, int summ[]) throws SQLException{
        String s="";
        int j =0;
        for(int i=5; i<=cols;i++) {
            String col = rs.getString(i);
            if(col==null)
                s += "<td style=\"color:#b1b1b1\">0</td>";
            else {
                s += "<td>"+ table.getField(col, i+"-"+pk,"width:20px") +"</td>";
                summ[j]+=Integer.parseInt(col);
            }
            j++;
        }
        return s;
    }

    private String summTable(int summ[]){
        String s="";
        s+="<tr style=\"" + backColorHead + "\"><td colspan=\"3\">ИТОГО:</td>";
        for(int i=0;i<summ.length;i++)
            s+="<td>"+summ[i]+"</td>";
        s+="</tr>";
        return s;
    }

    public String summPage(int cyclePk) throws SQLException{
        String s="";
        s+="<tr style=\""+backColorHead+"\"><td colspan=\"3\">ИТОГО ПО ЦИКЛУ:</td>";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM summ_sub("+cyclePk+")");
        while(rs.next()){
            String sum = rs.getString(1);
            if(sum!=null)
                s+="<td>"+sum+"</td>";
            else s+="<td>0</td>";
        }
        rs.close();
        statement.close();
        s+="</tr>";
        return s;
    }

    private void department() throws SQLException{
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT key_department FROM departments ORDER BY key_department_pk");
        while (resultSet.next())
            department.add(resultSet.getString("key_department"));
        statement.close();
        resultSet.close();
    }

    /**
     * Заголовок ГСЭ
     */
    public String headTable(){
        String s="";
        s+="<tr>";
        s+="<td colspan=\"4\">Аудиторные</td><td colspan=\"3\">Самост.</td>";
        for(int i=1; i<5;i++)
            s+="<td colspan=\"8\">"+ i + "курс</td>";
        s+="</tr><tr>";
        Config config = new Config();
        ArrayList<String> content = config.getArrayXml("table-hmp1");
        for(int i=0;i<7;i++)
            s+="<td rowspan=\"2\" class=\"rotatable\">"+content.get(i)+"</td>";
        for(int i=1; i<9;i++)
            s+="<td colspan=\"4\">" + i + " (18)</td>";
        s+="</tr><tr style=\"height:60px\">";
        ArrayList<String> content1 = config.getArrayXml("table-hmp2");
        for(int i=1; i<9;i++)
            for(int j=0;j<4;j++)
                s += "<td style=\"width:10px; height: 20px;\" class=\"rotatable\">"+content1.get(j)+"</td>";
        s+="</tr>";
        return s;
    }
}
