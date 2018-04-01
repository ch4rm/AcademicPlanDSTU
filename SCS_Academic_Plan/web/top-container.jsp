<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 10.01.2018
  Time: 18:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    function setVisible() {
        var style = document.getElementById("right-block").style;
        var style2 = document.getElementById("left-block").style;
        var button = document.getElementById("setting-but");
        if(style.width == "400px") {
            style.width = "25px";
            style2.width="calc(100% - (25px))";
            button.innerHTML="<";
        }else{

            style.width = "400px";
            style2.width = "calc(100% - (400px))";
            button.innerHTML=">";
        }
    }

    function init() {
        var table = document.getElementById("cont-table");
        if(table.offsetHeight>1100) {
            document.getElementById("cont-block").style.height = table.offsetHeight + 220 + "px";
            document.getElementById("cont-main-block").style.height = table.offsetHeight+70+"px";
        }
    }
</script>
<body onload="init();">
<div class="wrapper">
    <div class = "menu">
        <ul>
            <li><a href="title.jsp">ТИТУЛ</a></li>
            <li><a href="hse.jsp">ГСЭ</a></li>
            <li><a href="mns.jsp">МЕН</a></li>
            <li><a href="prof.jsp">ПРОФ</a></li>
            <li><a href="summary.jsp">ИТОГ</a></li>
            <li><a href="calculation.jsp">РАСЧЕТ РУП</a></li>
        </ul>
    </div>
    <div id="left-block" class="left-block custom-scrollbar">
        <div id="cont-block" class="content-block">
            <div class="content">