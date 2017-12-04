package org.scs.ap.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Direct extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        }
        /*response.setContentType("text/html"); //Задаем формат ответа - HTML, текст
        PrintWriter out = response.getWriter(); //Получаем объект, позволяющий записать контент в ответ
        out.write("<!DOCTYPE html>\n" + // Записываем в ответ HTML код простейшей странички
                "<html>\n" +
                "<head><title>K P A C U B O</title></head>\n" +
                "<body bgcolor=\"#fdf5e6\">\n" +
                "<h1>TUPA:</h1>\n" +
                "<p>Perviy syjt na Java</p>\n" +
                "</body></html>");*/
    }
}