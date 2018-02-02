package org.scs.ap.view;

import java.util.ArrayList;

public class Table {
    private String htmlClass = "title-table";
    private ArrayList<String> head = new ArrayList<>();
    private ArrayList<ArrayList<String>> strings = new ArrayList<>();
    private StringBuilder formattedString = new StringBuilder();

    public Table(ArrayList<String>head){
        setHead(head);
    }

    public void setHead(ArrayList<String> head){
        this.head = head;
    }

    /**
     * Открываем таблицу сразу при создании объекта
     */
    public Table(int htmlWidth){
        formattedString.append("<table class=\"" + htmlClass + "\" border=1 ");
        formattedString.append("width=\"" + htmlWidth + "%\" >");}

    public Table(int htmlWidth, String htmlClass){
        formattedString.append("<table class=\"" + this.htmlClass+" " + htmlClass + "\" border=1 ");
        formattedString.append("width=\"" + htmlWidth + "%\" >");}

    public Table(){
        formattedString.append("<table class=\"" + htmlClass + "\" border=1 >");
        }

    /**
     * Открыть строку
     */
    public void openRow(){formattedString.append("<tr>");}

    /**
     * Добавить ячейку
     * @param str - содержимое ячейки
     * @param rowspan - атрибут html
     * @param colspan - атрибут html
     */
    public void add(String str, int rowspan, int colspan){
        formattedString.append("<td rowspan = \"" + rowspan + "\" colspan = \"" + colspan + "\">");
        formattedString.append(str);
        formattedString.append("</td>");
    }

    public void add(String str, String style){
        formattedString.append("<td style=\"" + style + "\">");
        formattedString.append(str);
        formattedString.append("</td>");
    }

    public void add(String str){
        formattedString.append("<td>");
        formattedString.append(str);
        formattedString.append("</td>");
    }

    /**
     * Добавить text-field
     * @param str - содержимое
     */
    public void addField(String str){
        formattedString.append("<td>");
        formattedString.append("<input style=\"width: 20px\" type=\"text\" class=\"text-field\" value=\"");
        formattedString.append(str);
        formattedString.append("\"></td>");
    }

    public void addField(String str, int width){
        formattedString.append("<td>");
        formattedString.append("<input style=\"width: "+ width +"px\" type=\"text\" class=\"text-field\" value=\"");
        formattedString.append(str);
        formattedString.append("\"></td>");
    }

    public void addField(String str, String name){
        formattedString.append("<td>");
        formattedString.append("<input type=\"text\" name = \"" + name + "\" style=\"width: 20px\" class=\"text-field\" value=\"");
        formattedString.append(str);
        formattedString.append("\"></td>");
    }


    /**
     * Закрыть строку
     */
    public void closeRow(){formattedString.append("</tr>");}

    @Override
    public String toString() {
        formattedString.append("</table>");
        return formattedString.toString();
    }
}
