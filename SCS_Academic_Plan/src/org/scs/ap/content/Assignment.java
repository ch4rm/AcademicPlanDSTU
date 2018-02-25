package org.scs.ap.content;

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
public class Assignment {
    private String backColorHead="background: #CEDFF2";
    private String backColorCols="background: #e6eefb";
    private Connection connection;
    private String cycle="";
    private ArrayList<String> department = new ArrayList<>();
    private ArrayList<Subject> subjects = new ArrayList<>();
    private int index=0;

    public Assignment(Connection connection, int key_type_fk){
        init(connection, key_type_fk);
    }

    public Assignment(Connection connection, String backColorHead, String backColorCols, int key_type_fk){
        this.backColorHead = backColorHead;
        this.backColorCols = backColorCols;
        init(connection, key_type_fk);
    }

    private void init(Connection connection, int key_type_fk){
        this.connection = connection;
        try {
            department();
            subjectAssigment(key_type_fk);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Имя цикла (Б1)
     */
    public String getCycle(int cyclePk) throws SQLException {
        String s = "";
        Table table = new Table();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM cycles WHERE key_cycle_pk = "+cyclePk);
        while (resultSet.next()) {
            cycle = resultSet.getString("key_cycle_let");
            s = table.getField(cycle, "key_cycle_let", "width:80px; font-weight:bolder; font-size:14pt; "+backColorHead)
                + table.getField(resultSet.getString("name_c"), "name_c", "width: 600px; font-weight:bolder; font-size:14pt; " +
                    backColorHead);
        }
        statement.close();
        resultSet.close();
        return s;
    }

    /**
     * Имя части (Б1.Б)
     */
    public String getParts(int part) throws SQLException{
        String s;
        Table table = new Table();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM parts WHERE key_parts_pk = "+part);
        String keyPartsLet="";
        String nameP="";
        while (resultSet.next()){
            keyPartsLet=resultSet.getString("key_parts_let");
            nameP=resultSet.getString("name_p");
        }
        s= cycle + "." + table.getField(keyPartsLet, "key_parts_let."+part, "width:50px; font-weight:bold;font-size:12pt; " +
                backColorHead)
                + table.getField(nameP, "name_p."+part, "width:600px; font-weight:bold;font-size:12pt; " +
                backColorHead);
        statement.close();
        resultSet.close();
        return s;
    }

    /**
     * Предметы (Б1.Б1 История)
     */
    public String getSubjects(int keyParts) throws SQLException{
        int[]result = new int[43];
        String s="";
        Table table = new Table();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM subjects WHERE key_parts_fk = " + keyParts + " ORDER BY key_subject_pk");
        while (resultSet.next()){
            String name = resultSet.getString("name_s");
            int keypk = resultSet.getInt("key_subject_pk");
            int keydepfk = resultSet.getInt("key_department_fk");
            int exams = resultSet.getInt("exams_s");
            int setoff = resultSet.getInt("setoff_s");
            int lec = resultSet.getInt("lect_s");
            int lab = resultSet.getInt("lab_s");
            int pract = resultSet.getInt("pract_s");
            int ksr = resultSet.getInt("ksr_s");
            int bsr = resultSet.getInt("bsr_s");
            //Всего
            int lecLabPrac=lec+lab+pract;
            int ksrBsr=ksr+bsr;
            int all=lecLabPrac+ksrBsr;
            //Сумма
            result[0]+=exams;
            result[1]+=setoff;
            result[2]+=all;
            result[3]+=lecLabPrac;
            result[4]+=lec;
            result[5]+=lab;
            result[6]+=pract;
            result[7]+=ksrBsr;
            result[8]+=ksr;
            result[9]+=bsr;
            s+="<tr><td>" + cycle + "."              + resultSet.getString("key_subject") + "</td>"
                    + "<td>"                         + table.getField(name, "name_s."+keypk,"width:300px")
                    + "</td><td>"                    + table.getField(department.get(keydepfk-1),"key_department_fk."+index,"width:40px")
                    + "</td><td></td><td>"           + table.getField(exams+"", "exams_s."+keypk,"width:20px")
                    + "</td><td>"                    + table.getField(setoff+"", "setoff_s."+keypk,"width:20px")
                    + "</td><td>"+all+"</td><td>"
                    +lecLabPrac+"</td><td>"          + table.getField(lec+"", "lect_s."+keypk,"width:20px")
                    + "</td><td>"                    + table.getField(lab+"", "lab_s."+keypk,"width:20px")
                    + "</td><td>"                    + table.getField(pract+"", "pract_s."+keypk,"width:20px")
                    + "</td><td>"+ksrBsr+"</td><td>" + table.getField(ksr+"", "ksr_s."+keypk,"width:20px")
                    + "</td><td>"                    + table.getField(bsr+"", "bsr_s."+keypk,"width:20px")
                    + "</td>";
            s+=getSubjectAssigments(table, result, keypk);
            s+="</tr>";
            index++;
        }
        s+="<tr><td colspan=\"2\">ИТОГО:</td><td></td><td></td>";
        s+="<td>"+result[0]+"</td><td>"+result[1]+"</td><td>"+result[2]+"</td><td>"+result[3]+"</td><td>"
                +result[4]+"</td><td>"+result[5]+"</td><td>"+result[6]+"</td><td>"+result[7]+"</td><td>"
                +result[8]+"</td><td>"+result[9]+"</td>";
        for(int i=10;i<42;i++)
            s+="<td>"+result[i]+"</td>";
        s+="</tr>";
        statement.close();
        resultSet.close();
        return s;
    }

    private String getSubjectAssigments(Table table, int result[], int pk){
        String sub="";
        String style;
        int r = 10;
        boolean color=true;
        //System.out.println("fk="+ subjects.get(index).getSubjectFk()+" SizeSemester=" +subjects.get(index).getSizeSemester());
        //int sizeSemester = subjects.get(index).getSizeSemester();
        //Количество возможных семестров
        for(int i=1;i<9;i++) {
            if (color)
                style = backColorCols;
            else
                style = "";
            color = !color;

            if(subjects.get(index).getSizeSemester()==0){
                sub+=entrySubject(style, table, i);
                for (int k = 0; k < 4; k++)
                    r++;
            }
            //Количество семестров у одного предмета
            for (int j = 0; j < subjects.get(index).getSizeSemester(); j++) {
                //Если семестр совпадает
                if (subjects.get(index).getSemester(j) == i) {
                    //ЛЕКЦИИ
                    int lec = subjects.get(index).getLec(j);
                    result[r] += lec;
                    r++;
                    sub += "<td style=\"" + style + "\">" + table.getField(lec + "",
                            "hour_lec_sa." + pk, "width:20px; " + style) + "</td>";
                    //ЛАБЫ
                    int lab = subjects.get(index).getLab(j);
                    result[r] += lab;
                    r++;
                    sub += "<td style=\"" + style + "\">" + table.getField(lab + "",
                            "hour_lab_sa." + pk, "width:20px; " + style) + "</td>";
                    //ПРАКТИКИ
                    int prac = subjects.get(index).getPrac(j);
                    result[r] += prac;
                    r++;
                    sub += "<td style=\"" + style + "\">" + table.getField(prac + "",
                            "hour_prac_sa." + pk, "width:20px; " + style) + "</td>";
                    //САМОСТОЯТЕЛЬН
                    int self = subjects.get(index).getSelf(j);
                    result[r] += self;
                    r++;
                    sub += "<td style=\"" + style + "\">" + table.getField(self + "",
                            "hour_self_sa." + pk, "width:20px; " + style) + "</td>";
                    break;
                } else if(j==subjects.get(index).getSizeSemester()-1) {
                    sub+=entrySubject(style, table, i);
                    for (int k = 0; k < 4; k++)
                        r++;
                    break;
                }
            }
        }
        return sub;
    }

    private String entrySubject(String style, Table table, int i){
        String sub="";
        sub += "<td style=\"" + style + "\">" + table.getField("0", "hour_lec_sa." + "." + index + "." + i, "width:20px;" + style) + "</td>";
        sub += "<td style=\"" + style + "\">" + table.getField("0", "hour_lab_sa." + "." + index + "." + i, "width:20px;" + style) + "</td>";
        sub += "<td style=\"" + style + "\">" + table.getField("0", "hour_prac_sa." + "." + index + "." + i, "width:20px;" + style) + "</td>";
        sub += "<td style=\"" + style + "\">" + table.getField("0", "hour_self_sa." + "." + index + "." + i, "width:20px;" + style) + "</td>";
        return sub;
    }

    /**
     *
     */
    private void subjectAssigment(int key_type_fk) throws SQLException{
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM subject_assignment WHERE key_type_fk= " + key_type_fk +
                " ORDER BY key_subject_fk");
        int i=1;
        subjects.add(new Subject(i));
        while (resultSet.next()){
            if(resultSet.getInt("key_subject_fk")==i){
            }
            else{
                while(resultSet.getInt("key_subject_fk")!=i) {
                    i++;
                    subjects.add(new Subject(i));
                }
            }
            subjects.get(i-1).addCourse(resultSet.getInt("course_num_sa"));
            subjects.get(i-1).addSemester(resultSet.getInt("semester_num_sa"));
            subjects.get(i-1).addLec(resultSet.getInt("hour_lec_sa"));
            subjects.get(i-1).addLab(resultSet.getInt("hour_lab_sa"));
            subjects.get(i-1).addPrac(resultSet.getInt("hour_prac_sa"));
            subjects.get(i-1).addSelf(resultSet.getInt("hour_self_sa"));
        }
        statement.close();
        resultSet.close();
    }

    /**
     * Шифр кафедры
     */
    private void department() throws SQLException{
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM departments ORDER BY key_department_pk");
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
