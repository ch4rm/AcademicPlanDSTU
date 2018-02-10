package org.scs.ap.view;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Shishko.Arthur on 04.02.2018.
 */
public class Config{
    private Document document;

    public Config(){
        URL file = getClass().getResource("servlet-text.xml");
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
