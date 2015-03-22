/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metric_v3;

/**
 *
 * @author alexandre
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import recoder.CrossReferenceServiceConfiguration;
import recoder.ParserException;
import recoder.convenience.ForestWalker;
import recoder.io.PropertyNames;
import recoder.io.SourceFileRepository;
import recoder.java.CompilationUnit;
import recoder.java.Expression;
import recoder.java.ProgramElement;
import recoder.java.declaration.ClassDeclaration;
import recoder.java.declaration.MethodDeclaration;
import recoder.java.statement.BranchStatement;
import recoder.java.statement.LoopStatement;
import recoder.kit.UnitKit;
import recoder.service.DefaultErrorHandler;

public class Metric_v3 {
    
    public static List<CompilationUnit> serviceConfiguration (String srcPath) {
        
        
		//create a service configuration
		CrossReferenceServiceConfiguration crsc = new CrossReferenceServiceConfiguration();
                
                 MyErrorHandler meh = new MyErrorHandler();
                 meh.setErrorThreshold(30000);
                 
		
		//set the path to source code ("src" folder). 
		//multiple source code paths, as well as paths to libraries, can be separated via ":" or ";".
		crsc.getProjectSettings().setProperty(PropertyNames.INPUT_PATH, srcPath);
		crsc.getProjectSettings().ensureSystemClassesAreInPath();
		crsc.getProjectSettings().setErrorHandler(meh);
                
		//tell Recoder to parse all .java files it can find in the directory "src"
		SourceFileRepository sfr = crsc.getSourceFileRepository();
		List<CompilationUnit> cul = null;
		try {
			cul = sfr.getAllCompilationUnitsFromPath();
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
                    System.out.println(e.getLocalizedMessage());
		}
		crsc.getChangeHistory().updateModel();
		return cul;

    }

public static void main(String[] args) {
    
   
    
    JFileChooser chooser = new JFileChooser();
    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int ret = chooser.showOpenDialog(null);
    String str = chooser.getSelectedFile().getAbsolutePath(); 
    String frame = "";
    List<CompilationUnit> cus = serviceConfiguration(str);
    ForestWalker fw = new ForestWalker(cus);
    
    File file = new File("log");
    File fileErr = new File("logErr");
    File fileDo = new File("logDo");
    boolean BranchLoop = false;
    boolean BranchLoopFor = false;
    
        try {
            BufferedWriter bDo = new BufferedWriter(new FileWriter(fileDo));
            BufferedWriter bwErr;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bwErr = new BufferedWriter(new FileWriter(fileErr));
            String pathFile = null;
            while (fw.next()) {
                
                ProgramElement pe = fw.getProgramElement();
            
                if (pe instanceof ClassDeclaration) {
                    
                    bw.write("*********" + "\n");
                    pathFile = UnitKit.getCompilationUnit(pe).getDataLocation().toString().replace("FILE:", "");
                    bw.write(UnitKit.getCompilationUnit(pe).getDataLocation().toString() + "\n");
                    ClassDeclaration cls = (ClassDeclaration)pe;
                    bw.write("\t Class " + cls.getName() + "\n");
                    
                }
                
                if (pe instanceof MethodDeclaration) {
                    
                    MethodDeclaration ca = (MethodDeclaration)pe;
                    int inic = ca.getStartPosition().getLine();
                    int end  = ca.getEndPosition().getLine();
                    bw.write("\t\t Method " + ca.getName() + "\n");
                    bw.write("\t\t Method scope  " + inic + " " + end + "\n");
                    
                }
                
                if (pe instanceof LoopStatement || pe instanceof BranchStatement) {
                    
                    
                    int line = pe.getStartPosition().getLine();
                    String isTryFor = pe.getClass().getSimpleName();
                    
                    if (isTryFor.equals("For")){
                        
                        BranchLoopFor = false;
                        
                    } else {
                        
                        BranchLoopFor = true;
                        
                    }                  
                    
                    if(!isTryFor.equals("Try") && !isTryFor.equals("Do")){
                        
                        BranchLoop = true;
                        
                        ListMetric.statementLoopBranch ++;
                        
                        bw.write("\t\t\t" + isTryFor +
                                " start line "+ line +
                                " end line "+ pe.getEndPosition().getLine() +
                                " start column " + pe.getStartPosition().getColumn() + "\n");                     
                      
                    } else {
                        
                        if(isTryFor.equals("Do")){
                            
                            bDo.write(pathFile + "\n");
                            bDo.write("\t\t\t" + isTryFor +
                                " start line "+ line +
                                " end line "+ pe.getEndPosition().getLine() +
                                " start column " + pe.getStartPosition().getColumn() + "\n");                          
                            
                            
                        }
                        
                    }                 
                    
                }
                
                if(BranchLoop && pe instanceof Expression /*&& !(pe instanceof Statement) && !(pe instanceof Literal) && !(pe instanceof TerminalProgramElement)*/){
                        
                        BranchLoop = false;
                        Expression bl = (Expression)pe;
                        String expression = bl.toSource().replace("\n", "").trim();
                        String aux = expression.replace(" ", "");
                        String expr = Metric_v3.expression(aux);
                        
                        if(!expr.startsWith("ERROR ")){
                            
                            if(BranchLoopFor){
                                //bw.write("\t\t\texpression " + expression +"\n");
                                bw.write("\t\t\texpression " + expr + "\n");
                                int numCond = Metric_v3.contCondition(aux);
                                String rep = "";
                                if(Metric_v3.equalNumberCondition(expr) == 1){
                                    rep = "Yes Rep";
                                } else {
                                    rep = "No Rep";
                                }
                                Metric_v3.recorMark(numCond);
                                bw.write("\t\t\tcondition number " + numCond + "\n");
                                bw.write("\t\t\tcondition repetition " + rep + "\n");
                            } else {
                                Metric_v3.recorMark(1);
                                bw.write("\t\t\texpression (****)" +"\n");
                                bw.write("\t\t\tcondition number " + 1 + "\n");
                            }
                            
                        } else {
                            
                            ListMetric.statementLoopBranchError++;
                            bwErr.write(pathFile + "\n");
                            bwErr.write(expression + "\n");                           
                            bwErr.write("*************************"+ "\n");
                            
                        }                        
                                                
                }
                
            }
            bw.write("*********" + "\n");
            bw.close();
            bDo.close();
        }
            bwErr.close();
            
            Metric_v3.printHash();
            
        } catch (IOException ex) {
            Logger.getLogger(Metric_v3.class.getName()).log(Level.SEVERE, null, ex);
        }   
      
    
  }
  
  public static String expression(String exp){
      
      int inic = -1;
      int end  = -1;
      int cont =  0;
      int index = 0;
      
       
      for (int i = index; i < exp.length(); i++) {
          
          if(exp.charAt(i) == '('){
             
              cont++;
              
          } else {
              
              if(exp.charAt(i) == ')'){
             
                  cont--;
                  end = i + 1;
                  if(cont == 0) {
                      break;
                  }
                  
              }
          }
          
      }
      
      if(cont == 0){
          
//          exp = exp.replace("\n","").trim();
//          exp = exp.replace("\t","").trim();
//          exp = exp.replace(" ","").trim();
//          exp = exp.replace("'('","").trim();
//          exp = exp.replace("')'","").trim();
//          exp = exp.replace("\"(\"","").trim();
//          exp = exp.replace("\")\"","").trim();
          
          return exp;
          
      } else {
          
          return "ERROR - More then one line";
          
      }
      
      
  }
  
  public static int contCondition(String exp){
      //remover estas strings de strings na linha. fazer.
      String aux = exp.replace("_", "#");
      aux = aux.replace("'&'", "#");
      aux = aux.replace("&&", "_");
      aux = aux.replace("||", "_");
      aux = aux.replace("&", "_");
      aux = aux.replace("|", "_");
      aux = aux.replace("^", "_");
      String arrayAux[] = aux.split("_");
      
      return arrayAux.length;
      
  }
  
  public static int equalNumberCondition(String exp){
      //remover estas strings de strings na linha. fazer.
      String aux = exp.replace("_", "#");
      aux = aux.replace("'&'", "");
      aux = aux.replace("&&", "_");
      aux = aux.replace("||", "_");
      aux = aux.replace("&", "_");
      aux = aux.replace("|", "_");
      aux = aux.replace("^", "_");
      aux = aux.replace("(", "");
      aux = aux.replace(")", "");
//      aux = aux.replace("!", "");
      String arrayAux[] = aux.split("_");
      
      for (int i = 0; i < arrayAux.length; i++) {
          String string = arrayAux[i];
          for (int j = 0; j < arrayAux.length; j++) {
              String string2 = arrayAux[j];
              if(i!=j && string.equals(string2)){
                  ListMetric.repetition ++;
                  return 1;
              }
          }
          
      }
      
      return 0;
      
  }
  
  public static void recorMark(int val){
      
      int aux;
     
      if(Metric_v3.mapCon.containsKey(String.valueOf(val))) {
          
          aux = Integer.parseInt(Metric_v3.mapCon.get(String.valueOf(val)));
          aux ++;
          
          Metric_v3.mapCon.remove(String.valueOf(val));
          Metric_v3.mapCon.put(String.valueOf(val), String.valueOf(aux));
          
      } else {
          
          Metric_v3.mapCon.put(String.valueOf(val), String.valueOf(1));
          
      }
      
      
  }
  
   public static  Map<String,String> mapCon = new  HashMap<>();
   
   public static void printHash(){
       
       File file = new File("logCont");
       
       
       Set<String> setKey = Metric_v3.mapCon.keySet();
       
       Double num = 0.0;
       Double numOpA = 0.0;
       Double numOpB = 0.0;
       DecimalFormat df = new DecimalFormat("0.00"); 
       
           try {
               
           try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
               for (String string : setKey) {
                   
                   numOpA = (double)Integer.parseInt(Metric_v3.mapCon.get(string));
                   numOpB = (double)ListMetric.statementLoopBranch;
                   num = ((numOpA/numOpB) * 100.0);
                   bw.write("Number of Conditions " + string + " =  $ " + Metric_v3.mapCon.get(string) + "  $ " + df.format(num) + "%"  + "\n");
                   
               }
               bw.write("Number of Decisions with Condition repetition $ " + ListMetric.repetition + "\n");
               
               numOpA = (double)(ListMetric.statementLoopBranch + ListMetric.statementLoopBranchError);
               numOpB = (double) ListMetric.statement;
               num = (double)((numOpA/numOpB)*100);
               bw.write("Total of Statement $ " + ListMetric.statement + "\n");
               bw.write("Total of Decisions $ " + ListMetric.statementLoopBranch + " $ " + df.format(num) + "%"  +"\n");
               bw.write("Total of Decisions not checked $ " + ListMetric.statementLoopBranchError + "\n");
           }
               
           } catch (IOException ex) {
               Logger.getLogger(Metric_v3.class.getName()).log(Level.SEVERE, null, ex);
           }
           
      
       
   }

  
}

class MyErrorHandler extends DefaultErrorHandler {
    
    public void modelUpdated(EventObject event) { isUpdating = false; }
    
}
