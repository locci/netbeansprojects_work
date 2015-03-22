/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tokenexpression;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *
 * @author alexandre
 */
public class BooleanExpressionCheck {
    
    public void booleanEvaluete(ArrayList<String> finalList, int size){
            
            if(size > 25){
                
                System.out.println("More than 25 conditions");
                System.exit(0);
                
            }
            
            try {
                
                //BufferedWriter bw = new BufferedWriter(new FileWriter(new File("/home/alexandre/outPut")));
                BufferedReader br = new BufferedReader(new FileReader(new File("/home/alexandre/Tables/cond" + size)));
                String str;  
                String val1;  
                String val2; 
                String expre1;
                String expre2;
                String[] val1A;
                String[] val2A;
                
                
                
                
                while(br.ready()){                    
                    
                    str = br.readLine();
                    if(str.contains("Condition")){
                        
                        System.out.println(str);
                        val1 = br.readLine();
                        val1 = changeVal(val1);
                        val1A = val1.split(" ");
                        expre1 = finalExpression(finalList, val1A);
                        
                        val2 = br.readLine();
                        val2 = changeVal(val2);
                        val2A = val2.split(" ");
                        expre2 = finalExpression(finalList, val2A);                   
                        
                        try {
                            
                            boolean boolExpr1 = evalExpression(expre1);
                            boolean boolExpr2 = evalExpression(expre2);  
                            
                            if(boolExpr1 ^ boolExpr2){
                                
                                if(boolExpr1){
                                    System.out.println(expre1 + " = true " + " and  " + expre2 + " = false");
                                } else {
                                    System.out.println(expre1 + " = false " + " and  " + expre2 + " =  true");
                                }
                                
                            }
                            
                        } catch (ScriptException ex) {
                            //Logger.getLogger(BooleanExpressionCheck.class.getName()).log(Level.SEVERE, null, ex);
                            System.out.println("Erro 1" + expre1 + " " + expre2);
                        } 
                       
                    } else {
                        
                        val1 = str;
                        val1 = changeVal(val1);
                        val1A = val1.split(" ");
                        expre1 = finalExpression(finalList, val1A);
                        val2 = br.readLine();
                        val2 = changeVal(val2);
                        val2A = val2.split(" ");
                        expre2 = finalExpression(finalList, val2A);
                        
                        
                        try {
                            
                            boolean boolExpr1 = evalExpression(expre1);
                            boolean boolExpr2 = evalExpression(expre2);  
                            
                            if(boolExpr1 ^ boolExpr2){
                                
                                if(boolExpr1){
                                    System.out.println(expre1 + " = true " + " and  " + expre2 + " = false");
                                } else {
                                    System.out.println(expre1 + " = false " + " and  " + expre2 + " =  true");
                                }
                                
                            }
                            
                        } catch (ScriptException ex) {
                            System.out.println("Erro 2" + expre1 + " " + expre2);
                        }                    

                    }
                    
                }
                
                br.close();
                
            } catch (IOException ex) {
                
                Logger.getLogger(Token.class.getName()).log(Level.SEVERE, null, ex);
                
            }
        
    }
    
