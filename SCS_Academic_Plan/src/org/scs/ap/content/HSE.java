package org.scs.ap.content;

import org.scs.ap.database.Database;

/**
 * Created by User on 03.02.2018.
 */
public class HSE {
    private Database database;

    public HSE(Database database){
        this.database = database;
    }

    public String headTable(){
        String s="";
        s+="<tr>";
        s+="<td colspan=\"4\">Аудиторные</td><td colspan=\"3\">Самост.</td>";
        for(int i=1; i<5;i++)
            s+="<td colspan=\"8\">"+ i + "курс</td>";
        s+="</tr><tr>";
        s+="<td rowspan=\"2\" class=\"rotatable\">Всего</td><td rowspan=\"2\" class=\"rotatable\">Лек</td>";
        s+="<td rowspan=\"2\" class=\"rotatable\">Лаб</td>";
        s+="<td rowspan=\"2\" class=\"rotatable\">Пр.</td><td rowspan=\"2\" class=\"rotatable\">Всего</td>";
        s+="<td rowspan=\"2\" class=\"rotatable\">КСР</td>";
        s+="<td rowspan=\"2\" class=\"rotatable\">БСР</td>";
        for(int i=1; i<9;i++)
            s+="<td colspan=\"4\">" + i + " (18)</td>";
        s+="</tr><tr style=\"height:60px\">";
        for(int i=1; i<9;i++) {
            s += "<td style=\"width:10px; height: 20px;\" class=\"rotatable\">Лек.</td>";
            s += "<td style=\"width:10px; height: 20px;\" class=\"rotatable\">Лаб.</td>";
            s += "<td style=\"width:10px; height: 20px;\" class=\"rotatable\">Пр.</td>";
            s += "<td style=\"width:10px; height: 20px;\" class=\"rotatable\">Сам.</td>";
        }
        s+="</tr>";
        return s;
    }
}
