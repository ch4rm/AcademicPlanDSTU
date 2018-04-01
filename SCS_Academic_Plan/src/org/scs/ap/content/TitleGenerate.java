package org.scs.ap.content;

import org.scs.ap.view.Config;
import org.scs.ap.view.Table;
import java.sql.*;
import java.util.ArrayList;

public class TitleGenerate {
    private Connection connection;
    private Config config;
    private String backColorHead;
    private String yearCreation;
    private String yearReception;
    private String qualification;
    private String termsEducation;
    private String lvlEducation;
    private String formEducation;
    private String profiles;
    /** theory - теоретическое обучение; passingExam - сдача кредитов; session - сессия: vacation - каникулы:
     * practice - практика; ressearch - НИР; stateExam - государственный экзамен: finalWork - подотовка ВКР. */
    private int theory[] = new int[4];
    private int passingExam[] = new int[4];
    private int session[] = new int[4];
    private int vacation[] = new int[4];
    private int practice[] = new int[4];
    private int ressearch[] = new int[4];
    private int stateExam[] = new int[4];
    private int finalWork[] = new int[4];
    private final int monthTableColumns[]={4, 5, 4, 4, 5, 4, 4, 4, 5, 4, 4, 5};

    public TitleGenerate(Connection connection, Config config, String backColorHead){
        this.backColorHead = backColorHead;
        this.connection = connection;
        this.config = config;
        initial();
    }

    /**
     * Заполняем поля из базы сразу при запуске title
     */
    public void initial() {
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
            resultSet = statement.executeQuery("SELECT * FROM profiles");
            while (resultSet.next()) {
                profiles = resultSet.getString("name_prof");
            }
            resultSet.close();
            statement.close();
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
        Statement statement;
        try {
            statement = connection.createStatement();
            ArrayList<String> months;
            months = config.getArrayXml("month");
            table.openRow(backColorHead);
            table.setRow("<td rowspan=\"4\" class=\"rotatable\">КУРС</td>");
            for(int i=0; i<12;i++)
                table.add(months.get(i), monthTableColumns[i]);
            table.closeRow();
            //Недели
            genWeek(table, "week_msa", statement);
            //Старт недели
            genWeek(table, "date_start", statement);
            //Конец недели
            genWeek(table, "date_end", statement);
            //Курсы
            genLabelMsa(table, statement);
            statement.close();
        }catch (SQLException e){
            new RuntimeException(e);
        }
        return table.toString();
    }

