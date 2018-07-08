package org.scs.ap.view;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

public class Config{
    private Document document;
    private String XMLname = "servlet-text.xml";

    public Config(String XMLname){
        this.XMLname = XMLname;
        openXML();
    }

    public Config(){
        openXML();
    }

    private void openXML(){
        URL file = getClass().getResource(XMLname);
        File xmlFile = new File(file.getPath());
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try{
            builder = factory.newDocumentBuilder();
            document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
        }catch (Exception e){}
    }

    public ArrayList<String> getArrayXml(String name){
        ArrayList<String> names = new ArrayList<String>();
        try{
            NodeList nodeList = document.getElementsByTagName(name);
            for(int i=0;i<nodeList.getLength();i++)
                names.add(nodeList.item(i).getTextContent());
        }catch (Exception e){
            System.out.println("item not exist");
        }
        return names;
    }
}
