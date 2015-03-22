/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.fapesp.web;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author alexandre
 */
public class Token {
    
    public  void tokenExpre(String str){
        
//        String str = "";
//        for (String s: args) {
//           str = s;
//        }        
        
        //Run netbeans
        PreProcessExpression pp = new PreProcessExpression();
//        String exp = pp.removeComment(str);
        String exp = pp.removeComment(str);
        tokenizem(exp); 
        //testes apostila
        //"((A||B) && (A||C))"
        //(A||(B&&C&&A))
        //"((A||B) && (C||D))" para analise on the fly ele aceita (FTFT) e (TFFF) para mc/dc relax
        //((false||true)&&(false||true)) =  true com ((true||false)&&(false||false))
        //(A||B) && (A||C)
        //((B && C) || D) p 25 o caso de teste 4 não colabora com mc/dc ok
        //testes repetição
        //(A||B)&&(C||A)&&(D||A)
        //(A||(B&&C&&(D||E)))
        //(A||B||C||A)
        //(A||(B&&C&&A)) neste exemplo demonstro que B e C não afetam a saída
        //testes
        //((A||B)&&(C||A)&&(C||D)&&(E||F)&&(G||A)&&(H||I)&&(J||K)&&(L||A))
        //"(A || B|| C|| D|| A || E || A || H|| K ||M ||N ||P)"
        //"((A && B)|| (C && D) || E)"
        //t1
        //"(((A.a(a.a()) & b(B.b()) ||(D|!CsssX||B)||(A!=V)| (A==V)&&(A>=V)))== A" saída ==A errada
        //(C.c.d(\"aa\") || D) OK
        //t2
        //((A!=B) == C) OK
        //"((A!=B) == ((C > 4) == true))"
        //t3
        //"(A.a(\"   ))   \") || c('('))"
        //(D(a) && (B(C>5)||C()))
        //(A|| (B && C) && D)
        
    }
    
