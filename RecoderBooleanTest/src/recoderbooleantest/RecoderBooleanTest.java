/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package recoderbooleantest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.EventObject;
import java.util.List;
import javax.swing.JFileChooser;
import recoder.CrossReferenceServiceConfiguration;
import recoder.ParserException;
import recoder.abstraction.Variable;
import recoder.convenience.ForestWalker;
import recoder.io.PropertyNames;
import recoder.io.SourceFileRepository;
import recoder.java.CompilationUnit;
import recoder.java.Expression;
import recoder.java.ProgramElement;
import recoder.java.Statement;
import recoder.java.StatementBlock;
import recoder.java.TerminalProgramElement;
import recoder.java.declaration.ClassDeclaration;
import recoder.java.declaration.MethodDeclaration;
import recoder.java.declaration.VariableSpecification;
import recoder.java.expression.Literal;
import recoder.java.expression.operator.BinaryAnd;
import recoder.java.expression.operator.BinaryAndAssignment;
import recoder.java.expression.operator.BinaryOr;
import recoder.java.expression.operator.BinaryOrAssignment;
import recoder.java.statement.BranchStatement;
import recoder.java.statement.LoopStatement;
import recoder.kit.UnitKit;
import recoder.service.DefaultErrorHandler;

/**
 *
 * @author alexandre
 */
public class RecoderBooleanTest {

    /**
     * @param args the command line arguments
     */
    
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
			e.printStackTrace();
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
        boolean booleanStatementFind = false;
               
         while (fw.next()) {
        
            ProgramElement pe = fw.getProgramElement();
            
            if (pe instanceof LoopStatement || pe instanceof BranchStatement) {
                
                
                String isTryFor = pe.getClass().getSimpleName();
                
                if(!isTryFor.equals("Try")){
                    
                    booleanStatementFind = true;
                   

                }
                    
            } 
            
             if (booleanStatementFind && pe instanceof Expression && !(pe instanceof Statement) && !(pe instanceof Literal) && !(pe instanceof TerminalProgramElement)){
                 
                                 
                     Expression ba1 = (Expression)pe; 
                     String stR = ba1.toSource().replace("\n", "").trim();
                     stR = stR.replace(" ", "");
                     System.out.println("literal " + stR);
                     booleanStatementFind = false;
                
            }
            
            if(pe instanceof TerminalProgramElement ){
                
                TerminalProgramElement te = (TerminalProgramElement)pe;
                System.out.println("terminal " + te.toSource());
                
            }
            
            
            
            if (pe instanceof BinaryAnd){
                
                BinaryAnd ba = (BinaryAnd)pe; 
//                System.out.println("and " + ba.toSource());    
                //System.out.println("or " + ba.getExpressionCount());
                
            }
            
            if (pe instanceof BinaryOrAssignment){
                
//                BinaryOr ba = (BinaryOr)pe;                
//                System.out.println("or " + ba.getArguments());    
                
                
            } 
//            
//            if (pe instanceof BinaryOr){
//                
//                BinaryOr ba = (BinaryOr)pe;                
//                System.out.println("and " + ba.toSource());
//                
//                
//            }
//            
//            if (pe instanceof BinaryOrAssignment){
//                
//                BinaryOr ba = (BinaryOr)pe;  
//                System.out.println("BinaryOrAssignment");
//                
//                
//            }
            
//            if (pe instanceof BinaryAndAssignment){
//                
//                BinaryOr ba = (BinaryOr)pe;                
//                System.out.println("BinaryAndAssignment");
//                
//                
//            }
                   
            
        } 
         
         
                
    }

}  
        
class MyErrorHandler extends DefaultErrorHandler {
    
    @Override
    public void modelUpdated(EventObject event) { isUpdating = false; }
    
}
