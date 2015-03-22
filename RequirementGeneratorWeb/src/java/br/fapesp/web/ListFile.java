/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.fapesp.web;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author alexandre
 */
public class ListFile {
    
    public static void makeList(String str){
        
        File file = new File(str);
        File listFile[] = file.listFiles();
        
        for (int i = 0; i < listFile.length; i++) {
            
            if(listFile[i].isFile() && listFile[i].getName().endsWith(".java")){
                List.listFile.add(listFile[i].getAbsolutePath());
            } else {
                makeList(listFile[i].getAbsolutePath());
            }           
                   
        }         
        
    }
    
}
