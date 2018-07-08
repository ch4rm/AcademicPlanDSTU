package org.scs.ap.servlet;

import org.scs.ap.view.Message;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

import static org.scs.ap.servlet.Login.db;

@WebServlet(name = "HSEPost")
public class HSEPost extends HttpServlet {
    private Connection connection = db.getConnection();
    Message message = new Message();
    //Имена полей в subject_assignment
    private String subjectsNamesAsgn[]={"hour_lec_sa", "hour_lab_sa", "hour_prac_sa","hour_self_sa"};
    //номер поля key_subject_pk
    private int isubjectPk=1;
    //номер поля key_subject
    private int ikeySubject=3;
    //номер поля name_s
    private int inameS=4;
    //номер шифра кафедры
    private int idepartment = 5;
    //номер поля экзамены
    private int iexams  = 6;
    //номер поля зачёты
    private int isetoff = 7;
    //номер поля бср
    private int ibsr = 8;
    //имя первого поля subject_assignment
    private int bSubAssign=9;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        postAction(request, response, 1);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/hse.jsp");
        dispatcher.forward(request, response);
    }

    public void postAction(HttpServletRequest request, HttpServletResponse response, int cycle) throws ServletException, IOException {
        //Обновление cycle
        updateCycle(request, cycle);
        //Обновление parts
        updateParts(request, cycle);
        //Обновление subjects
        updateSubjects(request, cycle);
    }

    /**
     * Внос изменений в таблицу cycles
     */
    private void updateCycle(HttpServletRequest request, int cycle){
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM cycles WHERE key_cycle_pk = " + cycle);
            while (rs.next()) {
                if (!rs.getString(2).equals(request.getParameter("cycle-" + cycle)))
                    st.execute("UPDATE cycles SET key_cycle_let = '" + request.getParameter("cycle-" + cycle)
                            + "' WHERE key_cycle_pk = " + cycle + ";");
                if (!rs.getString(3).equals(request.getParameter("namec-" + cycle))) {
                    st.execute("UPDATE cycles SET name_c = '" + request.getParameter("namec-" + cycle)
                            + "' WHERE key_cycle_pk = " + cycle + ";");
                }
            }
            rs.close();
            st.close();
        }catch (SQLException e){
            message.setMessage("Неверные значения названия цикла");
        }
    }

    /**
     * Внос изменений в таблицу parts
     */
    private void updateParts(HttpServletRequest request, int cycle) {
        try{
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM parts WHERE key_cycle_fk = "+cycle);
            while(rs.next()){
                int partPk = rs.getInt(1);
                if(!rs.getString(3).equals(request.getParameter("keylet-"+partPk)))
                    st.execute("UPDATE parts SET key_parts_let = '" + request.getParameter("keylet-" + partPk)
                            + "' WHERE key_parts_pk = " + partPk + ";");
                if(!rs.getString(4).equals(request.getParameter("namep-"+partPk)))
                    st.execute("UPDATE parts SET name_p = '" + request.getParameter("namep-" + partPk)
                            + "' WHERE key_parts_pk = " + partPk + ";");

            }
            rs.close();
            st.close();
        }catch (SQLException e){
            message.setMessage("Неверные значения названия части");
        }
    }

    /**
     * Внос изменения в таблицу Subjects
     */
    private void updateSubjects(HttpServletRequest request, int cycle)  {
        try{
            Statement st = connection.createStatement();
            st.execute("SELECT create_sub(" + cycle + ");");
            ResultSet rs = st.executeQuery("SELECT * FROM get_subjects ORDER BY key_subject_pk");
            while (rs.next()) {
                int pk = rs.getInt(isubjectPk);
                //Обновление key_subjects
                updateKeySubject(request, rs, pk);
                //Обновление name_s
                updateName(request, rs, pk);
                //Остальной subject
                updateSubjects(request, rs, pk);
                //subject_assignment
                updateSubjectAssign(request, rs, pk);
            }
            rs.close();
            st.close();
        }catch (SQLException e){
            message.setMessage("Ошибка при попытке получить доступ к базе");
        }
    }

    /**
     * Внос изменений в key_subjects
     */
    private void updateKeySubject(HttpServletRequest request, ResultSet rs, int pk)  {
        try{
            Statement st = connection.createStatement();
            double keySubject = rs.getDouble(ikeySubject);
            double newKeySubject= Double.parseDouble(request.getParameter(ikeySubject + "-" + pk));
            if (keySubject!=newKeySubject)
                st.execute("UPDATE subjects SET key_subject = " + newKeySubject+" WHERE key_subject_pk = "+pk+";");
        }catch (SQLException | NumberFormatException e){
            message.setMessage("Неверные значения номера части");
        }
    }

    /**
     * Внос изменений в name_s
     */
    private void updateName(HttpServletRequest request, ResultSet rs, int pk)  {
        try{
            Statement st = connection.createStatement();
            if(!rs.getString("name_s").equals(request.getParameter(inameS + "-" + pk)))
                st.execute("UPDATE subjects SET name_s = '" + request.getParameter(inameS + "-" + pk)+"' WHERE key_subject_pk = "+pk);
        }catch (SQLException e){
            message.setMessage("Неверные значения имени дисциплины");
        }
    }

    /**
     * Внос изменений в subjects
     */
    private void updateSubjects(HttpServletRequest request,ResultSet rs, int pk)  {
        try{
            Statement st = connection.createStatement();
            if (!rs.getString("key_department_fk").equals(request.getParameter(idepartment + "-" + pk)))
                st.execute("UPDATE subjects SET key_department_fk = " + request.getParameter(idepartment + "-" + pk) + " WHERE key_subject_pk = " + pk);
            try {
                int exm = Integer.parseInt(request.getParameter(iexams + "-" + pk));
                int setf = Integer.parseInt(request.getParameter(isetoff + "-" + pk));
                if (rs.getInt("exams_s") != exm) {
                    if (exm > -1 && exm < 9) {
                        st.execute("UPDATE subjects SET exams_s = '" + exm + "' WHERE key_subject_pk = " + pk);
                        st.execute("UPDATE subject_assignment SET semester_num_sa = '" + exm + "' WHERE key_subject_fk = " + pk);
                        st.execute("UPDATE subjects SET setoff_s = '" + 0 + "' WHERE key_subject_pk = " + pk);
                    } else message.setMessage("Семестр должен быть от 0 до 8");
                }
                if (rs.getInt("setoff_s") != setf) {
                    if (setf > -1 && setf < 9) {
                        st.execute("UPDATE subjects SET setoff_s = '" + setf + "' WHERE key_subject_pk = " + pk);
                        st.execute("UPDATE subject_assignment SET semester_num_sa = '" + setf + "' WHERE key_subject_fk = " + pk);
                        st.execute("UPDATE subjects SET exams_s = '" + 0 + "' WHERE key_subject_pk = " + pk);
                    } else message.setMessage("Семестр должен быть от 0 до 8");
                }
            } catch (NumberFormatException e) {
                message.setMessage("Неверное значение параметров экзаменов или зачётов");
            }
            if (!rs.getString("bsr_s").equals(request.getParameter(ibsr + "-" + pk)))
                st.execute("UPDATE subjects SET bsr_s = " + request.getParameter(ibsr + "-" + pk) + " WHERE key_subject_pk = " + pk);
        }catch (SQLException e){
            message.setMessage("Ошибка при обновлении значений в дисциплинах");
        }
    }

    /**
     * Внос изменений в assignment_subjects
     */
    private void updateSubjectAssign(HttpServletRequest request, ResultSet rs, int pk) {
        try{
            Statement st = connection.createStatement();
            ResultSetMetaData rsmd = rs.getMetaData();
            int j = 0;
            int k = bSubAssign;
            for (int i = 17; i < rsmd.getColumnCount(); i++) {
                if (rs.getString(i) != null) {
                    if (!rs.getString(i).equals(request.getParameter(k + "-" + pk))) {
                        st.execute("UPDATE subject_assignment SET " + subjectsNamesAsgn[j] + " = '" + request.getParameter(k + "-" + pk)
                                + "' WHERE key_subject_fk = " + pk);
                    }
                    k++;
                    j++;
                }
            }
        }catch (SQLException e){
            message.setMessage("Ошибка при обновлении значений в семестрах");
        }
    }
}
