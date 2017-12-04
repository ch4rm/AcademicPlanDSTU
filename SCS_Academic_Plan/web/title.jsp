<%@ page import="org.scs.ap.database.Database" %>
<%@ page import="org.scs.ap.view.HTML" %><%--
  Created by IntelliJ IDEA.
  User: futurediary
  Date: 26.11.2017
  Time: 21:44
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Титул</title>
        <link rel=STYLESHEET type="text/css" href="styles/style.css">
    </head>
    <body>
        <%  Database database = new Database();
            HTML html = new HTML(database);
        %>
        <%=html.createTableSubjects()%>
    </body>
</html>
