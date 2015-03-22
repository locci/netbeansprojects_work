/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package requerimentmcdc_v_3;

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
public class TableMaker {
    
//    public void generateTableTrue(int numCond){
//        
//        String str = "%" + numCond + "s";
//        String fileName = "/home/alexandre/Tables/cond" + numCond;
//        int pot  = (int) Math.pow(2, numCond);
//        int aux  = (int) pot/2;
//        int aux2 = aux;
//        int cont = 0;
//        int stop = 1;
//        File file = new File(fileName);
//        
//        try {
//            
//            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
//                
//                bw.write("Condition " + stop + "\n");
//                
//                while(stop <= numCond){
//                    
//                    while(cont < aux2 && aux2 < pot) {
//                        
//                        bw.write(String.format(str, Integer.toBinaryString(cont)).replace(' ', '0') + "\n");
//                        bw.write(String.format(str, Integer.toBinaryString(cont + aux)).replace(' ', '0') + "\n");
//                        cont++;
//                        
//                    }
//                    
//                    if(aux2 < pot){
//                        
//                        cont =  cont + aux;
//                        aux2  = cont + aux;
//                        
//                    } else {
//                        
//                        stop++;
//                        cont = 0;
//                        aux = (int)aux/2;
//                        aux2 = aux;
//                        if(stop <= numCond){
//                            bw.write("Condition " + stop + "\n");
//                        }
//                        
//                    }
//                    
//                    
//                }
//            }
//            
//        } catch (IOException ex) {
//            
//            Logger.getLogger(TableMaker.class.getName()).log(Level.SEVERE, null, ex);
//            
//        }     
//        
//  
//    }
//    
//    public static void main(String args[]){
//        
//        TableMaker tt = new TableMaker();
//        
//        for (int i = 21; i <= 25; i++) {
//            tt.generateTableTrue(i);
//        }
//        
//    }
    
}
