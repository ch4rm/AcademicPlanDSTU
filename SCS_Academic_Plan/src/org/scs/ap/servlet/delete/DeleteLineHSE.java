package org.scs.ap.servlet.delete;

import org.scs.ap.view.Message;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.scs.ap.servlet.Login.db;

@WebServlet(name = "DeleteLineHSE")
public class DeleteLineHSE extends HttpServlet {
    Message message = new Message();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        postAction(request, response);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/hse.jsp");
        dispatcher.forward(request, response);
    }

    public void postAction(HttpServletRequest request, HttpServletResponse response){
        try {
            int part = Integer.parseInt(request.getParameter("part-name-cl"));
            double subjectNum = Double.parseDouble(request.getParameter("part-num-cl"));
            Connection connection = db.getConnection();
            Statement st = connection.createStatement();
            //ПК
            int pk = 0;
            ResultSet rs = st.executeQuery("SELECT key_subject_pk FROM subjects WHERE key_parts_fk = " + part + " AND key_subject = " + subjectNum);
            while(rs.next())
                pk = rs.getInt(1);
            st.executeUpdate("DELETE FROM subject_assignment WHERE key_subject_fk = " + pk);
            st.executeUpdate("DELETE FROM subjects WHERE key_subject_pk = " + pk);
        }catch(Exception e){
            message.setMessage("Введены неверные значения при удалении строки");
        }
    }
}
