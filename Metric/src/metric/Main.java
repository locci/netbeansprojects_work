/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metric;

import java.io.File;
import javax.swing.JFileChooser;

/**
 *
 * @author alexandre
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int ret = chooser.showOpenDialog(null);
        String str = chooser.getSelectedFile().getAbsolutePath(); 
        FileManeger fManeger = new FileManeger();
        fManeger.incertIntoTheList(str);
        //Metric.NumberFile();
        for (File listOfFilie : FileManeger.listOfFilie) {
            fManeger.openFile(listOfFilie);
        }
        System.out.println("numero de branchs " + Metric.numberOfBranch);
        System.out.println("Total Files "   + Metric.todosOsArquivos);
        Metric.NumberFile();
        for (int i = 1; i < Metric.num.length; i++) {
            
            if(Metric.num[i]!= 0){
                System.out.println("Expressões com " + i + " condições = " + Metric.num[i]);
            }
            
        }
        
        
    }
    
}
