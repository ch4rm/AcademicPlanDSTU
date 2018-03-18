<%@ page import="java.sql.ResultSet" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 11.03.2018
  Time: 19:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ArrayList<String> dp = new ArrayList<>();
    Statement st = connection.createStatement();
    ResultSet rs = st.executeQuery("SELECT * FROM departments");
    while(rs.next())
        dp.add(rs.getString(2));
    st.close();
    rs.close();
%>


<script>
    function addCol(){
        document.getElementById('parent_popup').style.display='block';
        var el=document.getElementById('cycle-in');
        el.value = document.getElementById('cycle-head').value;
   }

    function delCol(){
        document.getElementById('parent_popup1').style.display='block';
        var el=document.getElementById('cycle-in1');
        el.value = document.getElementById('cycle-head').value;
    }
</script>
<div id="parent_popup" class="parent_popup">
    <div id="popup" class="popup">
        <form action="/addhse" method="POST" id="form-popup" class="popup-form">
            <table class="popup-table">
                <tr><td style="text-align:center;" colspan="2">Добавить строку в цикл <input type="text" id="cycle-in"
                        name="cycle-in" class="text-field" style="font-size: 16pt; width: 30px;" >:</td></tr>
                <tr><td>Имя части (Б/В): </td><td><input type="text" name="part-name" class="text-field-n"></td></tr>
                <tr><td>Название Дисциплины: </td><td><input type="text" name="dist-name" class="text-field-n"></td></tr>
                <tr><td>Шифр кафедры: </td><td>
                    <select name="dep-name" class="text-field-n">
                        <option value="1"><%=dp.get(0)%></option>
                        <option value="2"><%=dp.get(1)%></option>
                        <option value="3"><%=dp.get(2)%></option>
                        <option value="4"><%=dp.get(3)%></option>
                        <option value="5"><%=dp.get(4)%></option>
                        <option value="6"><%=dp.get(5)%></option>
                        <option value="7"><%=dp.get(6)%></option>
                        <option value="8"><%=dp.get(7)%></option>
                        <option value="9"><%=dp.get(8)%></option>
                    </select>
                </td></tr>
                <tr><td>Номер семестра: </td><td><input type="text" name="sem-num" class="text-field-n"></td></tr>
                <tr><td></td><td><input type="submit" class="save-button addb" value="Добавить"/></td></tr>
            </table>
        </form>
        <a class="close"title="Закрыть" onclick="document.getElementById('parent_popup').style.display='none';">X</a></div>
</div>


<div id="parent_popup1" class="parent_popup">
    <div id="popup1" class="popup">
        <form method="POST" id="form-popup1" class="popup-form" style="height:200px;">
            <table class="popup-table">
                <tr><td style="text-align:center;" colspan="2">Удалить строку в цикле <input type="text" id="cycle-in1"
                        name="cycle-in" class="text-field" style="font-size: 16pt; width: 30px;" >:</td></tr>
                <tr><td>Имя части (Б/В): </td><td><input type="text" name="part-name-cl" class="text-field-n"></td></tr>
                <tr><td>Номер предмета (1/1.1): </td><td><input type="text" name="part-num-cl" class="text-field-n"></td></tr>
                <tr><td></td><td><input type="submit" class="save-button remove" value="Удалить"/></td></tr>
            </table>
        </form>
        <a class="close"title="Закрыть" onclick="document.getElementById('parent_popup1').style.display='none';">X</a></div>
</div>