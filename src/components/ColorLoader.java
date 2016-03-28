/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.awt.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 *
 * @author chieuvh
 */
public class ColorLoader {

    public static List<MyColor> colorList = new ArrayList();

    public static void main(String[] args) throws Exception {
        load();
    }

    public static void load() throws Exception {
        ClassLoader classLoader = ColorLoader.class.getClassLoader();
        File file = new File(classLoader.getResource("color.html").getFile());

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(file);

        //optional, but recommended
        //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("tr");
        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                NodeList tdList = eElement.getElementsByTagName("td");
                if (tdList.getLength() == 0) {
                    continue;
                }
                String[] colors = tdList.item(3).getTextContent().replace("(", "").replace(")", "").split(",");
                Color color = new Color(Integer.parseInt(colors[0]), Integer.parseInt(colors[1]), Integer.parseInt(colors[2]));
//                System.out.println(tdList.item(3).getTextContent());
                MyColor myColor = new MyColor();
                myColor.color = color;
                myColor.name = tdList.item(1).getTextContent();
                colorList.add(myColor);
            }
        }
    }

}
