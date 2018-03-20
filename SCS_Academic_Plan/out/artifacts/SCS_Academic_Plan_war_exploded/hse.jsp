<%@ page import="org.scs.ap.content.SubjectGenerate" %>
<%@ page import="org.scs.ap.view.Config" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="static org.scs.ap.servlet.Login.db" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 14.01.2018
  Time: 21:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String backColorHead="background: #CEDFF2";
    int cycle = 1;
    int parts[] = {1, 2};

    Statement statement = db.getConnection().createStatement();
    statement.execute("SELECT create_sub("+cycle+");");
    SubjectGenerate hse = new SubjectGenerate(db.getConnection(),backColorHead);
    Config cfg = new Config();
    ArrayList<String> content = cfg.getArrayXml("table-hmp");
    statement.close();
%>
<html>
<head>
    <title>ГСЭ</title>
    <LINK REL="StyleSheet" HREF="<%=request.getContextPath()%>/styles/style.css" TYPE="text/css">
</head>
<%@ include file="top-container.jsp" %>
<%@ include file="popup.jsp" %>
<form action="/hse" method="POST">
    <div id="cont-main-block" style="height: 1100px">
        <table id="cont-table" style="width: 100%; text-align: left; font-size: 11pt;" class = "hmp-table">
            <tr>
                <td rowspan="4" style="width: 90px"><%=content.get(0)%></td>
                <td rowspan="4" style="width: 300px"><%=content.get(1)%></td>
                <td rowspan="4" style="width: 55px" class="rotatable"><%=content.get(2)%></td>
                <td rowspan="4" class="rotatable"><%=content.get(3)%></td>
                <td rowspan="4" class="rotatable"><%=content.get(4)%></td>
                <td rowspan="4" class="rotatable"><%=content.get(5)%></td>
                <td rowspan="4" style="width: 25px" class="rotatable"><%=content.get(6)%></td>
                <td colspan="7" style="width: 270px"><%=content.get(7)%></td>
                <td colspan="32">
                    <%=content.get(8)%>
                </td>
            </tr>
            <%=hse.headTable()%>
            <tr>
                <td colspan="46" style="<%=backColorHead%>"><%=hse.getCycle(cycle)%></td>
            </tr>
            <tr>
                <td colspan="46" style="font-weight:bolder; <%=backColorHead%>"><%=hse.getParts(parts[0])%></td>
            </tr>
            <%=hse.getSubjects(parts[0])%>
            <tr>
                <td colspan="46" style="font-weight:bolder; <%=backColorHead%>"><%=hse.getParts(parts[1])%></td>
            </tr>
            <%=hse.getSubjects(parts[1])%>
            <%=hse.summPage()%>
        </table>
    </div>
    <div style="width: 1800px; height: 100px; bottom:0;">
        <input type="submit" name="submit" class="save-button" value="Сохранить"/>
        <input type="button" name="add" class="save-button addb" value="Добавить" onclick="addCol();"/>
        <input type="button" name="remove" class="save-button remove" value="Удалить" onclick="delCol();"/>
    </div>
</form>
<%@ include file="bottom-container.jsp" %>