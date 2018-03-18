package org.scs.ap.servlet;

import org.scs.ap.database.Database;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by User on 11.03.2018.
 */
@WebServlet(name = "AddColHSE")
public class AddColHSE extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        try {
            postAction(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/hse.jsp");
        dispatcher.forward(request, response);
    }

    public void postAction(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        String cyclen = request.getParameter("cycle-in");
        String part = request.getParameter("part-name");
        String names = request.getParameter("dist-name");
        String dep = request.getParameter("dep-name");
        try {
            int num = Integer.parseInt(request.getParameter("sem-num"));
            Database database = new Database();
            Connection connection = database.getConnection();
            Statement st = connection.createStatement();

            System.out.println(cyclen);
            System.out.println(part);
            System.out.println(names);
            System.out.println(dep);


            st.close();
            connection.close();
        }catch (Exception e){

        }
    }
}
