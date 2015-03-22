/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package requerimentmcdc_v_4;

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
import tokenexpression.Token;

/**
 *
 * @author alexandre
 */
public class RequerimentMCDC_V_4 {
    
    //Set recoder server.
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
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
//        JFileChooser chooser = new JFileChooser();
//        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//        int ret = chooser.showOpenDialog(null);
//        String str = chooser.getSelectedFile().getAbsolutePath(); 
        String str = "/home/alexandre/NetBeansProjects/newtriangle";
        List<CompilationUnit> cus = serviceConfiguration(str);
        ForestWalker fw = new ForestWalker(cus);
        
        File file = new File("log");
        File fileErr = new File("logErr");
        File fileDo = new File("logDo");
        File fileXor = new File("logXor");
        boolean BranchLoop = false;
        boolean BranchLoopFor = false;
        tokenexpression.Token tk = new Token();
        
        outPutWFile = new BufferedWriter(new FileWriter(file));
        
        String pathFile = null;
            
            while (fw.next()) {
                
                ProgramElement pe = fw.getProgramElement();
            
                if (pe instanceof ClassDeclaration) {
                    
                    outPutWFile.write("*********" + "\n");
                    pathFile = UnitKit.getCompilationUnit(pe).getDataLocation().toString().replace("FILE:", "");
                    outPutWFile.write(UnitKit.getCompilationUnit(pe).getDataLocation().toString() + "\n");
                    ClassDeclaration cls = (ClassDeclaration)pe;
                    outPutWFile.write("\t Class " + cls.getName() + "\n");
                    
                }
                
                if (pe instanceof MethodDeclaration) {
                    
                    MethodDeclaration ca = (MethodDeclaration)pe;
                    int inic = ca.getStartPosition().getLine();
                    int end  = ca.getEndPosition().getLine();
                    outPutWFile.write("\t\t Method " + ca.getName() + "\n");
                    outPutWFile.write("\t\t Method scope  " + inic + " " + end + "\n");
                    
                    
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
                        
//                        bw.write("\t\t\t" + isTryFor +
//                                " start line "+ line +
//                                " end line "+ pe.getEndPosition().getLine() +
//                                " start column " + pe.getStartPosition().getColumn() + "\n");
                        outPutWFile.write("\t\t\t" + isTryFor +
                                " start line "+ line +
                                " end line "+ pe.getEndPosition().getLine() +
                                " start column " + pe.getStartPosition().getColumn() + "\n");
                      
                    } else {
                        
                        if(isTryFor.equals("Do")){
                            
//                            bDo.write(pathFile + "\n");                              
//                            bDo.write("\t\t\t" + isTryFor +
//                                " start line "+ line +
//                                " end line "+ pe.getEndPosition().getLine() +
//                                " start column " + pe.getStartPosition().getColumn() + "\n");                          
                            
                            
                        }
                        
                    }                 
                    
                }
                
                if(BranchLoop && pe instanceof Expression /*&& !(pe instanceof Statement) && !(pe instanceof Literal) && !(pe instanceof TerminalProgramElement)*/){
                        
                    BranchLoop = false;
                    Expression bl = (Expression)pe;
                    String expression = "("+ bl.toSource().replace("\n", "").trim()+")";
                    
                    if(BranchLoopFor){
                        
                        System.out.println(expression);
                        outPutWFile.write("\t\t\tExpression " + expression + "\n");
                        tk.tokenExpre(expression);
                        
                    }
                    
                }
                
            }
            

            outPutWFile.close();
            
        }
        
       
    public static BufferedWriter outPutWFile;
            
    
}

//Set top number of excptions.
class MyErrorHandler extends DefaultErrorHandler {
    
    @Override
    public void modelUpdated(EventObject event) { isUpdating = false; }
    
}
