<%@ page import="org.scs.ap.database.Database" %>
<%@ page import="org.scs.ap.content.Titles" %>
<%@ page import="org.scs.ap.view.Config" %>
<%@ page import="java.sql.Connection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%  Database database = new Database();
    Connection connection = database.getConnection();
    Config cfg = new Config();
    Titles titles = new Titles(connection, cfg);
%>
<html>
<head>
    <title>Титул</title>
    <LINK REL="StyleSheet" HREF="<%=request.getContextPath()%>/styles/style.css" TYPE="text/css">
</head>
<%@ include file="top-container.jsp" %>
<form action="/title" method="POST">
    <div style="height: 1100px">
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
                    "__"____ <input type="text" name="yearCreation" class="text-field" value="<%=titles.getYearCreations()%>">г.<br>
                    Одобрен Ученым советом ДонГТУ, протокол от __.__.<input type="text" name="yearCreation" class="text-field" value="<%=titles.getYearCreations()%>">г. №__
                </td>
                <td style="text-align: right; padding-right: 100px">
                    Квалификация: <input type="text" name="qualification" style="width: 80px" class="text-field" value="<%=titles.getQualification()%>"><br>
                    Срок обучения: <input type="text" name="termsEducation" class="text-field" value="<%=titles.getTermsEducation()%>">г.<br>
                    на базе: среднего общего образования<br>
                </td>
            </tr>
            <tr>
                <td colspan=2 style="text-align:center;">
                    <h3 style="margin-top:10px; display:inline-block;">УЧЕБНЫЙ ПЛАН</h3>
                    <h5 style="font-style:italic; display:inline-block;"> - год приёма <input type="text" name="yearReception" class="text-field" value="<%=titles.getYearReception()%>"></h5>
                </td>
            </tr>
            <tr>
                <td colspan=2>
                    <table style="width:80%; margin-left:10%; text-align:justify; font-size:12px; font-weight:bold;">
                        <tr>
                            <td>уровень высшего образования (УВО)</td>
                            <td style="text-decoration: underline;"><input type="text" name="lvlEducation" style="width: 80px"  class="text-field" value="<%=titles.getLvlEducation()%>"></td>
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
                            <td style="text-decoration: underline;"><input type="text" style="width: 500px" name="name_prof" class="text-field" value="<%=titles.getProfiles()%>"></td>
                        </tr>
                        <tr>
                            <td>форма обучения</td>
                            <td style="text-decoration: underline;"><input type="text" style="width: 80px" name="formEducation" class="text-field" value="<%=titles.getFormEducation()%>"></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td style="font-size: 8px;">(дневная, вечерняя, заочная (дистанционная), экстернат</td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <div style = "width: 1700px; margin: 0 auto;">
            <h3 style="text-align: center;">І. ГРАФИК УЧЕБНОГО ПРОЦЕССА</h3>
            <%=titles.getScheduleProcess()%>
            <h3>ОБОЗНАЧЕНИЯ: </h3>
            <a style="text-align: center;"> _ - теоретическое обучение; СК - сдача кредитов; С - сессия: К - каникулы: П - практика; Н - НИР; Г - государственный экзамен: Д - подотовка ВКР. </a>
        </div>

        <div style="margin:0 auto; width: 1700px; height: 400px">
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
    </div>
    <div style="width: 1800px; height: 100px; bottom:0;">
        <input type="submit" name="submit" class="save-button" value="Сохранить"/>
    </div>
    <%connection.close();%>
</form>
<%@ include file="bottom-container.jsp" %>
