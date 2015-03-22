/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.fapesp.web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author alexandre
 */
public class PreProcessExpression {
    
    public String removeComment(String str){
        
        int beg = 0;
        int end = 0;
        String subString = "";
        String subStringAux = "";
        Pattern pattern  = Pattern.compile("'(.*?)'");
        Pattern pattern2 = Pattern.compile("\"(.*?)\"");
        Matcher matcher = pattern.matcher(str); 
        Matcher matcher2 = pattern2.matcher(str); 
        
        while (matcher.find()) {
            
            beg = matcher.start();
            end = matcher.end();
            subString = str.substring(beg, end);
            subStringAux = subString.replace(")", "?");
            subStringAux = subStringAux.replace("(", "?");
            str = str.replace(subString, subStringAux);            

        }
        
        while (matcher2.find()) {
            
            beg = matcher2.start();
            end = matcher2.end();
            subString = str.substring(beg, end);
            subStringAux = subString.replace(")", "?");
            subStringAux = subStringAux.replace("(", "?");
            str = str.replace(subString, subStringAux);
            
        }
        
        
        return str;
        
    }
    
}
