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
<body>
<div class="header">
    <div class="menu">
        <ul>
            <li><a href="title" style="background-color:#D0D0D0; color:white;">ТИТУЛ</a></li>
            <li><a href="">ГСЕ</a></li>
            <li><a href="">МЕН</a></li>
            <li><a href="">ПРОФ</a></li>
            <li><a href="">ИТОГ</a></li>
            <li><a href="">РАСЧЕТ РУП</a></li>
        </ul>
    </div>
</div>
<div class="wrapper">
    <div class="left-block custom-scrollbar">




        <table class="table-content" width="90%">
            <tr>
                <td style="text-align:center;" colspan=2>
                    Министерство образования и науки Луганской Народной Республики<br>
                    ГОУ ВПО ЛНР "Донбасский государственный технический университет" *(ДонГТУ)
                </td>
            </tr>
            <tr>
                <td style="width:70%">
                    <h4 style="margin-left:15%">Утверждаю</h4>
                    И.о. ректора __ Зинченко А.М.<br>
                    "__"__ <%=titles.getYearCreations()%>г.<br>
                    Одобрен Ученым советом ДонГТУ, протокол от __.__.<%=titles.getYearCreations()%>г. №__
                </td>
                <td style="width:30%">
                    Квалификация: <%=titles.getQualification()%><br>
                    Срок обучения: <%=titles.getTermsEducation()%>г.<br>
                    на базе: среднего общего образования<br>
                </td>
            </tr>
            <tr>
                <td colspan=2 style="text-align:center;">
                    <h3 style="margin-top:10px; display:inline-block;">УЧЕБНЫЙ ПЛАН</h3>
                    <h5 style="font-style:italic; display:inline-block;"> - год приёма <%=titles.getYearReception()%></h5>
                </td>
            </tr>
            <tr>
                <td colspan=2>
                    <table style="width:80%; margin-left:10%; text-align:justify; font-size:12px; font-weight:bold;">
                        <tr>
                            <td>уровень высшего образования (УВО)</td>
                            <td style="text-decoration: underline;"><%=titles.getLvlEducation()%></td>
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
                            <td style="text-decoration: underline;"><%=titles.getFormEducation()%></td>
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

        <div style="width: 900px; margin:0 auto;">

            <div class = "down-table1">
                <h3 style="text-align: center">II. СВОДНЫЕ ДАННЫЕ О БЮДЖЕТЕ ВРЕМЕНИ, недели  </h3>
                <%=titles.getTimeBudgets()%>
            </div>
            <div class = "down-table2">
                <h3 style="text-align: center">III. ПРАКТИКА</h3>
                <%=titles.getPractics()%>
            </div>
            <div class = "down-table3">
                <h3 style="text-align: center">IV. ГОСУДАРСТВЕННАЯ ИТОГОВАЯ АТТЕСТАЦИЯ</h3>
                <%=titles.genStateAtestation()%>
            </div>
        </div>
    </div>





    <div class="right-block custom-scrollbar">
    </div>
</div>
<div class="footer">
    &copy ДОНБАССКИЙ ГОСУДАРСТВЕННЫЙ ТЕХНИЧЕСКИЙ УНИВЕРСИТЕТ<br>
    КАФЕДРА СПЕЦИАЛИЗИРОВАННЫХ КОМПЮТЕРНЫХ СИСТЕМ | 2017
</div>
</body>
</html>