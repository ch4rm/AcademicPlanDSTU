<%@ page import="org.scs.ap.content.HSE" %>
<%@ page import="org.scs.ap.database.Database" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 14.01.2018
  Time: 21:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Database database = new Database();
    HSE hse = new HSE(database);
%>
<html>
<head>
    <title>ГСЭ</title>
    <LINK REL="StyleSheet" HREF="<%=request.getContextPath()%>/styles/style.css" TYPE="text/css">
</head>
<%@ include file="top-container.jsp" %>
<form>
    <div style="height: 1100px">
        <table style="width: 100%; text-align: center;" class = "title-table">
            <tr>
                <td rowspan="5">№ п/п</td>
                <td rowspan="5" style="width: 150px">Название дисцпилины</td>
                <td rowspan="5" class="rotatable">Шифр кафедры</td>
                <td rowspan="5" class="rotatable">з.е</td>
                <td rowspan="5" class="rotatable">Экзамены</td>
                <td rowspan="5" class="rotatable">Зачёты</td>
                <td rowspan="5" class="rotatable">Общий объём</td>
                <td colspan="7">Часы</td>
                <td colspan="32">
                    Распределение по курсам, семестрам и неделям
                </td>
            </tr>
            <%=hse.headTable()%>
        </table>
    </div>
    <div style="width: 1800px; height: 100px; bottom:0;">
        <input type="submit" name="submit" class="save-button" value="Сохранить"/>
    </div>
</form>
<%@ include file="bottom-container.jsp" %>