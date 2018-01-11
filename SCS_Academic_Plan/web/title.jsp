<%@ page import="org.scs.ap.database.Database" %>
<%@ page import="org.scs.ap.content.Titles" %>
<%@ page import="org.scs.ap.view.SQLExec" %>
<%@ page import="org.scs.ap.servlet.Title" %><%--
  Created by IntelliJ IDEA.
  User: futurediary
  Date: 26.11.2017
  Time: 21:44
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%  Database database = new Database();
    SQLExec sqlExec = new SQLExec(database);
    Titles titles = new Titles(database);
%>
<html>
<head>
    <title>Титул</title>
    <LINK REL="StyleSheet" HREF="<%=request.getContextPath()%>/styles/style.css" TYPE="text/css">
</head>
<%@ include file="top-container.jsp" %>

<table class="table-content">
    <tr>
        <td style="font-weight: bold;" colspan=2>
            Министерство образования и науки Луганской Народной Республики<br>
            ГОУ ВПО ЛНР "Донбасский государственный технический университет" *(ДонГТУ)
        </td>
    </tr>
    <tr>
        <td style="text-align: left; padding-left: 100px">
            <h4>Утверждаю</h4>
            И.о. ректора __ Зинченко А.М.<br>
            "__"____ <input type="text" class="text-field" value="<%=titles.getYearCreations()%>">г.<br>
            Одобрен Ученым советом ДонГТУ, протокол от __.__.<input type="text" class="text-field" value="<%=titles.getYearCreations()%>">г. №__
        </td>
        <td style="text-align: right; padding-right: 100px">
            Квалификация: <input style="width: 80px" type="text" class="text-field" value="<%=titles.getQualification()%>"><br>
            Срок обучения: <input type="text" class="text-field" value="<%=titles.getTermsEducation()%>">г.<br>
            на базе: среднего общего образования<br>
        </td>
    </tr>
    <tr>
        <td colspan=2 style="text-align:center;">
            <h3 style="margin-top:10px; display:inline-block;">УЧЕБНЫЙ ПЛАН</h3>
            <h5 style="font-style:italic; display:inline-block;"> - год приёма <input type="text" class="text-field" value="<%=titles.getYearReception()%>"></h5>
        </td>
    </tr>
    <tr>
        <td colspan=2>
            <table style="width:80%; margin-left:10%; text-align:justify; font-size:12px; font-weight:bold;">
                <tr>
                    <td>уровень высшего образования (УВО)</td>
                    <td style="text-decoration: underline;"><input style="width: 80px" type="text" class="text-field" value="<%=titles.getLvlEducation()%>"></td>
                </tr>
                <tr>
                    <td>код и наименование укрупненной группы направления подготовки</td>
                    <td style="text-decoration: underline;">09.00.00  Информатика и вычислительная техника</td>
                </tr>
                <tr>
                    <td>код и наименование направления подготовки</td>
                    <td style="text-decoration: underline;">09.03.01  Информатика и вычислительная техника</td>
                </tr>
                <tr>
                    <td>профиль (направленность)</td>
                    <td style="text-decoration: underline;">Автоматизированные системы обработки информации и управления</td>
                </tr>
                <tr>
                    <td>форма обучения</td>
                    <td style="text-decoration: underline;"><input style="width: 80px" type="text" class="text-field" value="<%=titles.getFormEducation()%>"></td>
                </tr>
                <tr>
                    <td></td>
                    <td style="font-size: 8px;">(дневная, вечерняя, заочная (дистанционная), экстернат</td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<div style = "width: 1600px; margin: 0 auto;">
    <h3 style="text-align: center;">І. ГРАФИК УЧЕБНОГО ПРОЦЕССА</h3>
    <%=titles.getScheduleProcess()%>
    <h3>ОБОЗНАЧЕНИЯ: </h3>
    <a style="text-align: center;"> _ - теоретическое обучение; СК - сдача кредитов; С - сессия: К - каникулы: П - практика; Н - НИР; Г - государственный экзамен: Д - подотовка ВКР. </a>
</div>

<div style="margin:0 auto;">
    <div class = "down-table1">
        <h3 style="text-align: center">II. СВОДНЫЕ ДАННЫЕ О БЮДЖЕТЕ ВРЕМЕНИ, недели  </h3>
        <%=titles.getTimeBudgets()%>
    </div>
    <div class = "down-table2">
        <h3 style="text-align: center">III. ПРАКТИКА</h3>
        <%=titles.getPractics()%>
    </div>
    <div class="down-table3">
        <h3 style="text-align: center">IV. ГОСУДАРСТВЕННАЯ ИТОГОВАЯ АТТЕСТАЦИЯ</h3>
        <%=titles.genStateAtestation()%>
    </div>

</div>

<%@ include file="bottom-container.jsp" %>
