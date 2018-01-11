package org.scs.ap.content;

import org.scs.ap.database.Database;
import org.scs.ap.view.Table;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by User on 06.01.2018.
 */
public class Titles {
    private Database database;
    private String yearCreation;
    private String yearReception;
    private String qualification;
    private String termsEducation;
    private String lvlEducation;
    private String formEducation;

    public Titles(Database database){
        this.database = database;
        initial();
    }

    /**
     * Заполняем поля из базы сразу при запуске title
     */
    public void initial() {
        Connection connection = database.getConnection();
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM titles");
            while (resultSet.next()){
                yearCreation = resultSet.getString("year_creation");
                yearReception = resultSet.getString("year_reception");
                qualification = resultSet.getString("qualification");
                termsEducation = resultSet.getString("terms_education");
                lvlEducation = resultSet.getString("lvl_education");
                formEducation = resultSet.getString("form_education");
            }
            resultSet.close();
            statement.close();
            connection.close();
        }catch (SQLException e){
            new RuntimeException(e);
        }
    }

    /**
     * График учебного процесса
     * @return таблица html
     */
    public String getScheduleProcess(){
        Table table = new Table(100);
        Connection connection = database.getConnection();
        Statement statement;
        try {
            statement = connection.createStatement();
            genHead(table);
            //Недели
            genWeek(table, "week_msa", statement);
            //Старт недели
            genWeek(table, "date_start", statement);
            //Конец недели
            genWeek(table, "date_end", statement);
            //Курсы
            genLabelMsa(table, statement);
            statement.close();
            connection.close();
        }catch (SQLException e){
            new RuntimeException(e);
        }
        return table.toString();
    }

    /**
     * Заполняем недели
     */
    private void genWeek(Table table, String s, Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM matrix_schedules_ap");
        table.openRow();
        ArrayList<String> str = new ArrayList<>();
        while (resultSet.next())
            str.add(resultSet.getString(s));
        int size = str.size()/Integer.parseInt(termsEducation);
        for(int i=0; i<size;i++)
            table.add(str.get(i));
        table.closeRow();
        resultSet.close();
    }

    /**
     * Заполняем курсы
     */
    private void genLabelMsa(Table table, Statement statement) throws SQLException{
        ResultSet resultSet = statement.executeQuery("SELECT * FROM matrix_schedules_ap");
        ArrayList<String> str = new ArrayList<>();
        while (resultSet.next())
            str.add(resultSet.getString("label_msa"));
        int size = str.size()/Integer.parseInt(termsEducation);
        int kurs=0;
        for(int i=0; i<Integer.parseInt(termsEducation); i++){
            table.openRow();
            table.add(i+"");
            for(int j=0; j<size; j++){
                if(str.get(kurs)==null)
                    table.add("");
                else
                    table.add(str.get(kurs));
                kurs++;
            }
            table.closeRow();
        }
    }

    /**
     * Сводные даннные о бюджете времени
     * @return таблица html
     */
    public String getTimeBudgets() {
        Table table = new Table(100);
        Connection connection = database.getConnection();
        Statement statement;
        ResultSet resultSet;
        try {
            genHeadTimeBudget(table);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM time_budgets");
            ArrayList<Integer> lower = new ArrayList<>();
            for(int i=0;i<6;i++)
                lower.add(0);
            int sumLower=0;
            while (resultSet.next()) {
                ArrayList<Integer> all = new ArrayList<>();
                table.openRow();
                table.add(resultSet.getString("course_tb"));
                putAndCount(resultSet, lower, all, "weeks_theory_tb");
                putAndCount(resultSet, lower, all, "weeks_session_tb");
                putAndCount(resultSet, lower, all, "weeks_pract_tb");
                putAndCount(resultSet, lower, all, "weeks_diploma_tb");
                putAndCount(resultSet, lower, all, "weeks_state_exam_tb");
                putAndCount(resultSet, lower, all, "weeks_holiday_tb");
                int sumAll = 0;
                for(Integer i : all) {
                    table.add(i + "");
                    sumAll+=i;
                }
                table.add(sumAll + "");
                table.closeRow();
                sumLower+=sumAll;
            }
            table.openRow();
            table.add("Всего: ");
            for(Integer i : lower)
                table.add(i + "");
            table.add(sumLower + "");
            table.closeRow();
            resultSet.close();
            statement.close();
            connection.close();
        }catch (SQLException e){
            new RuntimeException(e);
        }
        return table.toString();
    }

    private void putAndCount(ResultSet resultSet, ArrayList<Integer> lower, ArrayList<Integer> all, String s) throws SQLException {
        all.add(Integer.parseInt(resultSet.getString(s)));
        lower.add(lower.get(0)+(all.get(all.size()-1)));
        lower.remove(0);
    }

    /**
     * Таблица практика
     * @return таблица html
     */
    public String getPractics(){
        Table table = new Table(80);
        Connection connection = database.getConnection();
        Statement statement;
        try {
            genHeadPracts(table);
            statement = connection.createStatement();
            ArrayList<String> practNames = new ArrayList<>();
            ArrayList<String> pract = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM pract_types");
            while(resultSet.next())
                practNames.add(resultSet.getString("name_pt"));
            resultSet = statement.executeQuery("SELECT * FROM pract");
            while(resultSet.next())
                pract.add(resultSet.getString("key_week_count"));
            int sem=4;
            for(int i = 0; i<practNames.size(); i++) {
                table.openRow();
                table.add(practNames.get(i));
                table.add(sem+"");
                table.add(pract.get(i));
                sem+=2;
                table.closeRow();
            }
            resultSet.close();
            statement.close();
            connection.close();
        }catch (SQLException e){
            new RuntimeException(e);
        }
        return table.toString();
    }

    /**
     * Государсвтенная итоговая аттестация
     * @return таблица html
     */
    public String genStateAtestation(){
        Table table = new Table(80);
        Connection connection = database.getConnection();
        Statement statement;
        try {
            genHeadState(table);
            statement = connection.createStatement();
            ArrayList<String> formSct = new ArrayList<>();
            ArrayList<String> nameSct = new ArrayList<>();
            ArrayList<String> state = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM state_certification_types");
            while(resultSet.next()) {
                formSct.add(resultSet.getString("form_sct"));
                nameSct.add(resultSet.getString("name_sct"));
            }
            resultSet = statement.executeQuery("SELECT * FROM state_certification");
            while(resultSet.next())
                state.add(resultSet.getString("semester_sc"));
            for(int i = 0; i<formSct.size(); i++) {
                table.openRow();
                table.add(formSct.get(i));
                table.add(nameSct.get(i));
                table.add(state.get(i));
                table.closeRow();
            }
            resultSet.close();
            statement.close();
            connection.close();
        }catch (SQLException e){
            new RuntimeException(e);
        }
        return table.toString();
    }

    private void genHead(Table table){
        table.openRow();
        table.add("КУРС", 4, 1);
        table.add("Сентябрь", 1, 4);
        table.add("Октябрь", 1, 5);
        table.add("Ноябрь", 1, 4);
        table.add("Декабрь", 1, 4);
        table.add("Январь", 1, 5);
        table.add("Февраль", 1, 4);
        table.add("Март", 1, 4);
        table.add("Апрель", 1, 4);
        table.add("Май", 1, 5);
        table.add("Июнь", 1, 4);
        table.add("Июль", 1, 4);
        table.add("Август", 1, 5);
        table.closeRow();
    }

    private void genHeadTimeBudget(Table table){
        table.openRow();
        String atribute="\theight: 120px;";
        table.add("КУРС", atribute);
        table.add("Теоретическое обучение", atribute);
        table.add("Экзаменационная сессия", atribute);
        table.add("Практика", atribute);
        table.add("Выпускная квалификационная работа", atribute);
        table.add("Государственный экзамен", atribute);
        table.add("Каникулы", atribute);
        table.add("Всего", atribute);
        table.closeRow();
    }

    private void genHeadPracts(Table table){
        table.openRow();
        String atribute="\theight: 120px;";
        table.add("Название практики", atribute);
        table.add("Семестр", atribute);
        table.add("Недели", atribute);
        table.closeRow();
    }

    private void genHeadState(Table table){
        table.openRow();
        table.add("Название учебной дисциплины");
        table.add("Форма государственной итоговой аттестации (экзамен, ВКР)");
        table.add("Семестр");
        table.closeRow();
    }

    public String getYearCreations() {
        return yearCreation;
    }
    public String getYearReception() {
        return yearReception;
    }
    public String getQualification() {
        return qualification;
    }
    public String getTermsEducation() {
        return termsEducation;
    }
    public String getLvlEducation() {
        return lvlEducation;
    }
    public String getFormEducation() {
        return formEducation;
    }
}
