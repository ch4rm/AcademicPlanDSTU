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
     * Заполняем титул
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        //Шапка
        updateHead("year_creation", request.getParameter("yearCreation"));
        updateHead("qualification", "'"+request.getParameter("qualification")+"'");
        updateHead("terms_education", request.getParameter("termsEducation"));
        updateHead("year_reception", request.getParameter("yearReception"));
        updateHead("lvl_education", "'"+request.getParameter("lvlEducation")+"'");
        updateHead("form_education", "'"+request.getParameter("formEducation")+"'");
        //Недели
        /*for(int i =0; i<52;i++) {
            updateTable("matrix_schedules_ap", "week_msa", request.getParameter("week_msa"+i), "key_matr_sch_ap_pk", i+1);

        }*/
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/title.jsp");
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        RequestDispatcher dispatcher = request.getRequestDispatcher("title.jsp");
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
    }

    private void updateHead(String row, String value){
        Database database = new Database();
        Statement statement;
        ResultSet resultSet;
        Connection connection = database.getConnection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("UPDATE titles SET "+row+" = "+value);
            resultSet.close();
            statement.close();
            connection.close();
        }catch (SQLException e){
            new RuntimeException(e);
        }
    }

    /*private void updateTable(String table, String row, String value, String pc, int id){
        Database database = new Database();
        Statement statement;
        ResultSet resultSet;
        Connection connection = database.getConnection();
        try {
            statement = connection.createStatement();
            System.out.println("UPDATE "+ table +" SET "+row+" = "+value + " WHERE " + pc + " = " + id);
            resultSet = statement.executeQuery("UPDATE "+ table +" SET "+row+" = "+value + " WHERE " + pc + " = " + id);
            resultSet.close();
            statement.close();
            connection.close();
        }catch (SQLException e){
            new RuntimeException(e);
        }
    }*/

    /*private void updateSheduleProcess(String row, String value){
        Database database = new Database();
        Statement statement;
        ResultSet resultSet;
        Connection connection = database.getConnection();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("UPDATE matrix_schedules_ap SET "+row+" = "+value);
            resultSet.close();
            statement.close();
            connection.close();
        }catch (SQLException e){
            new RuntimeException(e);
        }
    }*/
}
