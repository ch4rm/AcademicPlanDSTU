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
    private String backColorHead;
    private String backColorCols;
    private Connection connection;
    private String cycle="";
    private ArrayList<String> department = new ArrayList<>();
    private ArrayList<Subject> subjects = new ArrayList<>();
    private String subjectsFName [] = {"name_s", "key_subject_pk", "key_department_fk", "exams_s", "setoff_s",
                    "lect_s", "lab_s", "pract_s", "ksr_s", "bsr_s"};
    private int index=0;

    public Assignment(Connection connection, String backColorHead, String backColorCols, int key_type_fk){
        this.backColorHead = backColorHead;
        this.backColorCols = backColorCols;
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
        //Для подсчёта суммы в subjects_assignment
        int[]summSubjectsAssign = new int[32];
        //Для подсчёта суммы в subjects
        int[]summSubjects = new int[10];
        String s="";
        Table table = new Table();
        Statement statement = connection.createStatement();
        //Функция sql для расчёта суммы subjects
        ResultSet resultSet = statement.executeQuery("SELECT * FROM sub_summ("+keyParts+")");
        int sIndx=0;
        //Считаем сумму subjects
        while(resultSet.next()){
            summSubjects[sIndx] = resultSet.getInt("summ");
            sIndx++;
        }
        resultSet.close();
        resultSet = statement.executeQuery("SELECT * FROM subjects WHERE key_parts_fk = " + keyParts + " ORDER BY key_subject_pk");
        while (resultSet.next()){
            String subjectParametrs[] = new String[10];
            for(int i=0; i<10;i++)
                subjectParametrs[i]=resultSet.getString(subjectsFName[i]);
            int keypk = Integer.parseInt(subjectParametrs[1]);
            int keydepfk = Integer.parseInt(subjectParametrs[2]);
            //Всего
            int lecLabPrac=Integer.parseInt(subjectParametrs[5])+Integer.parseInt(subjectParametrs[6])
                    +Integer.parseInt(subjectParametrs[7]);
            int ksrBsr=Integer.parseInt(subjectParametrs[8])+Integer.parseInt(subjectParametrs[9]);
            int all=lecLabPrac+ksrBsr;
            s+="<tr><td>" + cycle + "."              + resultSet.getString("key_subject") + "</td>"
                    + "<td>"                         + table.getField(subjectParametrs[0], "name_s."+keypk,"width:300px")
                    + "</td><td>"                    + table.getField(department.get(keydepfk-1),"key_department_fk."+index,"width:40px")
                    + "</td><td></td><td>"           + table.getField(subjectParametrs[3]+"", "exams_s."+keypk,"width:20px")
                    + "</td><td>"                    + table.getField(subjectParametrs[4]+"", "setoff_s."+keypk,"width:20px")
                    + "</td><td>"+all+"</td><td>"
                    +lecLabPrac+"</td><td>"          + table.getField(subjectParametrs[5]+"", "lect_s."+keypk,"width:20px")
                    + "</td><td>"                    + table.getField(subjectParametrs[6]+"", "lab_s."+keypk,"width:20px")
                    + "</td><td>"                    + table.getField(subjectParametrs[7]+"", "pract_s."+keypk,"width:20px")
                    + "</td><td>"+ksrBsr+"</td><td>" + table.getField(subjectParametrs[8]+"", "ksr_s."+keypk,"width:20px")
                    + "</td><td>"                    + table.getField(subjectParametrs[9]+"", "bsr_s."+keypk,"width:20px")
                    + "</td>";
            //Считаем сумму и выводим subjects_assignment
            s+=getSubjectAssigments(table, summSubjectsAssign, keypk);
            s+="</tr>";
            index++;
        }
        s+="<tr><td colspan=\"2\">ИТОГО:</td><td></td><td></td><td>";
        for(int i = 0; i<9; i++){
            s+=summSubjects[i] + "</td><td>";
        }
        s+=summSubjects[9] + "</td>";
        for(int i=0;i<32;i++)
            s+="<td>"+summSubjectsAssign[i]+"</td>";
        s+="</tr>";
        statement.close();
        resultSet.close();
        return s;
    }

    private String getSubjectAssigments(Table table, int result[], int pk){
        String sub="";
        String style;
        int r = 0;
        boolean color=true;
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
                //Если семестр совпадает с тем что в базе
                if (subjects.get(index).getSemester(j) == i) {
                    //ЛЕКЦИИ
                    int lec = subjects.get(index).getLec(j);
                    result[r++] += lec;
                    sub += "<td style=\"" + style + "\">" + table.getField(lec + "",
                            "hour_lec_sa." + pk, "width:20px; " + style) + "</td>";
                    //ЛАБЫ
                    int lab = subjects.get(index).getLab(j);
                    result[r++] += lab;
                    sub += "<td style=\"" + style + "\">" + table.getField(lab + "",
                            "hour_lab_sa." + pk, "width:20px; " + style) + "</td>";
                    //ПРАКТИКИ
                    int prac = subjects.get(index).getPrac(j);
                    result[r++] += prac;
                    sub += "<td style=\"" + style + "\">" + table.getField(prac + "",
                            "hour_prac_sa." + pk, "width:20px; " + style) + "</td>";
                    //САМОСТОЯТЕЛЬН
                    int self = subjects.get(index).getSelf(j);
                    result[r++] += self;
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
     * Заполняем и объединяем по fk subject_assignment
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
     * Заполняем шифр кафедры
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
