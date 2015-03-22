/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.fapesp.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
                
                System.out.println("More than 25th conditions");
                System.exit(0);
                
            }
            
            try {
                
                BufferedReader br = new BufferedReader(new FileReader(new File( System.getProperty("user.home") + "/Tables/cond" + size)));
                String str;  
                String val1;  
                String val2; 
                String expre1;
                String expre2;
                String[] val1A;
                String[] val2A;
                String numCond = "";
                
                while(br.ready()){                    
                    
                    str = br.readLine();
                    
                    if(str.contains("Condition")){                        
                        
                        RequerimentMCDC_V_4.outPutWFile.write("\t\t\t" + str + "\n");
                        numCond = str;
                        numCond = numCond.replace("Condition ", "");
                        val1 = br.readLine();
                        val1 = changeVal(val1);
                        val1A = val1.split(" ");
                        expre1 = finalExpression(finalList, val1A);                        
                        val2 = br.readLine();
                        val2 = changeVal(val2);
                        val2A = val2.split(" ");
                        expre2 = finalExpression(finalList, val2A); 
                        boolean boolExpr1 = evalExpression(expre1);
                        boolean boolExpr2 = evalExpression(expre2);  
                            
                        if(boolExpr1 ^ boolExpr2){
                                
                            if(boolExpr1){
                                    
                                    RequerimentMCDC_V_4.outPutWFile.write("\t\t\t\t" + 
                                            expre1 + " = true " + " and  " + expre2 + " = false" + "\n");
                                    TokenList.requirementList.add(expre1 + " " + expre2 + " " + numCond + " " + 0);
                                    
                                } else {
                                    
                                    RequerimentMCDC_V_4.outPutWFile.write("\t\t\t\t" + 
                                            expre1 + " = false " + " and  " + expre2 + " =  true" + "\n");
                                    TokenList.requirementList.add(expre2 + " " + expre1 + " " + numCond + " " + 0);
                                    
                                }
                                
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
                        boolean boolExpr1 = evalExpression(expre1);
                        boolean boolExpr2 = evalExpression(expre2);  
                            
                        if(boolExpr1 ^ boolExpr2){
                                
                            if(boolExpr1){
                                
//                                System.out.println(expre1 + " = true " + " and  " + expre2 + " = false");
                                RequerimentMCDC_V_4.outPutWFile.write("\t\t\t\t" + 
                                            expre1 + " = true " + " and  " + expre2 + " = false" + "\n");
                                TokenList.requirementList.add(expre1 + " " + expre2 + " " + numCond + " " + 0);
                                
                            } else {
                                    
//                                System.out.println(expre1 + " = false " + " and  " + expre2 + " =  true");
                                RequerimentMCDC_V_4.outPutWFile.write("\t\t\t\t" 
                                            + expre1 + " = false " + " and  " + expre2 + " =  true" + "\n");
                                TokenList.requirementList.add(expre2 + " " + expre1 + " " + numCond + " " + 0);
                                    
                            }
                                
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
                
                numCond--;
                BufferedReader br = new BufferedReader(new FileReader(new File(System.getProperty("user.home") + "/Tables/cond" + numCond)));
                String str;
                String key;
                int keyNum = 0;
                String val1;  
                String val2; 
                String expre1;
                String expre2;
                String[] val1A;
                String[] val2A;
                String condRep = "";
                
                while(br.ready()){                    
                    
                    str = br.readLine();  
                                                
                    if(str.contains("Condition")){
                        
                        key = str.replace("Condition ","");
                        keyNum = Integer.parseInt(key);
                        RequerimentMCDC_V_4.outPutWFile.write("\t\t\t" + str + "\n");
                        val1 = br.readLine();
                        val1 = changeVal(val1);
                        val1A = val1.split(" ");
                        expre1 = finalExpressionRep(finalList, val1A);
                        val2 = br.readLine();
                        val2 = changeVal(val2);
                        val2A = val2.split(" ");
                        expre2 = finalExpressionRep(finalList, val2A);                   
                        boolean boolExpr1 = evalExpression(expre1);
                        boolean boolExpr2 = evalExpression(expre2);  
                            
                        if(boolExpr1 ^ boolExpr2){
                                
                            if(boolExpr1){
                                    
                                RequerimentMCDC_V_4.outPutWFile.write("\t\t\t\t" 
                                            + expre1 + " = true " + " and  " + expre2 + " = false" + "\n");
                                
                                if(hm.containsKey(keyNum)){
                                        
                                        condRep = expre1 + " " + expre2 + " " + keyNum;
                                        RequerimentMCDC_V_4.outPutWFile.write("\t\t\t\t" + 
                                                independencyOfRepCondition(keyNum, val1A, val2A, hm, finalList, condRep) + "\n");
                                
                                } else {
                                    
                                    TokenList.requirementList.add(expre1 + " " + expre2 + " " + keyNum + " " + 0);
                                    
                                }
                                
                            } else {
                                    
                                    RequerimentMCDC_V_4.outPutWFile.write("\t\t\t\t" + expre1 
                                            + " = false " + " and  " + expre2 + " =  true" + "\n");
                                    
                                    if(hm.containsKey(keyNum)){
                                        
                                        condRep = expre1 + " " + expre2 + " " + keyNum;
                                        RequerimentMCDC_V_4.outPutWFile.write("\t\t\t\t" + 
                                                independencyOfRepCondition(keyNum, val1A, val2A, hm, finalList, condRep) + "\n");
                                        
                                    } else {
                                        
                                        TokenList.requirementList.add(expre2 + " " + expre1 + " " + keyNum + " " + 0);
                                        
                                    }
                                    
                            }
                                
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
                        boolean boolExpr1 = evalExpression(expre1);
                        boolean boolExpr2 = evalExpression(expre2);  
                            
                        if(boolExpr1 ^ boolExpr2) {
                                
                            if(boolExpr1) {
                                
                                RequerimentMCDC_V_4.outPutWFile.write("\t\t\t\t" + 
                                            expre1 + " = true " + " and  " + expre2 + " = false" + "\n");
                                    
                                    if(hm.containsKey(keyNum)){
                                        
                                        condRep = expre1 + " " + expre2 + " " + keyNum;
                                        RequerimentMCDC_V_4.outPutWFile.write("\t\t\t\t" + 
                                                independencyOfRepCondition(keyNum, val1A, val2A, hm, finalList, condRep) + "\n");
                                        
                                    } else {
                                        
                                        TokenList.requirementList.add(expre1 + " " + expre2 + " " + keyNum + " " + 0);
                                        
                                    }
                                    
                            } else {
                                    
                                    RequerimentMCDC_V_4.outPutWFile.write("\t\t\t\t" + 
                                            expre1 + " = false " + " and  " + expre2 + " =  true" + "\n");
                                    
                                    if(hm.containsKey(keyNum)){
                                        
                                        condRep = expre1 + " " + expre2 + " " + keyNum;
                                        RequerimentMCDC_V_4.outPutWFile.write("\t\t\t\t" + 
                                                independencyOfRepCondition(keyNum, val1A, val2A, hm, finalList, condRep) + "\n");
                                        
                                    } else {
                                        
                                        TokenList.requirementList.add(expre2 + " " + expre1 + " " + keyNum + " " + 0);
                                        
                                    }
                                    
                            }
                                
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
            
            if(d=='0') {
                
                aux = aux + " " + "false";
                
            } else {
                
                aux = aux + " " + "true";
                
            }
            
        }
        
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
    

    public boolean evalExpression(String str) {       

        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine se = sem.getEngineByName("JavaScript");
        String myExpression = str;
        String aux;
        File fileErrorEval = new File("logErroEval");
            
        try {
            
            aux = se.eval(myExpression).toString();
            
            if(aux.equals("false")){
                
              return false;
                
            } else {

              return true;
                
            }   
            
        } catch (ScriptException ex) {
            
                try {
                    
                    BufferedWriter bw = new BufferedWriter(new FileWriter(fileErrorEval));
                    bw.write(str);
                    bw.close();
                    
                } catch (IOException ex1) {
                    
                    System.out.println("Erro: output File Error " + ex1);
                    
                }
                
            return false;
            
        }  
             
    }
    
    public String independencyOfRepCondition(int key, String[] val1, String[] val2, HashMap hm, ArrayList<String> finalList, String condRep){
        
        String aux;
        String expre1;
        String expre2;
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
            
            boolean expre1Bool = evalExpression(expre1);
            boolean expre2Bool = evalExpression(expre2);
            
            if(expre1Bool ^ expre2Bool){
                    
                if(out.equals("")){
                    
                    condRep = condRep + " " + j;
                    TokenList.requirementList.add(condRep);
                    out = out + "r" + j + "= I";
                    
                } else {
                        
                    out = out + " " + "r" + j + "= X";
                    
                }
                    
            }         
            
        }
        
        if(out.equals("")) {
            
            condRep = condRep + " " + "DISCARD";
            TokenList.requirementList.add(condRep);
            return "logical correlation between repetitions - discard";
            
        }
       
        return out;
        
    }
    
}
