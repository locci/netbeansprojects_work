/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testeboolean2;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alexandre
 */
public class MyParser {
    
    public static void main(String[] args) {
        
        String str = "(node.getNodeType()==Node.ELEMENT_NODE&&semanticAttributes&&node.hasAttributes()&&(node.getAttributes().getNamedItem(VALUE)!=null||node.getAttributes().getNamedItem(LOCATION)!=null||node.getAttributes().getNamedItem(REF_ID)!=null||node.getAttributes().getNamedItem(PATH)!=null||node.getAttributes().getNamedItem(PATHID)!=null))";
        char c[]   = str.toCharArray();
        StringBuilder sb = new StringBuilder(); 
        List<String> strings = new ArrayList<String>();
        
        for (int i = 0; i < c.length; i++) {
            char d = c[i];
            
            if(d != ' '){
                
                if(d == '&' || d == '(' || d == ')' || d == '|' || d == '^'){
                    if(sb.length() != 0) strings.add(sb.toString());
                    strings.add(String.valueOf(d));
                    sb = new StringBuilder();
                } else {
                  sb.append(d);
                }
                
            } 
            
        }
        
        String[] finalArray = strings.toArray(new String[0]);  // we turn our list to an array

        for (String string : finalArray) {
            System.out.println(string);  // prints out everything in our array
        }
        
    }
    
}
