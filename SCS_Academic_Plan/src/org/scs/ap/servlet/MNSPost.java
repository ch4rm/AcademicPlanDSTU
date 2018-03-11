package org.scs.ap.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by User on 04.03.2018.
 */
@WebServlet(name = "MNSPost")
public class MNSPost extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HSEPost hs = new HSEPost();
        hs.postAction(request, response, 2);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/mns.jsp");
        dispatcher.forward(request, response);
    }
}
