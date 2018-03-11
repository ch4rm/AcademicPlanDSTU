package org.scs.ap.servlet;

import org.scs.ap.database.Database;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

@WebServlet(name = "HSEPost")
public class HSEPost extends HttpServlet {
    private Connection connection;
    //Имена полей в get_subjects
    private String subjectsNames[] = {"exams_s", "setoff_s", "sum_all", "sum_llp", "lect_s", "lab_s", "pract_s",
            "sum_kb", "ksr_s", "bsr_s"};
    //Имена полей в subject_assignment
    private String subjectsNamesAsgn[]={"hour_lec_sa", "hour_lab_sa", "hour_prac_sa","hour_self_sa"};
    private ArrayList<String> department = new ArrayList<>();
    //номер поля key_subject_pk
    private int isubjectPk;
    //номер поля key_subject
    private int ikeySubject;
    //номер поля name_s
    private int inameS;
    //номер поля key_department_fk
    private int ikeyDepartmentFk;

    /**
     * 8, 9, 13 - номер полей в get_subjects для расчёта суммы
     * с 6го запись в subjects
     * с 16го запись в subjects_assignment
     */

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        postAction(request, response, 1);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/hse.jsp");
        dispatcher.forward(request, response);
    }

    public void postAction(HttpServletRequest request, HttpServletResponse response, int cycle)  throws ServletException, IOException {
        //Объявление полей
        isubjectPk=1;
        ikeySubject=3;
        inameS=4;
        ikeyDepartmentFk=5;
        Database database = new Database();
        connection = database.getConnection();
        try{
            //Список департаментов
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT key_department FROM departments ORDER BY key_department_pk");
            while (resultSet.next())
                department.add(resultSet.getString("key_department"));
            statement.close();
            resultSet.close();
            connection.setAutoCommit(false);
            Statement st = connection.createStatement();
            //Обновление cycle
            updateCycle(request, st, cycle);
            //Обновление parts
            updateParts(request, st, cycle);
            //Обновление subjects
            updateSubjects(request, st, cycle);
            st.executeBatch();
            st.close();
            connection.close();
        }catch(SQLException e){}
    }

    /**
     * Внос изменений в таблицу cycles
     */
    private void updateCycle(HttpServletRequest request, Statement st, int cycle) throws SQLException{
        Statement ust = connection.createStatement();
        ResultSet rs = ust.executeQuery("SELECT * FROM cycles WHERE key_cycle_pk = "+cycle);
        while(rs.next()){
             if(!rs.getString(2).equals(request.getParameter("cycle-"+cycle)))
                 st.addBatch("UPDATE cycles SET key_cycle_let = '" + request.getParameter("cycle-" + cycle)
                         + "' WHERE key_cycle_pk = " + cycle + ";");
            if(!rs.getString(3).equals(request.getParameter("namec-"+cycle)))
                st.addBatch("UPDATE cycles SET name_c = '" + request.getParameter("namec-" + cycle)
                        + "' WHERE key_cycle_pk = " + cycle + ";");

        }
        rs.close();
        ust.close();
    }

    /**
     * Внос изменений в таблицу parts
     */
    private void updateParts(HttpServletRequest request, Statement st, int cycle) throws SQLException{
        Statement ust = connection.createStatement();
        ResultSet rs = ust.executeQuery("SELECT * FROM parts WHERE key_cycle_fk = "+cycle);
        while(rs.next()){
            int partPk = rs.getInt(1);
            if(!rs.getString(3).equals(request.getParameter("keylet-"+partPk)))
                st.addBatch("UPDATE parts SET key_parts_let = '" + request.getParameter("keylet-" + partPk)
                        + "' WHERE key_parts_pk = " + partPk + ";");
            if(!rs.getString(4).equals(request.getParameter("namep-"+partPk)))
                st.addBatch("UPDATE parts SET name_p = '" + request.getParameter("namep-" + partPk)
                        + "' WHERE key_parts_pk = " + partPk + ";");

        }
        rs.close();
        ust.close();
    }

    /**
     * Внос изменения в таблицу Subjects
     */
    private void updateSubjects(HttpServletRequest request, Statement st, int cycle) throws SQLException {
        Statement mainSt = connection.createStatement();

        mainSt.execute("SELECT create_sub("+cycle+");");
        ResultSet rs = mainSt.executeQuery("SELECT * FROM get_subjects ORDER BY key_subject_pk");
        while(rs.next()){
            int pk = rs.getInt(isubjectPk);
            //Обновление key_subjects
            updateKeySubject(request, st, rs, pk);
            //Обновление name_s
            updateName(request, st, rs, pk);
            //Обновление departments
            updateDepartment(request, st, rs, pk);
            //Остальной subject
            updateSubjects(request, st, rs, pk);
            //subject_assignment
            updateSubjectAssign(request, st, rs, pk);
        }
        rs.close();
        connection.setAutoCommit(true);
        mainSt.close();
    }

    /**
     * Внос изменений в key_subjects
     */
    private void updateKeySubject(HttpServletRequest request, Statement st, ResultSet rs, int pk) throws SQLException{
        double keySubject = rs.getDouble(ikeySubject);
        double newKeySubject= Double.parseDouble(request.getParameter(ikeySubject + "-" + pk));
        if (keySubject!=newKeySubject)
            st.addBatch("UPDATE subjects SET key_subject = " + newKeySubject+" WHERE key_subject_pk = "+pk+";");
    }

    /**
     * Внос изменений в name_s
     */
    private void updateName(HttpServletRequest request, Statement st, ResultSet rs, int pk) throws SQLException {
        if(!rs.getString(inameS).equals(request.getParameter(inameS + "-" + pk)))
            st.addBatch("UPDATE subjects SET name_s = '" + request.getParameter(inameS + "-" + pk)+"' WHERE key_subject_pk = "+pk);
    }

    /**
     * Внос изменений в key_department_fk
     */
    private void updateDepartment(HttpServletRequest request, Statement st, ResultSet rs, int pk) throws SQLException {
        if(!department.get(rs.getInt(ikeyDepartmentFk)-1).equals(request.getParameter(ikeyDepartmentFk + "-" + pk))) {
            st.addBatch("UPDATE departments SET key_department = '" + request.getParameter(ikeyDepartmentFk + "-" + pk)
                    + "' WHERE key_department_pk = " + rs.getInt(ikeyDepartmentFk)+";");
        }
    }

    /**
     * Внос изменений в subjects
     */
    private void updateSubjects(HttpServletRequest request, Statement st, ResultSet rs, int pk) throws SQLException{
        int j=0;
        int k=6;
        for(int i=6; i<subjectsNames.length+6;i++) {
            if(i!=8 && i!=9 && i!=13) {
                if(!rs.getString(i).equals(request.getParameter(k + "-" + pk)))
                    st.addBatch("UPDATE subjects SET " + subjectsNames[j] + " = '" + request.getParameter(k + "-" + pk)
                            + "' WHERE key_subject_pk = " + pk);
                k++;
            }
            j++;
        }
    }

    /**
     * Внос изменений в assignment_subjects
     */
    private void updateSubjectAssign(HttpServletRequest request, Statement st, ResultSet rs, int pk) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int j=0;
        int k=13;
        for(int i=16; i<rsmd.getColumnCount();i++){
            if(rs.getString(i)!=null){
                if(!rs.getString(i).equals(request.getParameter(k + "-" + pk))){
                    st.addBatch("UPDATE subject_assignment SET " + subjectsNamesAsgn[j] + " = '" + request.getParameter(k + "-" + pk)
                            + "' WHERE key_subject_fk = " + pk);
                }
                k++;
                j++;
            }
        }
    }
}
