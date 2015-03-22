/*Programmer: Alexander L. Martins 
 *date: 04/2014
 *client: SAC Paper
 *coach: Ana C. V. Melo
 *description: this class handing files in a source diretory.
 * *library: 
 */

package metric;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author alexandre
 */
public class FileManeger {
    
    public void incertIntoTheList(String adress){
        
        File file = new File(adress);
        File [] fileArray = file.listFiles();
        
        for (File fileArray1 : fileArray) {
            if (fileArray1.isFile()) {
                boolean add;
                Metric.todosOsArquivos++;
                //System.out.println(fileArray1.getName() + " " +Metric.todosOsArquivos);
                if(fileArray1.getName().endsWith(".java") &&
                        !fileArray1.getName().endsWith(".java~")){  
                           //System.out.println(fileArray1.getName());
                           add = listOfFilie.add(fileArray1); 
                          
                }
            } else {                
                incertIntoTheList(fileArray1.getAbsolutePath());                  
            }
        }               
        
    }
    
    public void openFile(File file){       
        
        try {
            
            BufferedReader bufReader = new BufferedReader(new FileReader(file));
            String line = "";
            String arrayLine[];
            int cont = 0;
            
            while(bufReader.ready()){
                //eu preciso checar o que este método trim faz!?
                
                line = bufReader.readLine().trim();
                String str = line;
                
                if(!line.startsWith("//") && !line.startsWith("* ") && !line.startsWith("/* ") && !line.startsWith("/*") && !line.startsWith("/**")){
                    
                    //if(line.startsWith("if ") || line.startsWith("while ") ||line.startsWith("if(") || line.startsWith("while(")){
                     if(line.contains("if ") || line.contains("while ") ||line.contains("if(") || line.contains("while(")){ 
                         
                        if(line.contains("&&") || line.contains("||") || line.contains("|") ||  line.contains("&")){
                            
                            if(line.contains("&&") || line.contains("||")){
                                line = line.replace("||", " $ ");
                                line = line.replace("&&", " $ ");
                            }
                            
                            if(line.contains("&") || line.contains("|")){
                               // System.out.println(line);
                                line = line.replace("|", " $ ");
                                line = line.replace("&", " $ ");
                                
                            }
                            
                            if(line.contains(" $ ")){
                                //System.out.println(line); 
                            }
                            
                            StringTokenizer strTkn = new StringTokenizer(line, "$");
                            ArrayList<String> arrLis = new ArrayList<String>();

                            while(strTkn.hasMoreTokens()){
                                //System.out.println(strTkn.nextToken());
                                arrLis.add(strTkn.nextToken());
                            }
                            
                            //arrayLine = line.split(" $ ");
                            //System.out.println(arrLis.size()); 
                            if(arrLis.size() > 3){
                                System.out.println(str);
                            }
                            Metric.num[arrLis.size()]++; 
                            
                            //arrLis.clear();
                            //Versão antiga@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                            //line = line.replace(" || ", " or ");
                            //line = line.replace(" && ", " and ");
                            //Pattern pAnd = Pattern.compile(" and ");
                            //Matcher mAnd = pAnd.matcher(line);
                            //Pattern pOr = Pattern.compile(" or ");
                            //Matcher mOr = pOr.matcher(line);
                            //int contAnd = 0;
                            //int contOr = 0;
                            
                            //while(mAnd.find()){
                                
                                //contAnd++;
                                
                            //}
                            //System.out.println(contAnd);
                            //while(mOr.find()){ 
                                
                                //contOr++;
                                
                            //}
                            //if((contAnd + contOr) > 4) System.out.println(line);
                            //Metric.num[contAnd + contOr]++; 
                            //Versão antiga@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@                           
                            
                        } else {
                            
                            //System.out.println(str);
                            Metric.num[1]++;
                            
                        }
                        
                        Metric.numberOfBranch ++;
                        
                    } else {
                        //System.out.println(str);
                    }
                    
                }
                
                
            }
            
            bufReader.close();
            
        } catch (FileNotFoundException ex) {
            
            System.out.println("Error when open file " + ex.getLocalizedMessage());
            
        } catch (IOException ex) {
            
            Logger.getLogger(FileManeger.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
    }
    
    public static ArrayList<File> listOfFilie = new ArrayList<>();
    
}
