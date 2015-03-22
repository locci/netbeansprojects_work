/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.fapesp.web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import recoder.list.generic.ASTArrayList;
import recoder.service.DefaultErrorHandler;


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
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int ret = chooser.showOpenDialog(null);
        String str = chooser.getSelectedFile().getAbsolutePath(); 
        //String str = "/home/alexandre/NetBeansProjects/newtriangle";
        List<CompilationUnit> cus = serviceConfiguration(str);
        ForestWalker fw = new ForestWalker(cus);
        
        
        File file = new File(System.getProperty("user.home") + "/logOutPro");
        File fileErr = new File("logErr");
        File fileDo = new File("logDo");
        File fileXor = new File("logXor");
        boolean BranchLoop = false;
        boolean BranchLoopFor = false;
        Token tk = new Token();
//        TokenList.reqirementList = new ArrayList<String>();
        
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
                        
                        TokenList.requirementList.clear();
                        outPutWFile.write("\t\t\tExpression " + expression + "\n");
                        tk.tokenExpre(expression);
                        outPutWFile.write("\t\t\t\tMinimum Set " + " " + "\n");
                        System.out.println("Nova expre ");
                        String outMiniTrue = "";
                        String outMiniFalse = "";
                        ArrayList<String> vaulCheckedTrue = new ArrayList<>();
                        ArrayList<String> keyListOrder = new ArrayList<>();
                        Map<String,String> reqCases = new HashMap<String,String>();
                        String key;
                        String indexReq;
                        
                        for (int i = 0; i < TokenList.requirementList.size(); i++) {
                            
                            String get = TokenList.requirementList.get(i);
                            String[] arr = get.split(" ");
                            key   = arr[2] + " " + arr[3];
                            
                            if(!reqCases.containsKey(key)) {
                                
                                reqCases.put(key, String.valueOf(i) + " ");
                                keyListOrder.add(key);
                                
                            } else {
                                
                                indexReq = reqCases.get(key) +  i  + " ";
                                reqCases.put(key, indexReq);
                                
                            }
                            
                        }
                        
                        if(keyListOrder.size() == 1) {
                            
                            String[] req =  TokenList.requirementList.get(0).split(" ");
                            System.out.println(req[0] + " " + req[1]);
                            outPutWFile.write(req[0] + " " + req[1] + "\n");
                            
                        } else {
                        
                        String[] root = reqCases.get(keyListOrder.get(0)).split(" ");
                        ArrayList<String> flip = new ArrayList<String>();
                        ArrayList<String> flop = new ArrayList<String>();
                        String load;
                        
                        for (int i = 0; i < root.length; i++) {
                            
                            int rootNum = Integer.valueOf(root[i]);
                            
                            for (int j = 1; j < keyListOrder.size(); j++) {
                                
                                String getKey  = keyListOrder.get(j);
                                String[] value = reqCases.get(getKey).split(" ");
                                
                                if(flip.isEmpty() && flop.isEmpty()){
                                    
                                    String[] req =  TokenList.requirementList.get(rootNum).split(" ");
                                    load = req[0] + " " + req[1] + " ";
                                    
                                    for (int k = 0; k < value.length; k++) {
                                       
                                        int req1 = Integer.valueOf(value[k]);
                                        String[] valueList = TokenList.requirementList.get(req1).split(" ");
                                        String aux = "";
                                        
                                        if(!load.contains(valueList[0])){
                                            
                                            aux = aux + valueList[0] + " ";
                                            
                                        }
                                        
                                        if(!load.contains(valueList[1])){
                                            
                                            aux = aux + valueList[1] + " ";
                                            
                                        }
                                        
                                        flip.add(load + aux);
                                        
                                    }
                                    
                                } else {//flip não está vázia
                          
                                    
                                    for (int k = 0; k < flip.size(); k++) {
                                        String get = flip.get(k);
                                                                                                                        
                                        for (int l = 0; l < value.length; l++) {
                                            int value1 = Integer.valueOf(value[l]);
                                            String aux = get;
                                            
                                            String[] valueList = TokenList.requirementList.get(value1).split(" ");
                                            
                                            if(!aux.contains(valueList[0])){
                                                
                                                aux = aux + " " + valueList[0];
                                                
                                            }
                                            
                                            if(!aux.contains(valueList[1])){
                                                
                                                aux = aux + " " + valueList[1];
                                                
                                            }
                                            
                                            flop.add(aux);
                                            
                                        }
                                        
//                                        flip.clear();
//                                    
//                                        for (int u = 0; u < flop.size(); u++) {
//
//                                            String get2 = flop.get(u);
//                                            flip.add(get2);
//
//                                        }
//
//                                        flop.clear();  
                                        
                                    }
                                    
                                    flip.clear();
                                    
                                    for (int k = 0; k < flop.size(); k++) {
                                        
                                        String get = flop.get(k);
                                        flip.add(get);
                                        
                                    }
                                    
                                    flop.clear();                                    
                                    
                                }
                                
                                
                                
                            }
                            
                            for (int j = 0; j < flip.size(); j++) {
                                
                                String get = flip.get(j);
                                outPutWFile.write("{" + get + "}" + "\n");
                                
                            }
                            
                            outPutWFile.write("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + "\n");
                            
                            flip.clear();
                            
                        }
                        
                    }
                        
//minimum set  2
                        
//                        for (int i = 0; i < TokenList.requirementList.size(); i++) {
//                            
//                            String get = TokenList.requirementList.get(i);
//                            String [] strPos = get.split(" ");                            
//                            outMiniTrue = strPos[0] + " "; 
//                            outMiniFalse = strPos[1] + " ";
//                            
//                            if(!vaulCheckedTrue.contains(strPos[0])){
//                                
//                            for (int j = 0; j < TokenList.requirementList.size(); j++) {
//                                
//                                    String get1 = TokenList.requirementList.get(j);
//                                    String [] strPosVal = get1.split(" ");
//                                
//                                    if(strPos[0].equals(strPosVal[0]) || strPos[0].equals(strPosVal[1])){
//                                    
//                                        if(!strPosVal[3].equals("0")){
//                                
//                                           outMiniTrue = outMiniTrue + "* c" + strPosVal[2] + " r" + strPosVal[3] + "* " + " ";
//                                
//                                        } else {
//                                
//                                           outMiniTrue = outMiniTrue+ " " + "c" + strPosVal[2] + " ";
//                                
//                                        }
//                                   
//                                }
//                                
//                            }
//                            
//                            outPutWFile.write("\t\t\t\t " + outMiniTrue + "\n");
//                            
//                            }
//                            
//                            if(!vaulCheckedTrue.contains(strPos[1])){
//                                 
//                            for (int j = 0; j < TokenList.requirementList.size(); j++) {
//
//                                String get1 = TokenList.requirementList.get(j);
//                                String [] strPosVal = get1.split(" ");
//
//                                if(strPos[1].equals(strPosVal[0]) || strPos[1].equals(strPosVal[1])){
//
//                                    if(!strPosVal[3].equals("0")){
//
//                                        outMiniFalse = outMiniFalse + "* c" + strPosVal[2] + " r" + strPosVal[3] + " *" + " ";
//
//                                    } else {
//
//                                        outMiniFalse = outMiniFalse + " " + "c" + strPosVal[2] + " ";
//
//                                    }
//
//                                }
//
//                               
//                            }   
//                            
//                            
//                            outPutWFile.write("\t\t\t\t " + outMiniFalse + "\n");
//                            
//                            
//                            
//                            
//                         }                            
//                            
//                           vaulCheckedTrue.add(strPos[0]); 
//                           vaulCheckedTrue.add(strPos[1]); 
//                        
//                        }
                        
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
