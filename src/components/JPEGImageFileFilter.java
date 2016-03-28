/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package components;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author chieuvh
 */
public class JPEGImageFileFilter extends FileFilter implements java.io.FileFilter {

    public boolean accept(File f) {
        if (f.getName().toLowerCase().endsWith(".jpeg")) {
            return true;
        }
        if (f.getName().toLowerCase().endsWith(".jpg")) {
            return true;
        }
        if (f.getName().toLowerCase().endsWith(".png")) {
            return true;
        }
        if (f.isDirectory()) {
            return true;
        }
        return false;
    }

    public String getDescription() {
        return "JPEG files";
    }

}
