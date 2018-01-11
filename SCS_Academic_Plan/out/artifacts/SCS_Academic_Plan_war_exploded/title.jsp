<%@ page import="org.scs.ap.database.Database" %>
<%@ page import="org.scs.ap.content.Titles" %><%--
  Created by IntelliJ IDEA.
  User: futurediary
  Date: 26.11.2017
  Time: 21:44
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%  Database database = new Database();
    Titles titles = new Titles(database);
%>
<html>
<head>
    <title>Титул</title>
    <LINK REL="StyleSheet" HREF="<%=request.getContextPath()%>/styles/style.css" TYPE="text/css">
</head>
<%@ include file="top-container.jsp" %>

<%@ include file="bottom-container.jsp" %>