    /**
     * Заполняем недели
     */
    private void genWeek(Table table, String s, Statement statement) throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * FROM matrix_schedules_ap ORDER BY key_matr_sch_ap_pk");
        table.openRow();
        ArrayList<String> str = new ArrayList<String>();
        while (resultSet.next())
            str.add(resultSet.getString(s));
        for(int i=0; i<52;i++)
            table.addField(str.get(i), s+i);
        table.closeRow();
        resultSet.close();
    }

    /**
     * Заполняем курсы
     */
    private void genLabelMsa(Table table, Statement statement) throws SQLException{
        ResultSet resultSet = statement.executeQuery("SELECT * FROM matrix_schedules_ap ORDER BY key_matr_sch_ap_pk");
        ArrayList<String> str = new ArrayList<String>();
        while (resultSet.next())
            str.add(resultSet.getString("label_msa"));
        int name = 0, kurs=0;
        for(int i=0; i<4; i++){
            table.openRow();
            table.add((i+1)+"");
            for(int j=0; j<52; j++){
                if(str.get(kurs)==null) {
                    table.addField("", "label_msa" + name);
                    theory[i]+=1;
                }
                else {
                    table.addField(str.get(kurs), "label_msa"+name);
                    switch (str.get(kurs)) {
                        case "СК": passingExam[i]+=1; break;
                        case "С": session[i]+=1; break;
                        case "К": vacation[i]+=1; break;
                        case "П": practice[i]+=1; break;
                        case "Н": ressearch[i]+=1; break;
                        case "Г": stateExam[i]+=1; break;
                        case "Д": finalWork[i]+=1; break;
                    }
                }
                kurs++;
                name++;
            }
            table.closeRow();
        }
    }

    /**
     * Сводные даннные о бюджете времени
     * @return таблица html
     */
    public String getTimeBudgets() {
        Table table = new Table();
        int sumAll=0;
        ArrayList<String> budget;
        budget = config.getArrayXml("budget");
        table.openRow(backColorHead);
        String atribute="\theight: 120px;";
        for(int i=0;i<10;i++) {
            table.add(budget.get(i), atribute);
        }
        table.closeRow();
        for(int i=0; i<4; i++) {
            table.openRow();
            table.add((i+1)+"");
            table.add(theory[i]+"");
            table.add(passingExam[i]+"");
            table.add(session[i]+"");
            table.add(vacation[i]+"");
            table.add(practice[i]+"");
            table.add(ressearch[i]+"");
            table.add(stateExam[i]+"");
            table.add(finalWork[i]+"");
            int all=theory[i]+passingExam[i]+session[i]+vacation[i]+practice[i]+ressearch[i]+stateExam[i]+finalWork[i];
            table.add(all+"");
            sumAll+=all;
            table.closeRow();
        }
        table.openRow();
        table.add("Всего:");
        table.add(theory[0]+theory[1]+theory[2]+theory[3]+"");
        table.add(passingExam[0]+passingExam[1]+passingExam[2]+passingExam[3]+"");
        table.add(session[0]+session[1]+session[2]+session[3]+"");
        table.add(vacation[0]+vacation[1]+vacation[2]+vacation[3]+"");
        table.add(practice[0]+practice[1]+practice[2]+practice[3]+"");
        table.add(ressearch[0]+ressearch[1]+ressearch[2]+ressearch[3]+"");
        table.add(stateExam[0]+stateExam[1]+stateExam[2]+stateExam[3]+"");
        table.add(finalWork[0]+finalWork[1]+finalWork[2]+finalWork[3]+"");
        table.add(sumAll+"");
        table.closeRow();
        return table.toString();
    }

    /**
     * Таблица практика
     * @return таблица html
     */
    public String getPractics(){
        Table table = new Table();
        Statement statement;
        try {
            ArrayList<String> practs;
            practs = config.getArrayXml("headPractics");
            table.openRow(backColorHead);
            String atribute="\theight: 120px;";
            for(int i=0;i<3;i++)
                table.add(practs.get(i), atribute);
            table.closeRow();
            statement = connection.createStatement();
            ArrayList<String> practNames = new ArrayList<>();
            ArrayList<String> pract = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM pract_types ORDER BY key_pract_pk");
            while(resultSet.next())
                practNames.add(resultSet.getString("name_pt"));
            resultSet = statement.executeQuery("SELECT * FROM pract ORDER BY key_pract_pk");
            while(resultSet.next())
                pract.add(resultSet.getString("key_week_count"));
            int sem=4;
            for(int i = 0; i<practNames.size(); i++) {
                table.openRow();
                table.addField(practNames.get(i), "name_pt"+i, 150);
                table.add(sem+"");
                table.addField(pract.get(i), "key_week_count"+i);
                sem+=2;
                table.closeRow();
            }
            resultSet.close();
            statement.close();
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
        Table table = new Table();
        Statement statement;
        try {
            ArrayList<String> states;
            states = config.getArrayXml("headState");
            table.openRow(backColorHead);
            for(int i=0;i<3;i++)
                table.add(states.get(i), "");
            table.closeRow();
            statement = connection.createStatement();
            ArrayList<String> formSct = new ArrayList<>();
            ArrayList<String> nameSct = new ArrayList<>();
            ArrayList<String> state = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM state_certification_types ORDER BY key_sc_pk");
            while(resultSet.next()) {
                formSct.add(resultSet.getString("form_sct"));
                nameSct.add(resultSet.getString("name_sct"));
            }
            resultSet = statement.executeQuery("SELECT * FROM state_certification ORDER BY key_sc_pk");
            while(resultSet.next())
                state.add(resultSet.getString("semester_sc"));
            for(int i = 0; i<formSct.size(); i++) {
                table.openRow();
                table.addField(formSct.get(i), "form_sct"+i, 250);
                table.addField(nameSct.get(i), "name_sct"+i, 250);
                table.addField(state.get(i), "semester_sc"+i, 20);
                table.closeRow();
            }
            resultSet.close();
            statement.close();
        }catch (SQLException e){
            new RuntimeException(e);
        }
        return table.toString();
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
    public String getProfiles() {
        return profiles;
    }
}
