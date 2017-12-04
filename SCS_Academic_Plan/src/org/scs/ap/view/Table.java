package org.scs.ap.view;

import java.util.ArrayList;

public class Table {
    private String htmlClass = "title-table";
    private String htmlWidth = "100%";
    private ArrayList<String> head = new ArrayList<>();
    private ArrayList<ArrayList<String>> strings = new ArrayList<>();

    public Table(){}

    public Table(ArrayList<String>head){
        setHead(head);
    }

    public void setTableClass(String htmlClass){
        this.htmlClass = htmlClass;
    }

    public void setHead(ArrayList<String> head){
        this.head = head;
    }

    public void addColumn(ArrayList<String> string){
        strings.add(string);
    }

    @Override
    public String toString() {
        StringBuilder formattedString = new StringBuilder();
        formattedString.append("<table class=\"" + htmlClass + "\" border=1 ");
        formattedString.append("width=\"" + htmlWidth + "\" >");
        for(ArrayList<String> str : strings){
            formattedString.append("<tr>");
            for(String string : str){
                formattedString.append("<td>");
                formattedString.append(string);
                formattedString.append("</td>");
            }
            formattedString.append("</tr>");
        }
        formattedString.append("</table>");
        return formattedString.toString();
    }
}
