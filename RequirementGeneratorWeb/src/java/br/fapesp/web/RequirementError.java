

//pendeci: I need to check if is interesting to implement "Assignment Operators" page 19th.
package br.fapesp.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

import recoder.CrossReferenceServiceConfiguration;
import recoder.ParserException;
import recoder.convenience.TreeWalker;
import recoder.io.PropertyNames;
import recoder.io.SourceFileRepository;
import recoder.java.CompilationUnit;
import recoder.java.ProgramElement;
import recoder.java.declaration.ClassDeclaration;
import recoder.java.declaration.MethodDeclaration;
import recoder.java.expression.operator.CopyAssignment;


public class RequirementError {
	
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
	
	public ArrayList<String> buildArithmeticOperatorErrorList(String srcPath){
		
		ArrayList<String> listError = new ArrayList<String>();
		List<CompilationUnit> cul = serviceConfiguration(srcPath); 
		
		for (CompilationUnit cunit : cul) {
			
			String fileName = cunit.getName().replace("FILE:", "");
			String error = "";			
			//System.out.println(fileName);
			TreeWalker tw = new TreeWalker(cunit);
			while (tw.next()) {
				ProgramElement pe = tw.getProgramElement();				
				if (pe instanceof MethodDeclaration) {
					TreeWalker tw2 = new TreeWalker(pe);
					String line[];
					
					while (tw2.next()) {
						ProgramElement pe2 = tw2.getProgramElement();
						if (pe2 instanceof CopyAssignment) {
							CopyAssignment ca = (CopyAssignment)pe2;
							//String info = ca.getStartPosition().getLine()+ ": " + ca.toSource().trim();
							String info2 = ca.getEndPosition().getLine() + ": " + ca.toSource().trim();							
							line = ca.getEndPosition().toString().split("/");
							info2 = info2.replace(line[0] + ":","");
							error = error + "##" + line[0] +"," + info2 + RequirementError.incertErrorArithmeticOperatorReplacementDeletion(info2);
																
						}
					}
					if(!error.equals("")){
						listError.add(fileName + error);
					}
					
					
				}
				error = "";
			}		   
		}
		
		return listError;
		
	}	
	
