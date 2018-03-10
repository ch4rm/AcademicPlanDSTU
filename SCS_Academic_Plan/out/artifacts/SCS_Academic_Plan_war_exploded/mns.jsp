<%@ page import="org.scs.ap.content.SubjectGenerate" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="org.scs.ap.database.Database" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.scs.ap.view.Config" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 14.01.2018
  Time: 21:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String backColorHead="background: #FFD0C6";
    Database database = new Database();
    Connection connection = database.getConnection();
    SubjectGenerate men = new SubjectGenerate(connection, backColorHead);
    Config cfg = new Config();
    ArrayList<String> content = cfg.getArrayXml("table-hmp");
%>
<html>
<head>
    <title>МЕН</title>
    <LINK REL="StyleSheet" HREF="<%=request.getContextPath()%>/styles/style.css" TYPE="text/css">
</head>
<%@ include file="top-container.jsp" %>
<form>
    <div id="cont-main-block" style="height: 1100px">
        <table id="cont-table" style="width: 100%; text-align: left; font-size: 11pt;" class = "hmp-table">
            <tr>
                <td rowspan="4" style="width: 90px"><%=content.get(0)%></td>
                <td rowspan="4" style="width: 300px"><%=content.get(1)%></td>
                <td rowspan="4" style="width: 50px" class="rotatable"><%=content.get(2)%></td>
                <td rowspan="4" class="rotatable"><%=content.get(3)%></td>
                <td rowspan="4" class="rotatable"><%=content.get(4)%></td>
                <td rowspan="4" class="rotatable"><%=content.get(5)%></td>
                <td colspan="7"><%=content.get(6)%></td>
                <td colspan="32">
                    <%=content.get(7)%>
                </td>
            </tr>
            <%=men.headTable()%>
            <tr>
                <td colspan="45" style="<%=backColorHead%>"><%=men.getCycle(2)%></td>
            </tr>
            <tr>
                <td colspan="45" style="font-weight:bolder; <%=backColorHead%>"><%=men.getParts(3)%></td>
            </tr>
            <%=men.getSubjects(3)%>
            <tr>
                <td colspan="45" style="font-weight:bolder; <%=backColorHead%>"><%=men.getParts(4)%></td>
            </tr>
            <%=men.getSubjects(4)%>
            <%=men.summPage(2)%>
        </table>
    </div>
    <div style="width: 1800px; height: 100px; bottom:0;">
        <input type="submit" name="submit" class="save-button" value="Сохранить"/>
    </div>
    <% connection.close(); %>
</form>
<%@ include file="bottom-container.jsp" %>