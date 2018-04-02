package org.scs.ap.servlet.insert;

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

import static org.scs.ap.servlet.Login.db;

/**
 * Created by User on 11.03.2018.
 */
@WebServlet(name = "AddLineHSE")
public class  AddLineHSE extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        postAction(request, response);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/hse.jsp");
        dispatcher.forward(request, response);
    }

    public void postAction(HttpServletRequest request, HttpServletResponse response){
        String cyclen = request.getParameter("cycle-in");
        int part = Integer.parseInt(request.getParameter("part-name"));
        String dname = request.getParameter("dist-name");
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
            //Масимальный номер части
            rs = st.executeQuery("SELECT MAX(key_subject) FROM subjects WHERE key_parts_fk = "+part);
            String keySubjectS="";
            while(rs.next())
                keySubjectS = rs.getString(1);
            int keySubject=(int)Double.parseDouble(keySubjectS)+1;
            //в таблицу subject
            st.executeUpdate("INSERT INTO subjects (key_subject, key_ap_fk, key_cycle_fk, key_parts_fk, name_s, " +
                    "key_department_fk, exams_s, setoff_s, lect_s, lab_s, pract_s, ksr_s, bsr_s) " +
                    "VALUES("+keySubject+", 1, "+cycleNum+", "+part+", '"+dname+"', "+dep+
                    ", 0, 0, 0, 0, 0, 0, 0)");
            rs =st.executeQuery("SELECT key_subject_pk FROM subjects WHERE key_subject = "+
                    keySubject+" AND name_s = '"+dname+"' AND key_cycle_fk = " + cycleNum +
                    " AND key_parts_fk = "+part);
            long fk=0;
            while(rs.next())
                fk = rs.getLong(1);
            rs.close();
            //в таблицу subject_assignment
            st.executeUpdate("INSERT INTO subject_assignment (key_subject_fk, course_num_sa, semester_num_sa, " +
                    "key_type_fk, hour_lec_sa, hour_lab_sa, hour_prac_sa, hour_self_sa, hour_exam_sa) " +
                    "VALUES("+fk+", 0, "+sem+", "+cycleNum+", 0, 0, 0, 0, 0)");
            st.close();
        }catch (Exception e){
            System.out.println("Ошибка при добавлении строки subject и subject_assignment");
        }
    }
}
