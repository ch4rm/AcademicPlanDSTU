package org.scs.ap.content;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CounterGenerate {
    private Connection connection;

    public CounterGenerate(Connection connection){
        this.connection = connection;
    }

    /**
     * Найти сумму аудиторных для таблицы Ауд./Сам.
     * @param semester
     */
    public int getSumSemesterAud(int semester) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = null;
        int sum = 0;
        String []cols = {"hour_lec_sa", "hour_lab_sa", "hour_prac_sa"};
        for(String s : cols){
            rs = statement.executeQuery("SELECT SUM(" + s+") FROM subject_assignment WHERE semester_num_sa = " + semester);
            rs.next();
            sum += rs.getInt(1);
        }
        statement.close();
        rs.close();
        return sum;
    }

    /**
     * Найти сумму самостоятельных для таблицы Ауд./Сам.
     * @param semester
     */
    public int getSumSemesterSelf(int semester) throws SQLException{
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT SUM(hour_self_sa) FROM subject_assignment WHERE semester_num_sa = " + semester);
        rs.next();
        int sum = rs.getInt(1);
        statement.close();
        rs.close();
        return sum;
    }

    /**
     * Найти число экзаменов за семестр
     * @param semester
     */
    public int getCountExams(int semester) throws SQLException{
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT COUNT(exams_s) FROM subjects WHERE exams_s = " + semester);
        rs.next();
        int count = rs.getInt(1);
        statement.close();
        rs.close();
        return count;
    }

    /**
     * Найти число зачётов за семестр
     * @param semester
     */
    public int getCountSetoff(int semester) throws SQLException{
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT COUNT(setoff_s) FROM subjects WHERE setoff_s = " + semester);
        rs.next();
        int count = rs.getInt(1);
        statement.close();
        rs.close();
        return count;
    }

    /**
     * Найти число курсовых за семестр
     * @param semester
     */
    public int getCountKurs(int semester) throws SQLException{
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT COUNT(name_s) FROM subjects WHERE name_s ILIKE 'Курс%' AND (setoff_s = "
                + semester +" OR exams_s = "+ semester +")");
        rs.next();
        int count = rs.getInt(1);
        statement.close();
        rs.close();
        return count;
    }

    /**
     * Найти число кредитов за семестр
     * @param semester
     */
    public int getCountCredits(int semester) throws SQLException{
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT (SUM(hour_lec_sa*18) + SUM(hour_lab_sa*18) + SUM(hour_prac_sa*18) " +
                "+ SUM(hour_self_sa*18))/36 FROM subject_assignment WHERE semester_num_sa = " + semester);
        rs.next();
        int count = rs.getInt(1);
        statement.close();
        rs.close();
        return count;
    }

    /**
     * Найти сумму БСР за семестр
     * @param semester
     */
    public int getSumBsr(int semester) throws SQLException{
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT SUM(bsr_s) FROM subjects WHERE setoff_s = " + semester +
                " OR exams_s = "+ semester);
        rs.next();
        int sum = rs.getInt(1);
        statement.close();
        rs.close();
        return sum;
    }

    /**
     * Найти сумму БСР
     */
    public int getAllSumBsr() throws SQLException{
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT SUM(bsr_s) FROM subjects");
        rs.next();
        int sum = rs.getInt(1);
        statement.close();
        rs.close();
        return sum;
    }
}
