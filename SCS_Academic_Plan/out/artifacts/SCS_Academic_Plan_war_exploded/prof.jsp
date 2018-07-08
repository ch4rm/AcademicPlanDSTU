<%@ page import="org.scs.ap.content.SubjectGenerate" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="org.scs.ap.database.Database" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.scs.ap.view.Config" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="org.scs.ap.view.Message" %>
<%@ page import="org.scs.ap.servlet.Session" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 14.01.2018
  Time: 21:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Message message = new Message();

    String backColorHead="background: #B4FFB1";
    int cycle = 3;
    int parts[] = {5, 6};

    Statement statement;
    Config cfg = new Config();
    ArrayList<String> content = new ArrayList<>();
    SubjectGenerate prof = null;
    try {
        Database database = new Database();
        Connection connection = database.getConnection();
        statement = connection.createStatement();
        statement.execute("SELECT create_sub(" + cycle + ");");
        prof = new SubjectGenerate(connection, backColorHead);
        content = cfg.getArrayXml("table-hmp");
        statement.close();
    }catch(SQLException e){
        System.out.println(e.toString());
    }
%>
<html>
<head>
    <title>ПРОФ</title>
    <LINK REL="StyleSheet" HREF="<%=request.getContextPath()%>/styles/style.css" TYPE="text/css">
</head>
<%@ include file="top-container.jsp" %>
<%@ include file="popup.jsp" %>
<form action="${pageContext.request.contextPath}/prof" method="POST">
    <div id="cont-main-block" style="height: 1100px">
        <table id="cont-table" style="width: 100%; text-align: left; font-size: 11pt;" class = "hmp-table">
            <tr>
                <td rowspan="4" style="width: 90px"><%=content.get(0)%></td>
                <td rowspan="4" style="width: 300px"><%=content.get(1)%></td>
                <td rowspan="4" style="width: 100px"><%=content.get(2)%></td>
                <td rowspan="4" style="width: 32px" class="rotatable"><%=content.get(3)%></td>
                <td rowspan="4" class="rotatable"><%=content.get(4)%></td>
                <td rowspan="4" class="rotatable"><%=content.get(5)%></td>
                <td rowspan="4" style="width: 30px" class="rotatable"><%=content.get(6)%></td>
                <td colspan="7" style="width: 290px"><%=content.get(7)%></td>
                <td colspan="32">
                    <%=content.get(8)%>
                </td>
            </tr>
            <%=prof.headTable()%>
            <tr>
                <td colspan="46" style="<%=backColorHead%>"><%=prof.getCycle(cycle)%></td>
            </tr>
            <tr>
                <td colspan="46" style="font-weight:bolder; <%=backColorHead%>"><%=prof.getParts(parts[0])%></td>
            </tr>
            <%=prof.getSubjects(parts[0])%>
            <tr>
                <td colspan="46" style="font-weight:bolder; <%=backColorHead%>"><%=prof.getParts(parts[1])%></td>
            </tr>
            <%=prof.getSubjects(parts[1])%>
            <%=prof.summPage()%>
        </table>
    </div>
    <div style="width: 2000px; height: 100px; bottom:0;">
        <%
            Session sess = new Session();
            if(sess.getAcces()<3){
        %>
        <input type="submit" name="submit" class="save-button" value="Сохранить"/>
        <%
            }
            if(sess.getAcces()<2){
        %>
        <input type="button" name="add" class="save-button addb" value="Добавить" onclick="addCol();"/>
        <input type="button" name="remove" class="save-button remove" value="Удалить" onclick="delCol();"/>
        <%
            }
        %>
    </div>
</form>
<%@ include file="bottom-container.jsp" %>
<% if(message.isShow()){ %>
<script>
    alert('<%=message.getMessage()%>')
</script>
<% } %>