package org.scs.ap.servlet.insert;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by User on 11.03.2018.
 */
@WebServlet(name = "AddLineHSE")
public class AddLineMNS extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        AddLineHSE hse = new AddLineHSE();
        hse.postAction(request, response);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/mns.jsp");
        dispatcher.forward(request, response);
    }



}
