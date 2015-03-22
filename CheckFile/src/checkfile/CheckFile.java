/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package checkfile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;

/**
 *
 * @author alexandre
 */
public class CheckFile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int ret = chooser.showOpenDialog(null);
        String str = chooser.getSelectedFile().getAbsolutePath();
        File fiRead = new File(str);
        File fiW = new File("log_Out");
        
        BufferedWriter bw = new BufferedWriter(new FileWriter(fiW));
        BufferedReader br = new BufferedReader(new FileReader(fiRead));
        
        while(br.ready()){
            
            str = br.readLine();
            if(!str.contains("source code")){
                bw.write(str + "\n");
            }
            
            
        }
        
        br.close();
        bw.close();
    }
    
}
