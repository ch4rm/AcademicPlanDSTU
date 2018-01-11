package org.scs.ap.servlet;

import org.scs.ap.database.Database;
import org.scs.ap.view.HTML;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Title extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        RequestDispatcher dispatcher = request.getRequestDispatcher("title.jsp");
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }


        /*
        Database database = new Database();
        PrintWriter out = response.getWriter();
        out.write("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head><title>титул</title></head>\n" +
                "<body>\n" +
                "<p>" + database.sql("SELECT * FROM public.subjects") + "</p>\n" +
                "</body></html>");*/
    }
}