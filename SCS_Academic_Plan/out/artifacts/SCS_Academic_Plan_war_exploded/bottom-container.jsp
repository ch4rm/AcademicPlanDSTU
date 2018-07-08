<%@ page import="org.scs.ap.database.Database" %>
<%@ page import="org.scs.ap.content.CounterGenerate" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.scs.ap.view.Config" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 10.01.2018
  Time: 18:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Database database = new Database();
    Connection connection = database.getConnection();
    CounterGenerate cg = new CounterGenerate(connection);
    Config cfg1 = new Config();
    ArrayList<String> content1 = new ArrayList<>();
    content1 = cfg1.getArrayXml("footer");
%>
</div>
</div>
</div>
<div id="right-block" class="right-block">
    <button id="setting-but" onclick="setVisible()" class="right-button">></button>
    <table class = "counter-table" style = "width: 260px;">
        <tr><td colspan = 3>Ауд./Сам.</td></tr>
        <tr><td>Семестр</td><td>Аудиторн.</td><td>Самост.</td></tr>
        <tr><td>1c.</td><td><%= cg.getSumSemesterAud(1)%></td><td><%= cg.getSumSemesterSelf(1)%></td></tr>
        <tr><td>2c.</td><td><%= cg.getSumSemesterAud(2)%></td><td><%= cg.getSumSemesterSelf(2)%></td></tr>
        <tr><td>3c.</td><td><%= cg.getSumSemesterAud(3)%></td><td><%= cg.getSumSemesterSelf(3)%></td></tr>
        <tr><td>4c.</td><td><%= cg.getSumSemesterAud(4)%></td><td><%= cg.getSumSemesterSelf(4)%></td></tr>
        <tr><td>5c.</td><td><%= cg.getSumSemesterAud(5)%></td><td><%= cg.getSumSemesterSelf(5)%></td></tr>
        <tr><td>6c.</td><td><%= cg.getSumSemesterAud(6)%></td><td><%= cg.getSumSemesterSelf(6)%></td></tr>
        <tr><td>7c.</td><td><%= cg.getSumSemesterAud(7)%></td><td><%= cg.getSumSemesterSelf(7)%></td></tr>
        <tr><td>8c.</td><td><%= cg.getSumSemesterAud(8)%></td><td><%= cg.getSumSemesterSelf(8)%></td></tr>
    </table>
    <table class = "counter-table" style = "width: 280px;">
        <tr><td colspan = 5>ЭЗК</td></tr>
        <tr><td>Семестр</td><td>Э</td><td>З</td><td>КП</td><td>Кред.</td></tr>
        <tr><td>1с.</td><td><%= cg.getCountExams(1)%></td><td><%= cg.getCountSetoff(1)%></td><td><%= cg.getCountKurs(1)%></td><td><%= cg.getCountCredits(1)%></td></tr>
        <tr><td>2с.</td><td><%= cg.getCountExams(2)%></td><td><%= cg.getCountSetoff(2)%></td><td><%= cg.getCountKurs(2)%></td><td><%= cg.getCountCredits(2)%></td></tr>
        <tr><td>3с.</td><td><%= cg.getCountExams(3)%></td><td><%= cg.getCountSetoff(3)%></td><td><%= cg.getCountKurs(3)%></td><td><%= cg.getCountCredits(3)%></td></tr>
        <tr><td>4с.</td><td><%= cg.getCountExams(4)%></td><td><%= cg.getCountSetoff(4)%></td><td><%= cg.getCountKurs(4)%></td><td><%= cg.getCountCredits(4)%></td></tr>
        <tr><td>5с.</td><td><%= cg.getCountExams(5)%></td><td><%= cg.getCountSetoff(5)%></td><td><%= cg.getCountKurs(5)%></td><td><%= cg.getCountCredits(5)%></td></tr>
        <tr><td>6с.</td><td><%= cg.getCountExams(6)%></td><td><%= cg.getCountSetoff(6)%></td><td><%= cg.getCountKurs(6)%></td><td><%= cg.getCountCredits(6)%></td></tr>
        <tr><td>7с.</td><td><%= cg.getCountExams(7)%></td><td><%= cg.getCountSetoff(7)%></td><td><%= cg.getCountKurs(7)%></td><td><%= cg.getCountCredits(7)%></td></tr>
        <tr><td>8с.</td><td><%= cg.getCountExams(8)%></td><td><%= cg.getCountSetoff(8)%></td><td><%= cg.getCountKurs(8)%></td><td><%= cg.getCountCredits(8)%></td></tr>
    </table>
    <table class = "counter-table" style = "width: 150px;">
        <tr><td colspan = 2>БСР</td></tr>
        <tr><td>1с.</td><td><%= cg.getSumBsr(1)%></td></tr>
        <tr><td>2с.</td><td><%= cg.getSumBsr(2)%></td></tr>
        <tr><td>3с.</td><td><%= cg.getSumBsr(3)%></td></tr>
        <tr><td>4с.</td><td><%= cg.getSumBsr(4)%></td></tr>
        <tr><td>5с.</td><td><%= cg.getSumBsr(5)%></td></tr>
        <tr><td>6с.</td><td><%= cg.getSumBsr(6)%></td></tr>
        <tr><td>7с.</td><td><%= cg.getSumBsr(7)%></td></tr>
        <tr><td>8с.</td><td><%= cg.getSumBsr(8)%></td></tr>
        <tr><td>Всего</td><td><%= cg.getAllSumBsr()%></td></tr>
    </table>
</div>
<div class="footer">
    &copy <%=content1.get(0)%><br>
    <%=content1.get(1)%>
</div>
</div>
</body>
</html>