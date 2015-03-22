/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metric_v4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author alexandre
 */
public class ContLines {
    
    public static void main(String[] args) throws IOException{
        
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int ret = chooser.showOpenDialog(null);
        String str = chooser.getSelectedFile().getAbsolutePath(); 
        BufferedWriter bw = new BufferedWriter(new FileWriter("lineNumber",true));
        String s;
        Process p;
        
        try {
            
            p = Runtime.getRuntime().exec("/home/alexandre/Dropbox/tools/javancss-32.53/bin/./javancss -ncss -recursive " + str);
            BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null){
                
                System.out.println("line: " + s);
                bw.write(str + "\n");
                bw.write(s + "\n");
                
            }                
            p.waitFor();
            bw.close();
            System.out.println ("exit: " + p.exitValue());
            p.destroy();
            
        } catch (Exception e) {
        
        
        }
        
        
    }
    
}
