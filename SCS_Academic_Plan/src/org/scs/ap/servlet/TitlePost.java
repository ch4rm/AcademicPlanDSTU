package org.scs.ap.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

import static org.scs.ap.servlet.Login.db;

@WebServlet(name = "TitlePost")
public class TitlePost extends HttpServlet {
    Connection connection=db.getConnection();
    Statement st;
    Statement upSt;
    /**
     * Обновляем титул
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            st = connection.createStatement();
            upSt = connection.createStatement();
            placeHead(request);
            placeMatrixShedulesUp(request);
            placePractics(request);
            placeStateAttestaion(request);
            st.close();
            upSt.executeBatch();
            upSt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/title.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Вносим изменения в вверхний титул
     */
    private void placeHead(HttpServletRequest request) throws SQLException{
        ResultSet resultSet = st.executeQuery("SELECT * FROM titles");
        while (resultSet.next()) {
            if (!request.getParameter("yearCreation").equals(resultSet.getString("year_creation")))
                upSt.addBatch("UPDATE titles SET year_creation = " + request.getParameter("yearCreation"));
            if (!request.getParameter("qualification").equals(resultSet.getString("qualification")))
                upSt.addBatch("UPDATE titles SET qualification = '" + request.getParameter("qualification") + "'");
            if (!request.getParameter("termsEducation").equals(resultSet.getString("terms_education")))
                upSt.addBatch("UPDATE titles SET terms_education = " + request.getParameter("termsEducation"));
            if (!request.getParameter("yearReception").equals(resultSet.getString("year_reception")))
                upSt.addBatch("UPDATE titles SET year_reception = " + request.getParameter("yearReception"));
            if (!request.getParameter("lvlEducation").equals(resultSet.getString("lvl_education")))
                upSt.addBatch("UPDATE titles SET lvl_education = '" + request.getParameter("lvlEducation") + "'");
            if (!request.getParameter("formEducation").equals(resultSet.getString("form_education")))
                upSt.addBatch("UPDATE titles SET form_education = '" + request.getParameter("formEducation") + "'");
        }
        resultSet = st.executeQuery("SELECT * FROM profiles");
        while (resultSet.next()) {
            if (!request.getParameter("name_prof").equals(resultSet.getString("name_prof")))
                upSt.addBatch("UPDATE profiles SET name_prof = '" + request.getParameter("name_prof")+"'");
        }
        resultSet.close();
    }

    /**
     * Вносим изменения в таблицу - график учебного процесса
     */
    private void placeMatrixShedulesUp(HttpServletRequest request) throws SQLException{
        ResultSet resultSet = st.executeQuery("SELECT * FROM matrix_schedules_ap ORDER BY key_matr_sch_ap_pk");
        int i=0;
        while (resultSet.next()) {
            if (!request.getParameter("week_msa"+i).equals(resultSet.getString("week_msa")))
                upSt.addBatch("UPDATE matrix_schedules_ap SET week_msa = " + request.getParameter("week_msa" + i)
                        + " WHERE key_matr_sch_ap_pk = " + (i+1));
            if(!request.getParameter("date_start"+i).equals(resultSet.getString("date_start")))
                upSt.addBatch("UPDATE matrix_schedules_ap SET date_start = " + request.getParameter("date_start" + i)
                        + " WHERE key_matr_sch_ap_pk = " + (i+1));
            if(!request.getParameter("date_end"+i).equals(resultSet.getString("date_end")))
                upSt.addBatch("UPDATE matrix_schedules_ap SET date_end = " + request.getParameter("date_end" + i)
                        + " WHERE key_matr_sch_ap_pk = " + (i+1));
            if(i>50) break;
            i++;
        }
        resultSet = st.executeQuery("SELECT * FROM matrix_schedules_ap ORDER BY key_matr_sch_ap_pk");
        i=0;
        while (resultSet.next()) {
            if(!request.getParameter("label_msa" + i).equals(resultSet.getString("label_msa"))) {
                if(request.getParameter("label_msa" + i).equals("")&&resultSet.getString("label_msa")==null) {}
                else if(request.getParameter("label_msa" + i).equals("")&&resultSet.getString("label_msa")!=null)
                    upSt.addBatch("UPDATE matrix_schedules_ap SET label_msa = null WHERE key_matr_sch_ap_pk = " + (i + 1));
                else
                    upSt.addBatch("UPDATE matrix_schedules_ap SET label_msa = '" + request.getParameter("label_msa" + i)
                            + "' WHERE key_matr_sch_ap_pk = " + (i + 1));
            }
            if(i>206) break;
            i++;
        }
        resultSet.close();
    }

    /**
     * Вносим изменения в таблицу практики
     */
    private void placePractics(HttpServletRequest request) throws SQLException {
        ResultSet resultSet = st.executeQuery("SELECT * FROM pract_types ORDER BY key_pract_pk");
        int i=0;
        while (resultSet.next()) {
            if (!request.getParameter("name_pt"+i).equals(resultSet.getString("name_pt")))
                upSt.addBatch("UPDATE pract_types SET name_pt = '" + request.getParameter("name_pt" + i)
                        + "' WHERE key_pract_pk = " + (i+1));
            i++;
        }
        resultSet = st.executeQuery("SELECT * FROM pract ORDER BY key_pract_pk");
        i=0;
        while (resultSet.next()) {
            if (!request.getParameter("key_week_count"+i).equals(resultSet.getString("key_week_count")))
                upSt.addBatch("UPDATE pract SET key_week_count = " + request.getParameter("key_week_count" + i)
                        + " WHERE key_pract_pk = " + (i+1));
            i++;
        }
        resultSet.close();
    }

    /**
     * Вносим изменения в таблицу Государственной Аттестации
     */
    private void placeStateAttestaion(HttpServletRequest request) throws SQLException{
        ResultSet resultSet = st.executeQuery("SELECT * FROM state_certification_types ORDER BY key_sc_pk");
        int i=0;
        while (resultSet.next()) {
            if (!request.getParameter("form_sct"+i).equals(resultSet.getString("form_sct")))
                upSt.addBatch("UPDATE state_certification_types SET form_sct = '" + request.getParameter("form_sct" + i)
                        + "' WHERE key_sc_pk = " + (i+1));
            if (!request.getParameter("name_sct"+i).equals(resultSet.getString("name_sct")))
                upSt.addBatch("UPDATE state_certification_types SET name_sct = '" + request.getParameter("name_sct" + i)
                        + "' WHERE key_sc_pk = " + (i+1));
            i++;
        }
        resultSet = st.executeQuery("SELECT * FROM state_certification ORDER BY key_sc_pk");
        i=0;
        while (resultSet.next()) {
            if (!request.getParameter("semester_sc"+i).equals(resultSet.getString("semester_sc")))
                upSt.addBatch("UPDATE state_certification SET semester_sc = " + request.getParameter("semester_sc" + i)
                        + " WHERE key_sc_pk = " + (i+1));
            i++;
        }
        resultSet.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        RequestDispatcher dispatcher = request.getRequestDispatcher("title.jsp");
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
    }
}

