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

@WebServlet(name = "TitlePost")
public class TitlePost extends HttpServlet {
    private Message message = new Message();
    private Connection connection=db.getConnection();

    /**
     * Обновляем титул
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        postAction(request);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/title.jsp");
        dispatcher.forward(request, response);
    }

    private void postAction(HttpServletRequest request){
        try {
            placeHead(request);
            placeMatrixShedulesUp(request);
            placePractics(request);
            placeStateAttestaion(request);
        }catch (SQLException e){
            e.getStackTrace();
        }
    }

    /**
     * Вносим изменения в вверхний титул
     */
    private void placeHead(HttpServletRequest request) throws SQLException {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM titles");
        rs.next();
        if (!request.getParameter("yearCreation").equals(rs.getString("year_creation")))
            st.execute("UPDATE titles SET year_creation = " + request.getParameter("yearCreation"));
        else if (!request.getParameter("yearCreation1").equals(rs.getString("year_creation")))
            st.execute("UPDATE titles SET year_creation = " + request.getParameter("yearCreation1"));
        if (!request.getParameter("qualification").equals(rs.getString("qualification")))
            st.execute("UPDATE titles SET qualification = '" + request.getParameter("qualification") + "'");
        if (!request.getParameter("termsEducation").equals(rs.getString("terms_education")))
            st.execute("UPDATE titles SET terms_education = " + request.getParameter("termsEducation"));
        if (!request.getParameter("yearReception").equals(rs.getString("year_reception")))
            st.execute("UPDATE titles SET year_reception = " + request.getParameter("yearReception"));
        if (!request.getParameter("lvlEducation").equals(rs.getString("lvl_education")))
            st.execute("UPDATE titles SET lvl_education = '" + request.getParameter("lvlEducation") + "'");
        if (!request.getParameter("formEducation").equals(rs.getString("form_education")))
            st.execute("UPDATE titles SET form_education = '" + request.getParameter("formEducation") + "'");
        rs = st.executeQuery("SELECT * FROM profiles");
        rs.next();
        if (!request.getParameter("name_prof").equals(rs.getString("name_prof")))
            st.execute("UPDATE profiles SET name_prof = '" + request.getParameter("name_prof") + "'");
        rs.close();
        st.close();
    }

    /**
     * Вносим изменения в таблицу - график учебного процесса
     */
    private void placeMatrixShedulesUp(HttpServletRequest request) throws SQLException {
        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT * FROM matrix_schedules_ap ORDER BY key_matr_sch_ap_pk");
        int i = 0;
        while (resultSet.next()) {
            if (!request.getParameter("week_msa" + i).equals(resultSet.getString("week_msa")))
                st.execute("UPDATE matrix_schedules_ap SET week_msa = " + request.getParameter("week_msa" + i)
                        + " WHERE key_matr_sch_ap_pk = " + (i + 1));
            if (!request.getParameter("date_start" + i).equals(resultSet.getString("date_start")))
                st.execute("UPDATE matrix_schedules_ap SET date_start = " + request.getParameter("date_start" + i)
                        + " WHERE key_matr_sch_ap_pk = " + (i + 1));
            if (!request.getParameter("date_end" + i).equals(resultSet.getString("date_end")))
                st.execute("UPDATE matrix_schedules_ap SET date_end = " + request.getParameter("date_end" + i)
                        + " WHERE key_matr_sch_ap_pk = " + (i + 1));
            if (i > 50) break;
            i++;
        }
        resultSet = st.executeQuery("SELECT * FROM matrix_schedules_ap ORDER BY key_matr_sch_ap_pk");
        i = 0;
        while (resultSet.next()) {
            if (!request.getParameter("label_msa" + i).equals(resultSet.getString("label_msa"))) {
                if (request.getParameter("label_msa" + i).equals("") && resultSet.getString("label_msa") == null) {
                } else if (request.getParameter("label_msa" + i).equals("") && resultSet.getString("label_msa") != null)
                    st.execute("UPDATE matrix_schedules_ap SET label_msa = null WHERE key_matr_sch_ap_pk = " + (i + 1));
                else
                    st.execute("UPDATE matrix_schedules_ap SET label_msa = '" + request.getParameter("label_msa" + i)
                            + "' WHERE key_matr_sch_ap_pk = " + (i + 1));
            }
            if (i > 206) break;
            i++;
        }
        resultSet.close();
    }

    /**
     * Вносим изменения в таблицу практики
     */
    private void placePractics(HttpServletRequest request) throws SQLException {
        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT * FROM pract_types ORDER BY key_pract_pk");
        int i = 0;
        while (resultSet.next()) {
            if (!request.getParameter("name_pt" + i).equals(resultSet.getString("name_pt")))
                st.execute("UPDATE pract_types SET name_pt = '" + request.getParameter("name_pt" + i)
                        + "' WHERE key_pract_pk = " + (i + 1));
            i++;
        }
        resultSet = st.executeQuery("SELECT * FROM pract ORDER BY key_pract_pk");
        i = 0;
        while (resultSet.next()) {
            if (!request.getParameter("key_week_count" + i).equals(resultSet.getString("key_week_count")))
                st.execute("UPDATE pract SET key_week_count = " + request.getParameter("key_week_count" + i)
                        + " WHERE key_pract_pk = " + (i + 1));
            i++;
        }
        resultSet.close();
    }

    /**
     * Вносим изменения в таблицу Государственной Аттестации
     */
    private void placeStateAttestaion(HttpServletRequest request) throws SQLException {
        Statement st = connection.createStatement();
        ResultSet resultSet = st.executeQuery("SELECT * FROM state_certification_types ORDER BY key_sc_pk");
        int i = 0;
        while (resultSet.next()) {
            if (!request.getParameter("form_sct" + i).equals(resultSet.getString("form_sct")))
                st.execute("UPDATE state_certification_types SET form_sct = '" + request.getParameter("form_sct" + i)
                        + "' WHERE key_sc_pk = " + (i + 1));
            if (!request.getParameter("name_sct" + i).equals(resultSet.getString("name_sct")))
                st.execute("UPDATE state_certification_types SET name_sct = '" + request.getParameter("name_sct" + i)
                        + "' WHERE key_sc_pk = " + (i + 1));
            i++;
        }
        resultSet = st.executeQuery("SELECT * FROM state_certification ORDER BY key_sc_pk");
        i = 0;
        while (resultSet.next()) {
            if (!request.getParameter("semester_sc" + i).equals(resultSet.getString("semester_sc")))
                st.execute("UPDATE state_certification SET semester_sc = " + request.getParameter("semester_sc" + i)
                        + " WHERE key_sc_pk = " + (i + 1));
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