    public static void tokenizem(String str){
        
        char strInChar[] = str.toCharArray();
        int cont = 0;
        String tokenCondition = "";
        TokenList.list = new ArrayList<>();
        
        while (cont < strInChar.length) {
            char t = strInChar[cont];
             
            switch (t) {
                case '(':  if(tokenCondition != ""){
                             if(tokenCondition != "|" || tokenCondition != "&" || tokenCondition != "(" || tokenCondition != "^"){
                                tokenCondition = tokenCondition + String.valueOf(t); 
                                //criar um contador para saber onde o método acaba
                                int contEnd = 1;
                                int inic = cont + 1;
                                cont++;
                                while(contEnd != 0){
                                    t = strInChar[cont];
                                    if(t == '('){
                                        contEnd ++;
                                    } else {
                                         if(t == ')'){
                                            contEnd --;
                                         }
                                    }
                                    cont++;
                                }                                 
                                tokenCondition = tokenCondition + str.substring(inic, cont); 
                                TokenList.list.add(tokenCondition);
                                tokenCondition = "";
                                cont--;
                                //*******
                             } else {
                                 TokenList.list.add(tokenCondition);
                                 tokenCondition = "";
                                 TokenList.list.add(String.valueOf(t));
                             }    
                           } else {
                             TokenList.list.add(String.valueOf(t));
                           }   
                           break;
                case ')':  if(tokenCondition != ""){
                             TokenList.list.add(tokenCondition);
                             tokenCondition = "";
                             TokenList.list.add(String.valueOf(t));
//                               tokenCondition = tokenCondition + ")";
                           } else {
                             TokenList.list.add(String.valueOf(t));
                           }   
                           break;
                case '^':  if(tokenCondition != ""){
                             TokenList.list.add(tokenCondition);
                             tokenCondition = "";
                             TokenList.list.add(String.valueOf(t));
//                               tokenCondition = tokenCondition + ")";
                           } else {
                             TokenList.list.add(String.valueOf(t));
                           }   
                           break;
                case '&':  if(tokenCondition != ""){
                             TokenList.list.add(tokenCondition);
                             tokenCondition = "";
                             if(strInChar[cont+1]=='&'){
                                TokenList.list.add(String.valueOf(t) + String.valueOf(t));
                                cont= cont + 1;
                             } else {
                                 TokenList.list.add(String.valueOf(t));
                             }     
                           } else {
                             if(strInChar[cont+1]=='&'){
                                TokenList.list.add(String.valueOf(t) + String.valueOf(t));
                                cont= cont + 1;
                             } else {
                                 TokenList.list.add(String.valueOf(t));
                             } 
                           }                                                    
                           break;
                case '|':  if(tokenCondition != ""){
                             TokenList.list.add(tokenCondition);
                             tokenCondition = "";
                             if(strInChar[cont+1]=='|'){
                                TokenList.list.add(String.valueOf(t) + String.valueOf(t));
                                cont= cont + 1;
                             } else {
                                 TokenList.list.add(String.valueOf(t));
                             }     
                           } else {
                             if(strInChar[cont+1]=='|'){
                                TokenList.list.add(String.valueOf(t) + String.valueOf(t));
                                cont= cont + 1;
                             } else {
                                 TokenList.list.add(String.valueOf(t));
                             } 
                           }                           
                           break;
                case ' ':  tokenCondition = tokenCondition;
                           break;
                case '!':  if(strInChar[cont+1]=='='){
                              if(tokenCondition != ""){
//                                  TokenList.list.add(tokenCondition);
//                                  tokenCondition = "";
//                                  TokenList.list.add(String.valueOf(t) + "=");
                                    tokenCondition = tokenCondition + String.valueOf(t) + "=";
                                    //TokenList.list.add(tokenCondition);
                                    //tokenCondition = "";
                              } else {
                                  //TokenList.list.add(String.valueOf(t));
                                  tokenCondition = tokenCondition + String.valueOf(t) + "=";
                              }                              
                              cont++;
                           } else {
                              TokenList.list.add(String.valueOf(t));
                           }                           
                           break;
                case '.':  String strAux[] = Token.methodToken(strInChar, cont);
                           tokenCondition = tokenCondition + strAux[0];
                           cont = Integer.parseInt(strAux[1]);
                           break;
                default:   if(tokenCondition.equals("==") || tokenCondition.equals("!=") || tokenCondition.equals(">=") 
                        || tokenCondition.equals("<=") || tokenCondition.equals("<") || tokenCondition.equals(">")){
                             TokenList.list.add(tokenCondition);
                             tokenCondition = "";
                             tokenCondition = tokenCondition + String.valueOf(t);
                           } else {
                             tokenCondition = tokenCondition + String.valueOf(t);
                           }                           
                           break;
            } 
            
            cont++;
            
        } 
        
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> listRep = new ArrayList<>();
        
        boolean repetition = false;
        
        for (int i = 0; i < TokenList.list.size(); i++) {
            
            if(!(TokenList.list.get(i).charAt(0)=='(') &&!(TokenList.list.get(i).charAt(0)==')') &&
                    !(TokenList.list.get(i).charAt(0)=='^') && !(TokenList.list.get(i).charAt(0)=='|') &&
                    !(TokenList.list.get(i).charAt(0)=='&') && !(TokenList.list.get(i).charAt(0)=='!')){
                
                if(list.contains(TokenList.list.get(i))){
                    
                     repetition = true;
                     //armazeno a repetição                     
                     listRep.add(TokenList.list.get(i));
                     
                } else {
                    
                    list.add(TokenList.list.get(i));
                    listRep.add(TokenList.list.get(i));
                    
                }
                
                
                
            }
            
        }
        
        
        if(!repetition){
            
            ArrayList<String> finalList = TokenList.list;
            int num = 1;
            
            for (int i = 0; i < list.size(); i++) {
                
                String aux = list.get(i);
                int index = finalList.indexOf(aux);
                finalList.set(index, "c" + num);
                num++; 
                
            } 
            
            BooleanExpressionCheck bec = new BooleanExpressionCheck();
            
            bec.booleanEvaluete(finalList, list.size());
            
            String out = "";
            for (int i = 0; i < finalList.size(); i++) {
                
                out = out + finalList.get(i);
                
            }
            
            
            
        } else {
            
            ArrayList<String> finalList = TokenList.list;
            int repTotal[] = new int[80];
            //HashMap contains all conditions that at least one repetition
            HashMap hm = new HashMap();
            boolean rep = false;
            int numCond = 1;
            
            for (int i = 0; i < repTotal.length; i++) {
                
                repTotal[i] = 0;
                
            }
            
            for (int i = 0; i < listRep.size(); i++) {
                
                String aux = listRep.get(i);
                
                for (int j = 0; j < finalList.size(); j++) {
                    
                    if(aux.equals(finalList.get(j))){
                        
                        int index = finalList.indexOf(aux);
                        finalList.set(index, "c" + numCond);
                        repTotal[numCond]++;
                        rep = true;
                        
                    }
                    
                }
                
                if(rep){
                    numCond++; 
                    rep = false;
                }               
                
            } 
            
            for (int i = 0; i < repTotal.length; i++) {
                
                if(repTotal[i] > 1){
                    
                    hm.put(i, repTotal[i]);
                    
                }
                
            }
            
            BooleanExpressionCheck bec = new BooleanExpressionCheck();            
            bec.booleanEvalueteRep(finalList, numCond, hm);            
            
        }
        
        
        
        
    }
    
    public static String[] methodToken(char[] t, int cont){
        
        String str = "";        

        int inic = 0;
        
        
        while(t[cont] !=  '('){
            
            str = str + String.valueOf(t[cont]);
            cont++;
        
        }
        
        str = str + String.valueOf(t[cont]);
        
        cont = cont + 1;
        
        while(t[cont] !=  ')'){
            
            if(t[cont] == '('){
              
              String strAux[] = Token.methodToken(t, cont);
              str = str + strAux[0];
              cont = Integer.parseInt(strAux[1]);
              
            } else {
                
                str = str + String.valueOf(t[cont]);
                cont++;
                
            }
                    
        }
        
        str = str + String.valueOf(')'); 
        String answer[] = new String[2];
        answer[0] = str;
        answer[1] = String.valueOf(cont);
        return answer;
        
    }
    
    
    
        
}
