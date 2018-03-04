<%@ page import="org.scs.ap.content.SubjectGenerate" %>
<%@ page import="org.scs.ap.database.Database" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="org.scs.ap.view.Config" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 14.01.2018
  Time: 21:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String backColorHead="background: #CEDFF2";
    String backColorCols="background: #e6eefb";
    Database database = new Database();
    Connection connection = database.getConnection();
    SubjectGenerate hse = new SubjectGenerate(connection,backColorHead, backColorCols, 1);
    Config cfg = new Config();
    ArrayList<String> content = cfg.getArrayXml("table-hmp");
%>
<html>
<head>
    <title>ГСЭ</title>
    <LINK REL="StyleSheet" HREF="<%=request.getContextPath()%>/styles/style.css" TYPE="text/css">
</head>
<%@ include file="top-container.jsp" %>
<form action="/subject" method="POST">
    <%  String cycle="cycle";
        session.setAttribute("cycle", cycle);
    %>
    <div id="cont-main-block" style="height: 1100px">
        <table id="cont-table" style="width: 100%; text-align: left; font-size: 11pt;" class = "title-table">
            <tr>
                <td rowspan="4" style="width: 90px"><%=content.get(0)%></td>
                <td rowspan="4" style="width: 300px"><%=content.get(1)%></td>
                <td rowspan="4" style="width: 40px" class="rotatable"><%=content.get(2)%></td>
                <td rowspan="4" class="rotatable"><%=content.get(3)%></td>
                <td rowspan="4" class="rotatable"><%=content.get(4)%></td>
                <td rowspan="4" class="rotatable"><%=content.get(5)%></td>
                <td rowspan="4" class="rotatable"><%=content.get(6)%></td>
                <td colspan="7"><%=content.get(7)%></td>
                <td colspan="32">
                    <%=content.get(8)%>
                </td>
            </tr>
            <%=hse.headTable()%>
            <tr>
                <td colspan="46" style="<%=backColorHead%>"><%=hse.getCycle(1)%></td>
            </tr>
            <tr>
                <td colspan="46" style="font-weight:bolder; <%=backColorHead%>"><%=hse.getParts(1)%></td>
            </tr>
            <%=hse.getSubjects(1)%>
            <tr>
                <td colspan="46" style="font-weight:bolder; <%=backColorHead%>"><%=hse.getParts(2)%></td>
            </tr>
            <%=hse.getSubjects(2)%>
        </table>
    </div>
    <div style="width: 1800px; height: 100px; bottom:0;">
        <input type="submit" name="submit" class="save-button" value="Сохранить"/>
    </div>
    <% connection.close(); %>
</form>
<%@ include file="bottom-container.jsp" %>