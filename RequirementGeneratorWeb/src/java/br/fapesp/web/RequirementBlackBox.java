/*Programmer: Alexander L. Martins 
 *date: 08/2014
 *client: FAPESP
 *coach: Ana C. V. Melo
 *description: RequirementCoverageDecision class is used to generate the test requirements for
 *the following criteria: decision.
 *library: jdom(generate XML file), recoder.
 */

package br.fapesp.web;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

import recoder.CrossReferenceServiceConfiguration;
import recoder.ParserException;
import recoder.abstraction.Method;
import recoder.abstraction.Type;
import recoder.convenience.TreeWalker;
import recoder.io.PropertyNames;
import recoder.io.SourceFileRepository;
import recoder.java.CompilationUnit;
import recoder.java.ProgramElement;
import recoder.java.declaration.ClassDeclaration;
import recoder.java.declaration.VariableSpecification;

public class RequirementBlackBox {
	
	/*
	 * @author Alexandre L. Martins
	 * @version 1
	 * @since 08/15/2014
	 * @throws no one
	 * @param String, ArrayList<String>
	 * @return void	 * 
	 * description: set the source files for recoder. * 
	 * */	
	public static List<CompilationUnit> serviceConfiguration (String srcPath) {
		//create a service configuration
		CrossReferenceServiceConfiguration crsc = new CrossReferenceServiceConfiguration();
		
		//set the path to source code ("src" folder). 
		//multiple source code paths, as well as paths to libraries, can be separated via ":" or ";".		
		crsc.getProjectSettings().setProperty(PropertyNames.INPUT_PATH, srcPath);
		crsc.getProjectSettings().ensureSystemClassesAreInPath();
		
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
	
	/*
	 * @author Alexandre L. Martins
	 * @version 1
	 * @since 08/20/2014
	 * @throws no one
	 * @param String, String
	 * @return void	 * 
	 * description: generates black box requirement. * 
	 * */	
	public static void generateRequirementBlackBox(String pathFile) {
		
		//setting src path
        List<CompilationUnit> cul = serviceConfiguration(pathFile);
        ArrayList<String> outList = new ArrayList<String>();
		String fileName   = "";
		String className  = "";
		String methodInfo = "";
		//traversing the abstract syntax trees of the parsed .java files
		
		for (CompilationUnit cunit : cul) {
			
			fileName = cunit.getName().replace("FILE:", "");
			methodInfo = methodInfo + fileName;
            TreeWalker tw = new TreeWalker(cunit);
            
			while (tw.next()) {
				
				ProgramElement pe = tw.getProgramElement();                                
				if (pe instanceof ClassDeclaration) {
                                      
					ClassDeclaration cls = (ClassDeclaration)pe;
										
					className = cls.getFullName();
					methodInfo = methodInfo + "," + className; 
					
					if(cls.isPublic()){
						methodInfo = methodInfo + "," + "public";
					} else {
						if(cls.isPrivate()){
							methodInfo = methodInfo + "," + "private";
						} else {
							if(cls.isProtected()){
								methodInfo = methodInfo + "," + "protected";
							} else {
								methodInfo = methodInfo + "," + "default";
							}
						}						
					}
					
					if(cls.isInterface()){
						
						methodInfo = methodInfo + "," + "yes";
						
					} else {
						
						methodInfo = methodInfo + "," + "no";
						
						if(cls.isAbstract()){
							
							methodInfo = methodInfo + "," + "yes";
							
						} else {
							
							methodInfo = methodInfo + "," + "no";
							
						}			
						
						if(cls.isFinal()){
							
							methodInfo = methodInfo + "," + "yesl";
							
						} else {
							
							methodInfo = methodInfo + "," + "no";
							
						}
						
						List<Method> methods = cls.getMethods();
						
	                    List<? extends VariableSpecification> var = cls.getVariablesInScope();
	                                    
						for (Method method : methods) {	
							
							List<Type>  typeArray = method.getSignature();
							
							methodInfo = methodInfo + "&" + method.getName();						
							//access modifiers
							if(method.isPublic()){
								methodInfo = methodInfo + "," + "public";
							} else {
								if(method.isPrivate()){
									methodInfo = methodInfo + "," + "private";
								} else {
									if(method.isProtected()){
										methodInfo = methodInfo + "," + "protected";
									} else {
										methodInfo = methodInfo + "," + "default";
									}
								}
							}
							
							//no access modifiers
							if(method.isFinal()){
								methodInfo = methodInfo + "," + "yes";
							} else {
								methodInfo = methodInfo + "," + "no";
							}
							if(method.isAbstract()){
								methodInfo = methodInfo + "," + "yes";
							} else {
								methodInfo = methodInfo + "," + "no";	
							}
							if(method.isStrictFp()){
								methodInfo = methodInfo + "," + "yes";
							} else {
								methodInfo = methodInfo + "," + "no";
							}
							if(method.isNative()){
								methodInfo = methodInfo + "," + "yes";
							} else {
								methodInfo = methodInfo + "," + "no";
							}
							if(method.isSynchronized()){
								methodInfo = methodInfo + "," + "yes";
							} else {
								methodInfo = methodInfo + "," + "no";
							}
							if(method.isVarArgMethod()){
								methodInfo = methodInfo + "," + "yes";
							} else {
								methodInfo = methodInfo + "," + "no";
							}
							if(method.isStatic()){
								methodInfo = methodInfo + "," + "yes";
							} else {
								methodInfo = methodInfo + "," + "no";
							}
							
							//return
							if(method.getReturnType() != null){
								methodInfo = methodInfo + "," + method.getReturnType().toString() + ",";
							} else {
								methodInfo = methodInfo + "," + "void" + ",";
							}                   
	                       
							//parameters
	                    	if(typeArray.isEmpty()){
	                    	   methodInfo = methodInfo + "void";
	                    	} else {
	                    	   for (int i = 0; i < typeArray.size(); i++) {
	                    	      if(i + 1 < typeArray.size()){
	                    	    	  methodInfo = methodInfo + typeArray.get(i).toString() + "_";
	                    	      } else {
	                    	    	  methodInfo = methodInfo + typeArray.get(i).toString();
	                    	      }       		  
	                    		}
	                    	}
	                       
						}
						methodInfo = methodInfo + "$";
						System.out.println(methodInfo);
						outList.add(methodInfo);
						methodInfo = "";
					}					
				}				
			}		   
		}
		//build XML
		RequirementBlackBox.buildXML(outList);
	}
	
	/*
	 * @author Alexandre L. Martins
	 * @version 1
	 * @since 08/20/2014
	 * @throws no one
	 * @param String, ArrayList<String>
	 * @return void	 * 
	 * description: generate an XML containing the test requirements for a given java source file.
	 * Model XML:
	 * <?xml version="1.0" encoding="UTF-8"?>
        <file pathName="/home/alexandre/Dropbox/workspace_projeto/triangle/src/triangle/Main.java">
          <class className="triangle.Main" abstract="no" final="no">
            <method name="main" access="public" final="no" abstract="no" isStrictFp="no" native="no" Synchronized="no" var-args="no" static="static">
              <parameter>ArrayType java.lang.String[]$</parameter>
              <return>void</return>
           </method>
         </class>
        </file>
	 */		
	private static void buildXML(ArrayList<String> requirementList){
		
		String pathFileName = "";
		String strFile[];
		String strFileReturnParaInfo[];
		String strFileInfo[];
		Element file = new Element("file");	
						
		for (int i = 0; i < requirementList.size(); i++) {			
			
			strFile = requirementList.get(i).split("$");
			
			for (int j = 0; j < strFile.length; j++) {
				
				strFileReturnParaInfo = strFile[j].split("&");
				strFileInfo = strFileReturnParaInfo[0].split(",");
				if(strFileInfo[2].equals("interface")){
					
					pathFileName = strFileInfo[0];
					
				} else {
					pathFileName = strFileInfo[0];
					Element classes = new Element("class");
					file = new Element("file");
					file.setAttribute("pathName", strFileInfo[0]);				
					classes.setAttribute("className", strFileInfo[1]);
					classes.setAttribute("access", strFileInfo[2]);
					classes.setAttribute("abstract", strFileInfo[4]);
					classes.setAttribute("final", strFileInfo[5]);
					for (int j2 = 1; j2 < strFileReturnParaInfo.length; j2++) {
						String strMethod[] = strFileReturnParaInfo[j2].split(",");
						Element methodes = new Element("method");
						methodes.setAttribute("name", strMethod[0]);
						methodes.setAttribute("id", String.valueOf(j2));
						methodes.setAttribute("access", strMethod[1]);
						methodes.setAttribute("final", strMethod[2]);
						methodes.setAttribute("abstract", strMethod[3]);
						methodes.setAttribute("isStrictFp", strMethod[4]);
						methodes.setAttribute("native", strMethod[5]);
						methodes.setAttribute("Synchronized", strMethod[6]);
						methodes.setAttribute("var-args", strMethod[7]);
						methodes.setAttribute("static", strMethod[8]);						
						String para[] = strMethod[10].split("_");
						for (int k = 0; k < para.length; k++) {
							Element parameter = new Element("parameter");
							parameter.setAttribute("id", String.valueOf(k+1));
							parameter.setText(para[k]);
							methodes.addContent(parameter);
						}
						Element methoedReturn = new Element("return");
						methoedReturn.setText(strMethod[9]);
						methodes.addContent(methoedReturn);
						classes.addContent(methodes);
						
					}
					file.addContent(classes);
					Document myDocument = new Document(file);
					XMLOutputter xout = new XMLOutputter();
					
					try {
						  xout.setFormat(org.jdom2.output.Format.getPrettyFormat());
						  String userHome = System.getProperty("user.home");
						  String outputFileName = pathFileName.replace('/', '_');
						  outputFileName = outputFileName.replace('.', '_');
						  int endIndex = outputFileName.length();						  
						  outputFileName = outputFileName.substring(1, endIndex);
						  pathFileName = pathFileName.replace("/", "_");
						  int tam = pathFileName.length();
						  pathFileName = pathFileName.substring(1, tam);
                                                  File fileDoc = new File(userHome + "/.blackBox/" + pathFileName + ".xml");
						  FileWriter fileOutput = new FileWriter(fileDoc);			  
						  xout.output(myDocument, fileOutput);
                                                  fileOutput.close();
                                                  
					} catch (IOException e) {

					      System.out.println("Output Error, see stack " + e.getMessage());

					}
				}
				
			}		
		}			
	}

}
