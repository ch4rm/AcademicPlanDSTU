package org.scs.ap.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by User on 11.03.2018.
 */
@WebServlet(name = "ExitPost")
public class ExitPost extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        Session sess = new Session();
        sess.setAcces(-1);
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
    }
}
