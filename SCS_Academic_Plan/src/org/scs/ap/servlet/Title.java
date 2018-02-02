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

/**
 * Created by User on 14.01.2018.
 */
@WebServlet(name = "Title")
public class Title extends HttpServlet {
    /**
     * Обновляем титул
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            placeHead(request);
            placeMatrixShedulesUp(request);
            placePractics(request);
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
        Database database = new Database();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM titles");
        while (resultSet.next()) {
            if (!request.getParameter("yearCreation").equals(resultSet.getString("year_creation")))
                update("UPDATE titles SET year_creation = " + request.getParameter("yearCreation"));
            if (!request.getParameter("qualification").equals(resultSet.getString("qualification")))
                update("UPDATE titles SET qualification = '" + request.getParameter("qualification") + "'");
            if (!request.getParameter("termsEducation").equals(resultSet.getString("terms_education")))
                update("UPDATE titles SET terms_education = " + request.getParameter("termsEducation"));
            if (!request.getParameter("yearReception").equals(resultSet.getString("year_reception")))
                update("UPDATE titles SET year_reception = " + request.getParameter("yearReception"));
            if (!request.getParameter("lvlEducation").equals(resultSet.getString("lvl_education")))
                update("UPDATE titles SET lvl_education = '" + request.getParameter("lvlEducation") + "'");
            if (!request.getParameter("formEducation").equals(resultSet.getString("form_education")))
                update("UPDATE titles SET form_education = '" + request.getParameter("formEducation") + "'");
        }
        statement.close();
        connection.close();
        resultSet.close();
    }

    /**
     * Вносим изменения в таблицу - график учебного процесса
     */
    private void placeMatrixShedulesUp(HttpServletRequest request) throws SQLException{
        Database database = new Database();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM matrix_schedules_ap ORDER BY key_matr_sch_ap_pk");
        int i=0;
        while (resultSet.next()) {
            if (!request.getParameter("week_msa"+i).equals(resultSet.getString("week_msa")))
                update("UPDATE matrix_schedules_ap SET week_msa = " + request.getParameter("week_msa" + i)
                        + " WHERE key_matr_sch_ap_pk = " + (i+1));
            if(!request.getParameter("date_start"+i).equals(resultSet.getString("date_start")))
                update("UPDATE matrix_schedules_ap SET date_start = " + request.getParameter("date_start" + i)
                        + " WHERE key_matr_sch_ap_pk = " + (i+1));
            if(!request.getParameter("date_end"+i).equals(resultSet.getString("date_end")))
                update("UPDATE matrix_schedules_ap SET date_end = " + request.getParameter("date_end" + i)
                        + " WHERE key_matr_sch_ap_pk = " + (i+1));
            if(i>50) break;
            i++;
        }
        resultSet = statement.executeQuery("SELECT * FROM matrix_schedules_ap ORDER BY key_matr_sch_ap_pk");
        i=0;
        while (resultSet.next()) {
            if(!request.getParameter("label_msa" + i).equals(resultSet.getString("label_msa"))) {
                if(request.getParameter("label_msa" + i).equals("")&&resultSet.getString("label_msa")==null) {}
                else if(request.getParameter("label_msa" + i).equals("")&&resultSet.getString("label_msa")!=null)
                    update("UPDATE matrix_schedules_ap SET label_msa = null WHERE key_matr_sch_ap_pk = " + (i + 1));
                else
                    update("UPDATE matrix_schedules_ap SET label_msa = '" + request.getParameter("label_msa" + i)
                            + "' WHERE key_matr_sch_ap_pk = " + (i + 1));
            }
            if(i>206) break;
            i++;
        }
        statement.close();
        connection.close();
        resultSet.close();
    }

    private void placePractics(HttpServletRequest request) throws SQLException {
        Database database = new Database();
        Connection connection = database.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM pract_types ORDER BY key_pract_pk");
        int i=0;
        while (resultSet.next()) {

        }
        statement.close();
        connection.close();
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

    /**
     * Функция реализации запроса
     * @param row - строка запроса
     */
    private void update(String row){
        Database database = new Database();
        Connection connection = database.getConnection();
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(row);
            resultSet.close();
            statement.close();
            connection.close();
        }catch (SQLException e){
            new RuntimeException(e);
        }

    }
}