	public ArrayList<String> buildRelationalOperatorsErrorList(String srcPath){
		
		ArrayList<String> listError = new ArrayList<String>();
		List<CompilationUnit> cul = serviceConfiguration(srcPath); 
		
		for (CompilationUnit cunit : cul) {
			
			String fileName = cunit.getName().replace("FILE:", "");
			String line;
			int contLine = 1;
			File file = new File(fileName);
			try {
				BufferedReader buff = new BufferedReader(new FileReader(file));
				String error = fileName;
				while(buff.ready()){
					line = buff.readLine();
					contLine++;
					if(line.contains("==")||line.contains("<=") || line.contains("=>") || line.contains("!=") || line.contains(" < ") || line.contains(" < ")){
						error = error + "##" + contLine +"," + line.trim() + RequirementError.incertErrorRelationalOperators(line.trim());
					}					
				}
				buff.close();
				System.out.println(error);
				listError.add(error);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 	
					   
		}		
		
		return listError;
	}
	
	
public ArrayList<String> buildShiftOperatorsErrorList(String srcPath){
		
		ArrayList<String> listError = new ArrayList<String>();
		List<CompilationUnit> cul = serviceConfiguration(srcPath); 
		
		for (CompilationUnit cunit : cul) {
			
			String fileName = cunit.getName().replace("FILE:", "");
			String line;
			int contLine = 1;
			File file = new File(fileName);
			try {
				BufferedReader buff = new BufferedReader(new FileReader(file));
				String error = fileName;
				while(buff.ready()){
					line = buff.readLine();
					contLine++;
					if(line.contains(">>")||line.contains("<<")){
						error = error + "##" + contLine +"," + line.trim() + RequirementError.incertErrorShiftOperators(line.trim());
					}					
				}
				buff.close();
				System.out.println(error);
				listError.add(error);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 	
					   
		}		
		
		return listError;
	}

public ArrayList<String> buildLogicalOperatorsErrorList(String srcPath){
	
	ArrayList<String> listError = new ArrayList<String>();
	List<CompilationUnit> cul = serviceConfiguration(srcPath); 
	
	for (CompilationUnit cunit : cul) {
		
		String fileName = cunit.getName().replace("FILE:", "");
		String line;
		int contLine = 1;
		File file = new File(fileName);
		try {
			BufferedReader buff = new BufferedReader(new FileReader(file));
			String error = fileName;
			while(buff.ready()){
				line = buff.readLine();
				contLine++;
				if(line.contains("||")||line.contains("&&")||line.contains(" & ") || line.contains(" | ")){
					error = error + "##" + contLine +"," + line.trim() + RequirementError.incertErrorLogicalOperators(line.trim());
				}					
			}
			buff.close();
			System.out.println(error);
			listError.add(error);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
				   
	}		
	
	return listError;
}
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@	
	//Implemt Insertion
	private static String incertErrorArithmeticOperatorReplacementDeletion(String str){
		
		String change = str;
		String out = "";
		if(str.contains(" + ")){
					
			out = out + "," + change.replace(" + ", " - ");
			out = out + "," + change.replace(" + ", " * ");			
			
		}
		
		if(str.contains(" * ")){
			
			out = out + "," + change.replace(" * ", " + ");		
			
		}
		
       if(str.contains(" * ")){
			
			out = out + "," + change.replace(" * ", " - ");		
			
		}
		
		if(str.contains(" - ")){
			
			out = out + "," + change.replace(" - ", " + ");			
			
		}
		
       if(str.contains("++")){
			
    	   out = out + "," + change.replace("++", "--");
			
			
	  }
       
      if(str.contains("--") ){
			
    	   out = out + "," + change.replace("--", "++");
			
			
	  }
      
      if(str.contains("++")){
			
   	   out = out + "," + change.replace("++", "");
			
			
	  }
      
     if(str.contains("--")){
			
   	   out = out + "," + change.replace("++", "");
			
			
	  }
     
     if(str.contains("/") ){
			
     	   out = out + "," + change.replace("/", "%");
  			
  			
  	  }
     
     if(str.contains("%")){
			
   	   out = out + "," + change.replace("%", "/");
			
			
	  }
     
     //Pendenci: I need to implement X++ to ++X
      /*
      if(str.contains("++")){
	   
	       for (int i = 0; i < changePlusPlus.length; i++) {
			  if(changePlusPlus[i].contains("++") && changePlusPlus[i].endsWith("++")){
				  changePlusPlus[i] = changePlusPlus[i].replace("++", "");
				  changePlusPlus[i] = "++" + changePlusPlus[i];
			  }
		   }  	   		
			
	  }
      
      if(str.contains("--")){
   	   
          for (int i = 0; i < changePlusPlus.length; i++) {
   		  if(changePlusPlus[i].contains("--") && changePlusPlus[i].endsWith("--")){
   			  changePlusPlus[i] = changePlusPlus[i].replace("--", "");
   			  changePlusPlus[i] = "--" + changePlusPlus[i];
   		  }
   	     }  	   		
   			
   	  }
      
      if(str.contains("++")){
   	   
	       for (int i = 0; i < changePlusPlus.length; i++) {
			  if(changePlusPlus[i].contains("++") && changePlusPlus[i].startsWith("++")){
				  changePlusPlus[i] = changePlusPlus[i].replace("++", "");
				  changePlusPlus[i] = changePlusPlus[i] + "++";
			  }
		   }  	   		
			
	  }
     
     if(str.contains("--")){
  	   
         for (int i = 0; i < changePlusPlus.length; i++) {
  		  if(changePlusPlus[i].contains("--") && changePlusPlus[i].startsWith("--")){
  			  changePlusPlus[i] = changePlusPlus[i].replace("--", "");
  			  changePlusPlus[i] = changePlusPlus[i] + "--";
  		  }
  	     }  	   		
  			
  	  }*/
		
		return out;
		
	}
	
private static String incertErrorRelationalOperators(String str){
		
		String change = str;
		String out = "";
		if(str.contains("<=")){
					
			out = out + "," + change.replace("<=", " > ");					
			
		}
		
		if(str.contains("=>")){
			
			out = out + "," + change.replace("=>", " <");		
			
		}
		
       if(str.contains("==")){
			
			out = out + "," + change.replace("==", "!=");		
			
		}
		
		if(str.contains(" > ")){
			
			out = out + "," + change.replace(" > ", " < ");			
			
		}
		
       if(str.contains(" < ")){
			
    	   out = out + "," + change.replace(" < ", " > ");
			
			
	  }
       
      if(str.contains("!=") ){
			
    	   out = out + "," + change.replace("!=", "==");
			
			
	  }  
		
		return out;
		
	}


private static String incertErrorShiftOperators(String str){
	
	String change = str;
	String out = "";
	if(str.contains("<<")){
				
		out = out + "," + change.replace("<<", ">>");					
		
	}
	
	if(str.contains("<<")){
		
		out = out + "," + change.replace("<<", "<<<<");		
		
	}
	
   if(str.contains(">>")){
		
		out = out + "," + change.replace(">>", "<<");		
		
	}
	
	if(str.contains(">>")){
		
		out = out + "," + change.replace(">>", ">>>>");			
		
	}   
	
	return out;
	
}

private static String incertErrorLogicalOperators(String str){
	
	String change = str;
	String out = "";
	if(str.contains("&&")){
				
		out = out + "," + change.replace("&&", "||");					
		
	}
	
	if(str.contains("||")){
		
		out = out + "," + change.replace("||", "&&");		
		
	}
	
   if(str.contains(" & ")){
		
		out = out + "," + change.replace(" & ", " | ");		
		
	}
	
	if(str.contains(" | ")){
		
		out = out + "," + change.replace(" | ", " & ");			
		
	}   
	
	return out;
	
}
//XML@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
private static void buildXMLArithmeticOperatorError(ArrayList<String> requirementList){
	
	for (int j = 0; j < requirementList.size(); j++) {
		Element file = new Element("file");
		//System.out.println(requirementList.get(j));	
		String str = requirementList.get(j);
		String infoLine[] = str.split("##");
		String pathFileName = infoLine[0];
		
		file.setAttribute("pathName", pathFileName);
		
		if(infoLine.length > 1){
			
			for (int w = 0; w < infoLine.length; w++) {				
				
				String normalMut[]   = infoLine[w].split(",");
				if(normalMut.length > 1){
					Element line = new Element("line");
					line.setText(normalMut[0]);					
					Element normalLine = new Element("NormalLine");
					normalLine.setAttribute("NormalLine",normalMut[1]);
					normalLine.setAttribute("Line",normalMut[0]);
					for (int i = 2; i < normalMut.length; i++) {
						
						Element xLine = new Element("Mutant");
						System.out.println(normalMut[i]);	
						xLine.setText(normalMut[i]);
						normalLine.addContent(xLine);
						
						
					}					
					file.addContent(normalLine);
					
				}					
					
				
				
			}	
			Document myDocument = new Document(file);
			XMLOutputter xout = new XMLOutputter();
			
			try {
				
				  xout.setFormat(org.jdom2.output.Format.getPrettyFormat());
				  // pattern: for each criterion a directory containing XML files created in the user's home.
				  String userHome = System.getProperty("user.home");
				  String outputFileName = pathFileName.replace('/', '_');
				  outputFileName = outputFileName.replace('.', '_');
				  int endIndex = outputFileName.length();
				  
				  outputFileName = outputFileName.substring(1, endIndex);
				  File fileDoc = new File(userHome + "/.xFile/" + outputFileName + "_ArithmeticOperator.xml");
				  FileWriter fileOutput = new FileWriter(fileDoc);			  
				  xout.output(myDocument, fileOutput);

			} catch (IOException e) {

			      System.out.println("Output Error, see stack " + e.getMessage());

			}
			
			
		}
	  }
		
	}
	
	private static void buildXMLRelationalOperatorError(ArrayList<String> requirementList){
		
		
		for (int j = 0; j < requirementList.size(); j++) {
			Element file = new Element("file");
			//System.out.println(requirementList.get(j));	
			String str = requirementList.get(j);
			String infoLine[] = str.split("##");
			String pathFileName = infoLine[0];
			
			file.setAttribute("pathName", pathFileName);
			
			if(infoLine.length > 1){
				
				for (int w = 0; w < infoLine.length; w++) {				
					
					String normalMut[]   = infoLine[w].split(",");
					if(normalMut.length > 1){
						Element line = new Element("line");
						line.setText(normalMut[0]);					
						Element normalLine = new Element("NormalLine");
						normalLine.setAttribute("NormalLine",normalMut[1]);
						normalLine.setAttribute("Line",normalMut[0]);
						for (int i = 2; i < normalMut.length; i++) {
							
							Element xLine = new Element("Mutant");
							xLine.setText(normalMut[i]);
							normalLine.addContent(xLine);
							
							
						}					
						file.addContent(normalLine);
						
					}					
						
					
					
				}	
				Document myDocument = new Document(file);
				XMLOutputter xout = new XMLOutputter();
				
				try {
					
					  xout.setFormat(org.jdom2.output.Format.getPrettyFormat());
					  // pattern: for each criterion a directory containing XML files created in the user's home.
					  String userHome = System.getProperty("user.home");
					  String outputFileName = pathFileName.replace('/', '_');
					  outputFileName = outputFileName.replace('.', '_');
					  int endIndex = outputFileName.length();
					  
					  outputFileName = outputFileName.substring(1, endIndex);
					  File fileDoc = new File(userHome + "/.xFile/" + outputFileName + "_RelationalOperator.xml");
					  FileWriter fileOutput = new FileWriter(fileDoc);			  
					  xout.output(myDocument, fileOutput);

				} catch (IOException e) {

				      System.out.println("Output Error, see stack " + e.getMessage());

				}
				
				
			}
			
		}
	
	
}
        private static void buildXMLLogicalOperatorError(ArrayList<String> requirementList){
		
		
		for (int j = 0; j < requirementList.size(); j++) {
			Element file = new Element("file");
			//System.out.println(requirementList.get(j));	
			String str = requirementList.get(j);
			String infoLine[] = str.split("##");
			String pathFileName = infoLine[0];
			
			file.setAttribute("pathName", pathFileName);
			
			if(infoLine.length > 1){
				
				for (int w = 0; w < infoLine.length; w++) {				
					
					String normalMut[]   = infoLine[w].split(",");
					if(normalMut.length > 1){
						Element line = new Element("line");
						line.setText(normalMut[0]);					
						Element normalLine = new Element("NormalLine");
						normalLine.setAttribute("NormalLine",normalMut[1]);
						normalLine.setAttribute("Line",normalMut[0]);
						for (int i = 2; i < normalMut.length; i++) {
							
							Element xLine = new Element("Mutant");
							xLine.setText(normalMut[i]);
							normalLine.addContent(xLine);
							
							
						}					
						file.addContent(normalLine);
						
					}					
						
					
					
				}	
				Document myDocument = new Document(file);
				XMLOutputter xout = new XMLOutputter();
				
				try {
					
					  xout.setFormat(org.jdom2.output.Format.getPrettyFormat());
					  // pattern: for each criterion a directory containing XML files created in the user's home.
					  String userHome = System.getProperty("user.home");
					  String outputFileName = pathFileName.replace('/', '_');
					  outputFileName = outputFileName.replace('.', '_');
					  int endIndex = outputFileName.length();
					  
					  outputFileName = outputFileName.substring(1, endIndex);
					  File fileDoc = new File(userHome + "/.xFile/" + outputFileName + "_LogicalOperator.xml");
					  FileWriter fileOutput = new FileWriter(fileDoc);			  
					  xout.output(myDocument, fileOutput);

				} catch (IOException e) {

				      System.out.println("Output Error, see stack " + e.getMessage());

				}
				
				
			}
			
		}
	
	
}

        private static void buildXMLShiftOperatorError(ArrayList<String> requirementList){
		
		
		for (int j = 0; j < requirementList.size(); j++) {
			Element file = new Element("file");
			//System.out.println(requirementList.get(j));	
			String str = requirementList.get(j);
			String infoLine[] = str.split("##");
			String pathFileName = infoLine[0];
			
			file.setAttribute("pathName", pathFileName);
			
			if(infoLine.length > 1){
				
				for (int w = 0; w < infoLine.length; w++) {				
					
					String normalMut[]   = infoLine[w].split(",");
					if(normalMut.length > 1){
						Element line = new Element("line");
						line.setText(normalMut[0]);					
						Element normalLine = new Element("NormalLine");
						normalLine.setAttribute("NormalLine",normalMut[1]);
						normalLine.setAttribute("Line",normalMut[0]);
						for (int i = 2; i < normalMut.length; i++) {
							
							Element xLine = new Element("Mutant");
							xLine.setText(normalMut[i]);
							normalLine.addContent(xLine);
							
							
						}					
						file.addContent(normalLine);
						
					}					
						
					
					
				}	
				Document myDocument = new Document(file);
				XMLOutputter xout = new XMLOutputter();
				
				try {
					
					  xout.setFormat(org.jdom2.output.Format.getPrettyFormat());
					  // pattern: for each criterion a directory containing XML files created in the user's home.
					  String userHome = System.getProperty("user.home");
					  String outputFileName = pathFileName.replace('/', '_');
					  outputFileName = outputFileName.replace('.', '_');
					  int endIndex = outputFileName.length();
					  
					  outputFileName = outputFileName.substring(1, endIndex);
					  File fileDoc = new File(userHome + "/.xFile/" + outputFileName + "_ShiftOperator.xml");
					  FileWriter fileOutput = new FileWriter(fileDoc);			  
					  xout.output(myDocument, fileOutput);

				} catch (IOException e) {

				      System.out.println("Output Error, see stack " + e.getMessage());

				}
				
				
			}
			
		}
	
	
}
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


	public static void mainBuild(String str){
		
		RequirementError re = new RequirementError();
		
		RequirementError.buildXMLArithmeticOperatorError(re.buildArithmeticOperatorErrorList(str));
		RequirementError.buildXMLRelationalOperatorError(re.buildRelationalOperatorsErrorList(str));
		RequirementError.buildXMLShiftOperatorError(re.buildShiftOperatorsErrorList(str));
		RequirementError.buildXMLLogicalOperatorError(re.buildLogicalOperatorsErrorList(str));
		
	}
        
}
