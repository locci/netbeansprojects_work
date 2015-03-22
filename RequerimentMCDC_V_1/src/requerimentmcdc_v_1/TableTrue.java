/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package requerimentmcdc_v_1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexandre
 */
public class TableTrue {
    
    public void generateTableTrue(int numCond){
        
        String str = "%" + numCond + "s";
        int aux  = (int) Math.pow(2, numCond);
        int pot  = (int) (Math.pow(2, numCond)/2);
        int cont = 0;
        int stop = 0;
        File tableTrue = new File("tabelTrue");
        
        try {
            
            BufferedWriter bw = new BufferedWriter(new FileWriter(tableTrue));
            
            for (int i = 0; i < /*pot*/aux; i++) {
                
                bw.write(String.format(str, Integer.toBinaryString(i)).replace(' ', '0') + "\n");
                //bw.write(String.format(str, Integer.toBinaryString(i + pot)).replace(' ', '0') + "\n");
                
            }
            
            bw.close();
            
        } catch (IOException ex) {
            Logger.getLogger(TableTrue.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
//        stop = cont + pot;
//        int cond = 1;
//        try {
//            
//            BufferedWriter bw = new BufferedWriter(new FileWriter(tableTrue));
//            
//            while(cont < aux - 1){
//            
//                bw.write("Condition " + cond + "\n");
//                while (cont < stop){
//
//                    bw.write(String.format(str, Integer.toBinaryString(cont)).replace(' ', '0') + "\n");
//                    bw.write(String.format(str, Integer.toBinaryString(cont + pot)).replace(' ', '0') + "\n");
//                    cont++;
//
//                }
//            
//                pot = (int) (pot/2);
//                stop = cont + pot;           
//                cond++;
//            
//           }
//            
//           bw.close();
//           
//        } catch (IOException ex) {
//            Logger.getLogger(TableTrue.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        
                
        
    }
    
}
