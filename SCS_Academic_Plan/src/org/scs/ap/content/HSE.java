package org.scs.ap.content;

import org.scs.ap.database.Database;
import org.scs.ap.view.Subject;
import org.scs.ap.view.Table;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Shishko.Arthur on 03.02.2018.
 */
public class HSE {
    private Database database;
    private String cycle="";
    private ArrayList<String> department = new ArrayList<>();
    private ArrayList<Subject> subjects = new ArrayList<>();
    private int index=0;

    public HSE(Database database){
        this.database = database;
        try {
            department();
            subjectAssigment(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Имя цикла (Б1)
     */
    public String getCycle() throws SQLException {
        String s = "";
        Table table = new Table();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM cycles WHERE key_cycle_pk = 1");
        while (resultSet.next()) {
            cycle = resultSet.getString("key_cycle_let");
            s = table.getField(cycle, "key_cycle_let", "width:80px; font-weight:bolder; font-size:14pt; background: #E1E5FF")
                + table.getField(resultSet.getString("name_c"), "name_c", "width: 600px; font-weight:bolder; font-size:14pt; " +
                    "background: #E1E5FF");
        }
        connection.close();
        statement.close();
        resultSet.close();
        return s;
    }

    /**
     * Имя части (Б1.Б)
     */
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
        s= cycle + "." + table.getField(keyPartsLet.get(Part), "key_parts_let"+Part, "width:50px; font-weight:bold;font-size:12pt; " +
                "background: #E1E5FF")
                + table.getField(nameP.get(Part), "name_p"+Part, "width:600px; font-weight:bold;font-size:12pt; " +
                "background: #E1E5FF");
        connection.close();
        statement.close();
        resultSet.close();
        return s;
    }

    /**
     * Предметы (Б1.Б1 История)
     */
    public String getSubjects(int keyParts) throws SQLException{
        int[] result = new int[39];
        for(int i=0;i<39;i++)
            result[i]=0;
        String s="";
        keyParts++;
        Table table = new Table();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM subjects WHERE key_parts_fk = " + keyParts);
        while (resultSet.next()){
            /*int exams_s=resultSet.getInt("exams_s");
            int setoff_s=resultSet.getInt("setoff_s");
            int lect_s=resultSet.getInt("lect_s");
            int lab_s=resultSet.getInt("lab_s");
            int pract_s=resultSet.getInt("pract_s");
            int ksr_s=resultSet.getInt("ksr_s");
            int bsr_s=resultSet.getInt("bsr_s");
            result[0]+=resultSet.getInt("exams_s");
            result[1]+=resultSet.getInt("setoff_s");
            result[2]+=resultSet.getInt("lect_s");
            result[3]+=resultSet.getInt("lab_s");
            result[4]+=resultSet.getInt("pract_s");
            result[5]+=resultSet.getInt("ksr_s");
            result[6]+=resultSet.getInt("bsr_s");*/
            s+="<tr><td>" + cycle + "." + resultSet.getString("key_subject") + "</td>"
                    + "<td>" + table.getField(resultSet.getString("name_s"), "name_s"+index,"width:300px")
                    + "</td><td>" + table.getField(department.get(Integer.parseInt(resultSet.getString("key_department_fk"))+1),
                    "key_department_fk"+index,"width:30px")
                    + "</td><td></td><td>" + table.getField(resultSet.getString("exams_s"), "exams_s"+index,"width:20px")
                    + "</td><td>" + table.getField(resultSet.getString("setoff_s"), "setoff_s"+index,"width:20px")
                    + "</td><td></td><td></td><td>" + table.getField(resultSet.getString("lect_s"), "lect_s"+index,"width:20px")
                    + "</td><td>" + table.getField(resultSet.getString("lab_s"), "lab_s"+index,"width:20px")
                    + "</td><td>" + table.getField(resultSet.getString("pract_s"), "pract_s"+index,"width:20px")
                    + "</td><td></td><td>" + table.getField(resultSet.getString("ksr_s"), "ksr_s"+index,"width:20px")
                    + "</td><td>" + table.getField(resultSet.getString("bsr_s"), "bsr_s"+index,"width:20px")
                    + "</td>";
            s+=getSubjectAssigment(table, result);
            s+="</tr>";
            index++;
        }
        /*s+="<tr><td colspan=\"2\">ИТОГО:</td><td></td><td></td>";
        s+="<td>"+result[0]+"</td><td>"+result[1]+"</td><td></td><td></td><td>"+result[2]+"</td><td>"+result[3]
                +"</td><td>"+result[4]+"</td><td>"+result[5]+"</td><td></td><td>"+result[6]+"</td><td>"+result[7]+"</td>";
        for(int ddd=0;ddd<32;ddd++)
            s+="<td></td>";
        s+="</tr>";*/
        connection.close();
        statement.close();
        resultSet.close();
        return s;
    }

    private String getSubjectAssigment(Table table, int result[]){
        String sub="";
        for(int i=1;i<9;i++){
            for(int j=0;j<subjects.get(index).getSizeSemester();j++){
                if(subjects.get(index).getSemester(j)==i){
                    int lec = subjects.get(index).getLec(j);
                    sub+="<td style=\""+ (lec==0?"":"background: #D1D8FF") +"\">" + table.getField(lec+"",
                            "hour_lec_sa"+"."+index+"."+ subjects.get(index).getSemester(j),"width:20px; "+(lec==0?"":"background: #D1D8FF"))
                            + "</td>";
                    int lab = subjects.get(index).getLab(j);
                    sub+="<td style=\""+ (lab==0? "":"background: #D1D8FF") + "\">" + table.getField(lab+"",
                            "hour_lab_sa"+"."+index+"."+ subjects.get(index).getSemester(j),"width:20px; "+(lab==0?"":"background: #D1D8FF"))
                            + "</td>";
                    int prac = subjects.get(index).getPrac(j);
                    sub+="<td style=\""+(prac==0?"":"background: #D1D8FF")+"\">" + table.getField(prac+"",
                            "hour_prac_sa"+"."+index+"."+ subjects.get(index).getSemester(j),"width:20px; "+(prac==0?"":"background: #D1D8FF"))
                            + "</td>";
                    int self=subjects.get(index).getSelf(j);
                    sub+="<td style=\""+(self==0?"":"background: #D1D8FF")+"\">" + table.getField(self+"",
                            "hour_self_sa"+"."+index+"."+ subjects.get(index).getSemester(j),"width:20px;"+(self==0?"":"background: #D1D8FF"))
                            + "</td>";
                }
                else{
                    sub+="<td>"+table.getField("0","hour_lec_sa"+"."+index+"."+i,"width:20px")+"</td>";
                    sub+="<td>"+table.getField("0","hour_lab_sa"+"."+index+"."+i,"width:20px")+"</td>";
                    sub+="<td>"+table.getField("0","hour_prac_sa"+"."+index+"."+i,"width:20px")+"</td>";
                    sub+="<td>"+table.getField("0","hour_self_sa"+"."+index+"."+i,"width:20px")+"</td>";
                }
            }
        }
        return sub;
    }

    private void subjectAssigment(int key_type_fk) throws SQLException{
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM subject_assignment WHERE key_type_fk= " + key_type_fk +
                " ORDER BY key_subject_fk");
        int i=1;
        subjects.add(new Subject(i));
        while (resultSet.next()){
            if(resultSet.getInt("course_num_sa")==i){
            }else {
                i++;
                subjects.add(new Subject(i));
            }
            subjects.get(i-1).addCourse(resultSet.getInt("course_num_sa"));
            subjects.get(i-1).addSemester(resultSet.getInt("semester_num_sa"));
            subjects.get(i-1).addLec(resultSet.getInt("hour_lec_sa"));
            subjects.get(i-1).addLab(resultSet.getInt("hour_lab_sa"));
            subjects.get(i-1).addPrac(resultSet.getInt("hour_prac_sa"));
            subjects.get(i-1).addSelf(resultSet.getInt("hour_self_sa"));
        }
        connection.close();
        statement.close();
        resultSet.close();
    }

    /**
     * Шифр кафедры
     */
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
