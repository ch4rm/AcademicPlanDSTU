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
import java.util.ArrayList;

import static org.scs.ap.servlet.Login.db;

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
        int part = Integer.parseInt(request.getParameter("part-name"));
        String names = request.getParameter("dist-name");
        int dep = Integer.parseInt(request.getParameter("dep-name"));
        int sem = Integer.parseInt(request.getParameter("sem-num"));
        try {
            Connection connection = db.getConnection();
            Statement st = connection.createStatement();
            //Номер цикла в базе
            ResultSet rs = st.executeQuery("SELECT key_cycle_let FROM cycles");
            int cycleNum=1;
            while(rs.next()) {
                if (cyclen.equals(rs.getString(1))) break;
                cycleNum++;
            }
            if(cycleNum==0)
                throw new Exception();
            //Номер части в базе
            rs = st.executeQuery("SELECT key_parts_pk FROM parts WHERE key_cycle_fk = "+cycleNum);
            ArrayList<Integer> keyPartsPk = new ArrayList<>();
            while(rs.next())
                keyPartsPk.add(rs.getInt(1));
            part=keyPartsPk.get(part-1);
            //Масимальный номер части
            rs = st.executeQuery("SELECT MAX(key_subject) FROM subjects WHERE key_parts_fk = "+part);
            String keySubject="";
            while(rs.next())
                keySubject = rs.getString(1);
            rs.close();

            //st.executeUpdate("INSERT INTO ");
            //st.executeUpdate("INSERT INTO ");
            System.out.println(cycleNum);
            System.out.println(part);
            System.out.println(names);
            System.out.println(dep);
            System.out.println(sem);
            System.out.println(keySubject);

            st.close();
        }catch (Exception e){

        }
    }
}
