<%@ page import="org.scs.ap.content.TitleGenerate" %>
<%@ page import="org.scs.ap.view.Config" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="static org.scs.ap.servlet.Login.db" %>
<%@ page import="org.scs.ap.view.Message" %>
<%@ page import="org.scs.ap.servlet.Session" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Message message = new Message();
    String backColorHead="background: white";
    Config cfg = new Config();
    TitleGenerate titles = new TitleGenerate(db.getConnection(), cfg, backColorHead);
    ArrayList<String> content = cfg.getArrayXml("content");
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
                    <%=content.get(0)%><br>
                    <%=content.get(1)%>
                </td>
            </tr>
            <tr>
                <td style="text-align: left; padding-left: 100px">
                    <h4><%=content.get(2)%></h4>
                    И.о. ректора __ <%=content.get(3)%><br>
                    "__"____ <input type="text" name="yearCreation" class="text-field" value="<%=titles.getYearCreations()%>">г.<br>
                    <%=content.get(4)%> __.__.<input type="text" name="yearCreation1" class="text-field" value="<%=titles.getYearCreations()%>">г. №__
                </td>
                <td style="text-align: right; padding-right: 100px">
                    <%=content.get(5)%> <input type="text" name="qualification" style="width: 80px" class="text-field" value="<%=titles.getQualification()%>"><br>
                    <%=content.get(6)%> <input type="text" name="termsEducation" class="text-field" value="<%=titles.getTermsEducation()%>">г.<br>
                    <%=content.get(7)%> <br>
                </td>
            </tr>
            <tr>
                <td colspan=2 style="text-align:center;">
                    <h3 style="margin-top:10px; display:inline-block;"><%=content.get(8)%></h3>
                    <h5 style="font-style:italic; display:inline-block;"><%=content.get(9)%><input type="text" name="yearReception" class="text-field" value="<%=titles.getYearReception()%>"></h5>
                </td>
            </tr>
            <tr>
                <td colspan=2>
                    <table style="width:80%; margin-left:10%; text-align:justify; font-size:12px; font-weight:bold;">
                        <tr>
                            <td><%=content.get(10)%></td>
                            <td style="text-decoration: underline;"><input type="text" name="lvlEducation" style="width: 80px"  class="text-field" value="<%=titles.getLvlEducation()%>"></td>
                        </tr>
                        <tr>
                            <td><%=content.get(11)%></td>
                            <td style="text-decoration: underline;"><%=content.get(15)%></td>
                        </tr>
                        <tr>
                            <td><%=content.get(12)%></td>
                            <td style="text-decoration: underline;"><%=content.get(16)%></td>
                        </tr>
                        <tr>
                            <td><%=content.get(13)%></td>
                            <td style="text-decoration: underline;"><input type="text" style="width: 500px" name="name_prof" class="text-field" value="<%=titles.getProfiles()%>"></td>
                        </tr>
                        <tr>
                            <td><%=content.get(14)%></td>
                            <td style="text-decoration: underline;"><input type="text" style="width: 80px" name="formEducation" class="text-field" value="<%=titles.getFormEducation()%>"></td>
                        </tr>
                        <tr>
                            <td></td>
                            <td style="font-size: 8px;"><%=content.get(17)%></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
        <div style = "width: 1700px; margin: 0 auto;">
            <h3 style="text-align: center;"><%=content.get(20)%></h3>
            <%=titles.getScheduleProcess()%>
            <h3><%=content.get(18)%></h3>
            <a style="text-align: center;"><%=content.get(19)%></a>
        </div>

        <div style="margin:0 auto; width: 1700px; height: 400px">
            <div class = "down-table1">
                <h3 style="text-align: center"><%=content.get(21)%></h3>
                <%=titles.getTimeBudgets()%>
            </div>
            <div class = "down-table2">
                <h3 style="text-align: center"><%=content.get(22)%></h3>
                <%=titles.getPractics()%>
            </div>
            <div class="down-table3">
                <h3 style="text-align: center"><%=content.get(23)%></h3>
                <%=titles.genStateAtestation()%>
            </div>
        </div>
    </div>
    <div style="width: 2000px; height: 100px; bottom:0;">
        <%
            Session sess = new Session();
            if(sess.getAcces()<3){
        %>
        <input type="submit" name="submit" class="save-button" value="Сохранить"/>
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