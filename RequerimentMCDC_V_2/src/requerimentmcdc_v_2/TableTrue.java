/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package requerimentmcdc_v_2;

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
        int pot  = (int) Math.pow(2, numCond);
        int aux  = (int) pot/2;
        int aux2 = aux;
        int cont = 0;
        int stop = 1;
        
        System.out.println("Condition " + stop);
        
        while(stop <= numCond){           
            
            while(cont < aux2 && aux2 < pot) {
                 
                 System.out.println(String.format(str, Integer.toBinaryString(cont)).replace(' ', '0'));
                 System.out.println(String.format(str, Integer.toBinaryString(cont + aux)).replace(' ', '0'));
                 cont++;
                 
            }   
            
            if(aux2 < pot){
                
                cont =  cont + aux;
                aux2  = cont + aux;
                
            } else {
                
                stop++;
                cont = 0;
                aux = (int)aux/2;
                aux2 = aux;
                if(stop <= numCond){
                    System.out.println("Condition " + stop);
                }                
                
            }
            
            
        }
        
  
    }
    
}
