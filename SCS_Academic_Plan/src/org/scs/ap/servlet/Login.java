package org.scs.ap.servlet;

import org.scs.ap.database.Database;
import org.scs.ap.view.Config;
import org.scs.ap.view.Message;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "Login")
public class Login extends HttpServlet {
    public static Database db = new Database();
    private Message message = new Message();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Config config = new Config("logins.xml");
        ArrayList<String> logins = config.getArrayXml("login");
        String iLogin = request.getParameter("login");
        int i=0;
        boolean success = false;
        for(String s : logins) {
            if (iLogin.equals(s)) {
                ArrayList<String> passwords = config.getArrayXml("password");
                String iPassword = request.getParameter("password");
                if(iPassword.equals(passwords.get(i))){
                    success = true;
                    ArrayList<String> acceses = config.getArrayXml("acces");
                    Session session = new Session();
                    session.setAcces(Integer.parseInt(acceses.get(i)));
                    message.getMessage();
                    toTitle(request, response);
                }else{
                    message.setMessage("Неверно введён пароль");
                    toLogin(request, response);
                }
            }
            i++;
        }
        if(!success) {
            message.setMessage("Неверно введён логин");
            toLogin(request, response);
        }
    }

    private void toTitle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/title.jsp");
        dispatcher.forward(request, response);
    }

    private void toLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/login.jsp");
        dispatcher.forward(request, response);
    }
}

