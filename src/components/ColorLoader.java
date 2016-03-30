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
        File file = new File(classLoader.getResource("color.txt").getFile());
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;
        while ((line = br.readLine()) != null) {
            String[] colors = line.replace("(", "").replace(")", "").replace(" ", "").split(",");
            Color color = new Color(Integer.parseInt(colors[0]), Integer.parseInt(colors[1]), Integer.parseInt(colors[2]));
            MyColor myColor = new MyColor();
            myColor.color = color;
            colorList.add(myColor);
        }

    }

}