    public void booleanEvalueteRep(ArrayList<String> finalList, int numCond, HashMap hm){
        
            if(numCond > 25){
                
                System.out.println("More than 25 conditions");
                System.exit(0);
                
            }
            
            try {
                
                //BufferedWriter bw = new BufferedWriter(new FileWriter(new File("/home/alexandre/outPut")));
                numCond--;
                BufferedReader br = new BufferedReader(new FileReader(new File("/home/alexandre/Tables/cond" + numCond)));
                String str;
                String key;
                int keyNum = 0;
                String val1;  
                String val2; 
                String expre1;
                String expre2;
                String[] val1A;
                String[] val2A;
                
                
                
                
                while(br.ready()){                    
                    
                    str = br.readLine();            
                    if(str.contains("Condition")){
                        
                        key = str.replace("Condition ","");
                        keyNum = Integer.parseInt(key);
                        
                        System.out.println(str);
                        val1 = br.readLine();
                        val1 = changeVal(val1);
                        val1A = val1.split(" ");
                        expre1 = finalExpressionRep(finalList, val1A);
                        
                        val2 = br.readLine();
                        val2 = changeVal(val2);
                        val2A = val2.split(" ");
                        expre2 = finalExpressionRep(finalList, val2A);                   
                        
                        try {
                            
                            boolean boolExpr1 = evalExpression(expre1);
                            boolean boolExpr2 = evalExpression(expre2);  
                            
                            if(boolExpr1 ^ boolExpr2){
                                
                                if(boolExpr1){                                    
                                    System.out.println(expre1 + " = true " + " and  " + expre2 + " = false");
                                    if(hm.containsKey(keyNum)){
                                        System.out.println(independencyOfRepCondition(keyNum, val1A, val2A, hm, finalList));
                                    }
                                } else {
                                    System.out.println(expre1 + " = false " + " and  " + expre2 + " =  true");
                                    if(hm.containsKey(keyNum)){
                                        System.out.println(independencyOfRepCondition(keyNum, val1A, val2A, hm, finalList));
                                    }
                                }
                                
                            }
                            
                        } catch (ScriptException ex) {
                            //Logger.getLogger(BooleanExpressionCheck.class.getName()).log(Level.SEVERE, null, ex);
                            System.out.println("Erro 3" + expre1 + " " + expre2);
                        } 
                       
                    } else {
                        
                        val1 = str;
                        val1 = changeVal(val1);
                        val1A = val1.split(" ");
                        expre1 = finalExpressionRep(finalList, val1A);
                        val2 = br.readLine();
                        val2 = changeVal(val2);
                        val2A = val2.split(" ");
                        expre2 = finalExpressionRep(finalList, val2A);
                        
                        
                        try {
                            
                            boolean boolExpr1 = evalExpression(expre1);
                            boolean boolExpr2 = evalExpression(expre2);  
                            
                            if(boolExpr1 ^ boolExpr2){
                                
                                if(boolExpr1){
                                    System.out.println(expre1 + " = true " + " and  " + expre2 + " = false");
                                    if(hm.containsKey(keyNum)){
                                        System.out.println(independencyOfRepCondition(keyNum, val1A, val2A, hm, finalList));
                                    }
                                } else {
                                    System.out.println(expre1 + " = false " + " and  " + expre2 + " =  true");
                                    if(hm.containsKey(keyNum)){
                                        System.out.println(independencyOfRepCondition(keyNum, val1A, val2A, hm, finalList));
                                    }
                                }
                                
                            }
                            
                        } catch (ScriptException ex) {
                            //Logger.getLogger(BooleanExpressionCheck.class.getName()).log(Level.SEVERE, null, ex);
                            System.out.println("Erro 4" + expre1 + " " + expre2);
                        }                    

                    }
                    
                }
                
                br.close();
                
            } catch (IOException ex) {
                
                Logger.getLogger(Token.class.getName()).log(Level.SEVERE, null, ex);
                
            }
        
    }
    
    public String changeVal(String str){
        
        char c[] = str.toCharArray();
        String aux = "";
        
        for (int i = 0; i < c.length; i++) {
            char d = c[i];
            
            if(d=='0'){
                aux = aux + " " + "false";
            } else {
                aux = aux + " " + "true";
            }
            
        }
        //int size = aux.length();
        aux = aux.trim();
        return aux;
        
    }
    
    public String finalExpression(ArrayList<String> finalList, String[] strA){
        
        String aux="";
        int num = 0;
        
        for (int i = 0; i < finalList.size(); i++) {
            String get = finalList.get(i);
            
            if(get.contains("c" + (num + 1))){
                
                aux = aux + strA[num];
                num++;
                
            } else {
                
                aux = aux + get;
                
            }
            
        }
        
        return aux;
    }
    
    public String finalExpressionRep(ArrayList<String> finalList, String[] strA){
        
        String aux="";
        String strNum="";
        int index = 0;
        int num = 0;
        
        for (int i = 0; i < finalList.size(); i++) {
            String get = finalList.get(i);
            
            if(get.contains("c")){
                
                strNum = get.replace("c", "");
                index = Integer.parseInt(strNum) -1;
                aux = aux + strA[index];
                
            } else {
                
                aux = aux + get;
                
            }          
            
            
        }
        
        return aux;
        
    }
    

