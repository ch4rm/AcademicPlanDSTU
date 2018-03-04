package org.scs.ap.servlet;

import org.scs.ap.database.Database;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by User on 04.03.2018.
 */
@WebServlet(name = "SubjectPost")
public class SubjectPost extends HttpServlet {
    Database database;
    Connection connection;
    TitlePost titlePost;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        database = new Database();
        connection = database.getConnection();
        titlePost = new TitlePost();
        try{
            //updateSubjects(request);
            //System.out.println(request.getAttribute("cycle").toString());
            connection.close();
        }catch(SQLException e){}
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/hse.jsp");
        dispatcher.forward(request, response);
    }

    private void updateSubjects(HttpServletRequest request) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM subjects WHERE key_cycle_fk =1");
        while(resultSet.next()){
            int pk = resultSet.getInt("key_subject_pk");

        }
        resultSet.close();
        statement.close();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("cycle").toString());
    }

    /**
     * Функция реализации запроса
     * @param row - строка запроса
     */
    private void update(String row){
        titlePost.update(row);
    }
}
