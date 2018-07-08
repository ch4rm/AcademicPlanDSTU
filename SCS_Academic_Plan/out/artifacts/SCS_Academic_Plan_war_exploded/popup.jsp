<%@ page import="java.sql.ResultSet" %>
<%@ page import="static org.scs.ap.servlet.Login.db" %><%--
  Created by IntelliJ IDEA.
  User: User
  Date: 11.03.2018
  Time: 19:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ArrayList<String> part_pk = new ArrayList<String>();
    ArrayList<String> part = new ArrayList<String>();
    ArrayList<String> dp = new ArrayList<String>();
    Statement st;
    ResultSet rs;
    try {
        st = db.getConnection().createStatement();
        rs = st.executeQuery("SELECT key_department FROM departments");
        while (rs.next())
            dp.add(rs.getString(1));
        rs = st.executeQuery("SELECT key_parts_pk, key_parts_let FROM parts WHERE key_cycle_fk=" + cycle);
        while (rs.next()) {
            part_pk.add(rs.getString(1));
            part.add(rs.getString(2));
        }
        st.close();
        rs.close();
    }catch(SQLException e){
        System.out.println(e.toString());
    }
    String postAdd[] = {"/addhse", "/addmns", "/addprof"};
    String postDelete[] = {"/deletehse", "/deletemns", "/deleteprof"};

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
        <form action="<%=postAdd[cycle-1]%>" method="POST" id="form-popup" class="popup-form">
            <table class="popup-table">
                <tr><td style="text-align:center;" colspan="2">Добавить строку в цикл <input type="text" id="cycle-in"
                        name="cycle-in" class="text-field" style="font-size: 16pt; width: 30px;" >:</td></tr>
                <tr><td>Имя части: </td><td>
                    <select name="part-name" class="text-field-n">
                        <option value="<%=part_pk.get(0)%>"><%=part.get(0)%></option>
                        <option value="<%=part_pk.get(1)%>"><%=part.get(1)%></option>
                    </select>
                </td></tr>
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
                <tr><td>Номер семестра: </td><td>
                    <select name="sem-num" class="text-field-n">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                        <option value="6">6</option>
                        <option value="7">7</option>
                        <option value="8">8</option>
                    </select>
                </td></tr>
                <tr><td>Экзамен</td><td><input type="radio" checked name="type" class = "checkmark" value="0"></td></tr>
                <tr><td>Зачёт</td><td><input type="radio" name="type" class = "checkmark" value="1"></td></tr>
                <tr><td>Пустой</td><td><input type="radio" name="type" class = "checkmark" value="2"></td></tr>
                <tr><td></td><td><input type="submit" class="save-button addb" value="Добавить"/></td></tr>
            </table>
        </form>
        <a class="close" title="Закрыть" onclick="document.getElementById('parent_popup').style.display='none';">X</a></div>
</div>


<div id="parent_popup1" class="parent_popup">
    <div id="popup1" class="popup">
        <form action="<%=postDelete[cycle-1]%>" method="POST" id="form-popup1" class="popup-form" style="height:200px;">
            <table class="popup-table">
                <tr><td style="text-align:center;" colspan="2">Удалить строку в цикле <input type="text" id="cycle-in1"
                        name="cycle-in" class="text-field" style="font-size: 16pt; width: 30px;" >:</td></tr>
                <tr><td>Имя части: </td><td><select name="part-name-cl" class="text-field-n">
                    <option value="<%=part_pk.get(0)%>"><%=part.get(0)%></option>
                    <option value="<%=part_pk.get(1)%>"><%=part.get(1)%></option></select></td></tr>
                <tr><td>Номер предмета (1/1.1): </td><td><input type="text" name="part-num-cl" class="text-field-n"></td></tr>
                <tr><td></td><td><input type="submit" class="save-button remove" value="Удалить"/></td></tr>
            </table>
        </form>
        <a class="close" title="Закрыть" onclick="document.getElementById('parent_popup1').style.display='none';">X</a></div>
</div>