    public boolean evalExpression(String str) throws ScriptException {
        
        

//            ScriptEngineManager sem = new ScriptEngineManager();
//            ScriptEngine se = sem.getEngineByName("JavaScript");      
//            String aux = se.eval(str).toString();  
            
            ScriptEngineManager sem = new ScriptEngineManager();
            ScriptEngine engine = sem.getEngineByName("JavaScript");
            
            // let's get some work done!
            Object scriptResult = engine.eval("Boolean(" + str +")");
            String aux = scriptResult.toString();
                        
            if(aux.equals("false")){
                
                return false;
                
            } else {

                return true;
                
            }    
        
            
    }
    
    public String independencyOfRepCondition(int key, String[] val1, String[] val2, HashMap hm, ArrayList<String> finalList){
        
        String aux = "";
        String expre1  = "";
        String expre2 = "";
        String out = "";
        int auxNum;
        int numRep = 1;
        int numRepMap = Integer.parseInt(hm.get(key).toString());
        ArrayList<String> listRep = new ArrayList<>(); 
        
            
        for (int i = 0; i < finalList.size(); i++) {
            String get = finalList.get(i);            
            
            if(get.contains("c")){
                
                aux    = get.replace("c", "");
                auxNum = Integer.parseInt(aux);
                
                if(key == auxNum){
                    
                    get = get.replace("c" + key, "r" + numRep);
                    listRep.add(get);
                    numRep++;
                    
                } else {
                    
                    listRep.add(get);
                    
                }
                
            } else {
                
                listRep.add(get);
                
            }
            
        }    
        
        String demo = "";
        
        for (int i = 0; i < listRep.size(); i++) {
            String get = listRep.get(i);
            
            demo = demo + get;
            
        }
        
        System.out.println(demo);
        
        for (int j = 1; j < numRepMap + 1; j++) {
            
            expre1 = "";
            expre2 = "";
            
            for (int i = 0; i < listRep.size(); i++) {
            
               String get_1 = finalList.get(i);
               String get_2 = listRep.get(i);
               
               if(get_1.contains("c")){
                   
                   if(get_2.contains("r")){
                       
                       String auxIndexRep = get_2.replace("r", "");
                       int indexRep       = Integer.parseInt(auxIndexRep);
                       
                       if(indexRep == j){
                           
                            String auxIndex = get_1.replace("c", "");
                            int index       = Integer.parseInt(auxIndex);
                            expre1 = expre1 + val1[index-1];
                            
                            if(val1[index-1].equals("false")){
                                
                                expre2 = expre2 + "true"; 
                                
                            } else {
                                
                                expre2 = expre2 + "false"; 
                                
                            }                         
                           
                       } else {
                           
                            String auxIndex = get_1.replace("c", "");
                            int index       = Integer.parseInt(auxIndex);
                            expre1 = expre1 + val1[index - 1];
                            expre2 = expre2 + val1[index - 1];   
                           
                       }
                       
                   } else {
                       
                       String auxIndex = get_1.replace("c", "");
                       int index       = Integer.parseInt(auxIndex);
                       //erro aqui
                       expre1 = expre1 + val1[index - 1];
                       expre2 = expre2 + val1[index - 1];
                       
                   }
                   
               } else {
                   
                   expre1 = expre1 + get_1;
                   expre2 = expre2 + get_2;
                   
               } 
               
            
            }
            
            try {
                
                boolean expre1Bool = evalExpression(expre1);
                boolean expre2Bool = evalExpression(expre2);
                
                if(expre1Bool ^ expre2Bool){
                    
                    if(out.equals("")){
                        out = out + "r" + j + "= I";
                    } else {
                        
                        out = out + " " + "r" + j + "= X";
                    
                    }
                    
                }
                
            } catch (ScriptException ex) {
                Logger.getLogger(BooleanExpressionCheck.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(out.equals("")){
            
            return "correlação logica entre repetições - descartar";
            
        }
       
        return out;
        
    }
    
}
