<%@ page import="org.scs.ap.content.Assignment" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="org.scs.ap.database.Database" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 14.01.2018
  Time: 21:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Database database = new Database();
    Connection connection = database.getConnection();
    Assignment asmt = new Assignment(connection, 2);
%>
<html>
<head>
    <title>МЕН</title>
    <LINK REL="StyleSheet" HREF="<%=request.getContextPath()%>/styles/style.css" TYPE="text/css">
</head>
<%@ include file="top-container.jsp" %>
<form>
    <div style="height: 1100px">
        <table style="width: 100%; text-align: left; font-size: 11pt;" class = "title-table">
            <tr>
                <td rowspan="4" style="width: 90px">№ п/п</td>
                <td rowspan="4" style="width: 300px">Название дисципилины</td>
                <td rowspan="4" style="width: 40px" class="rotatable">Шифр кафедры</td>
                <td rowspan="4" class="rotatable">з.е</td>
                <td rowspan="4" class="rotatable">Экзамены</td>
                <td rowspan="4" class="rotatable">Зачёты</td>
                <td rowspan="4" class="rotatable">Общий объём</td>
                <td colspan="7">Часы</td>
                <td colspan="32">
                    Распределение по курсам, семестрам и неделям
                </td>
            </tr>
            <%=asmt.headTable()%>
            <tr>
                <td colspan="46" style="background: #dbe9f8"><%=asmt.getCycle(2)%></td>
            </tr>
            <tr>
                <td colspan="46" style="font-weight:bolder; background: #dbe9f8"><%=asmt.getParts(3)%></td>
            </tr>
            <%=asmt.getSubjects(3)%>
            <tr>
                <td colspan="46" style="font-weight:bolder; background: #dbe9f8"><%=asmt.getParts(4)%></td>
            </tr>
            <%=asmt.getSubjects(4)%>
        </table>
    </div>
    <div style="width: 1800px; height: 100px; bottom:0;">
        <input type="submit" name="submit" class="save-button" value="Сохранить"/>
    </div>
    <% connection.close(); %>
</form>
<%@ include file="bottom-container.jsp" %>