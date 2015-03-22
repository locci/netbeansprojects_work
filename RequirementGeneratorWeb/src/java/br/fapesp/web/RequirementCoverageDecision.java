/*Programmer: Alexander L. Martins 
 *date: 08/2014
 *client: FAPESP
 *coach: Ana C. V. Melo
 *description: RequirementCoverageDecision class is used to generate the test requirements for
 *the following criteria: decision.
 *library: jdom(generate XML file).
 */

package br.fapesp.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

public class RequirementCoverageDecision {
	
	public static void	generateRequirementCoverageDecision(String pathFile){
		
		ArrayList<String> requirementList = new ArrayList<String>();
		RequirementCoverageDecision rc = new RequirementCoverageDecision();
		File file = new File(pathFile);
		int id = 1;
		int contLine = 0;
		String requirement = "";
		String line = "";
		String exprLine = "";
		
		try {
			
			BufferedReader bufferReader = new BufferedReader(new FileReader(file));			
			
			while(bufferReader.ready()){
				
				line = bufferReader.readLine();
				contLine++;
				/*
				 * IMPROVE: I need to think about how to handle situations in which the 'if' is not followed by whitespace and an "if" of a line.
				 */
				if(IfWhileFor.isIfWhileFor(line)) {
					requirement = String.valueOf(id) + ";" ;
					id++;
					if(line.contains("if")){
						requirement = requirement + "if" + ";" ;
						exprLine = rc.getExpression(line);
					} else {
						if(line.contains("while")){
							requirement = requirement + "while" + ";" ;
							exprLine = rc.getExpression(line);
						} else {
							requirement = requirement + "for" + ";" ;
							exprLine = rc.getExpressionFor(line);
						}						
					}
					requirement = requirement + exprLine + ";" ;
					requirement = requirement + String.valueOf(contLine);
					requirementList.add(requirement);
					requirement = "";
				}
				
			}
			
			bufferReader.close();
			
		} catch (FileNotFoundException e) {
			
			System.out.println("Output Error: " + e.getMessage());
			
		} catch (IOException e) {
		
			System.out.println("Output Error: " + e.getMessage());
			
		}		
		
		RequirementCoverageDecision.buildXML(pathFile, requirementList);
		
	}
	
	/*
	 * @author Alexandre L. Martins
	 * @version 1
	 * @since 08/15/2014
	 * @throws no one
	 * @param String, ArrayList<String>
	 * @return void	 * 
	 * description: generate an XML containing the test requirements for a given java source file.
	 * Model XML:
	 * <?xml version="1.0" encoding="UTF-8"?>
     *  <file pathName=" ">
     *      <expression id=" " type=" " line=" ">
     *          <expressionString> </expressionString>
     *          <valueTrue>true</valueTrue>
     *          <valueFalse>false</valueFalse>
     *      </expression>
     *  </file>  
	 */	
	private static void buildXML(String pathFileName, ArrayList<String> requirementList){
		
		
		String str = "";
		String strArray[];
		//sets the path and name of file as attribute.
		Element file = new Element("file");
		file.setAttribute("pathName", pathFileName);
				
		for (int i = 0; i < requirementList.size(); i++) {			
			
			Element expression = new Element("expression");
			Element expressionString = new Element("expressionString");
			Element valueTrue = new Element("valueTrue");
			Element valueFalse = new Element("valueFalse");
			
			str = requirementList.get(i);
			strArray = str.split(";");
			expression = new Element("expression");
			//sets the id expression 1, 2, 3...n.
			expression.setAttribute("id", strArray[0]);
			//sets the expression type: if, while or for.
			expression.setAttribute("type", strArray[1]);
			//sets the expression string: ((x > 10) || (y < 20)).
			expressionString.setText(strArray[2]);
			//sets the expression line in file: 1, 2, 3, ..., n.
			expression.setAttribute("line", strArray[3]);
			//sets the expect value for this expression.
			valueTrue.setText("true");
			valueFalse.setText("false");			
			//record the values
			expression.addContent(expressionString);
			expression.addContent(valueTrue);
			expression.addContent(valueFalse);		
			file.addContent(expression);
			
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
			  File fileDoc = new File(userHome + "/.decision/" + outputFileName + ".xml");
			  FileWriter fileOutput = new FileWriter(fileDoc);			  
			  xout.output(myDocument, fileOutput);

		} catch (IOException e) {

		      System.out.println("Output Error, see stack " + e.getMessage());

		}
		
	}
	
	/*
	 * @author Alexandre L. Martins
	 * @version 1
	 * @since 08/15/2014
	 * @throws no one
	 * @param String
	 * @return String
	 * description: identifies the boolean expression in the string (A||B) for while and if.
	 */		
	private String getExpression (String strExpression){
		
		int inic = strExpression.indexOf('(');
		int end = strExpression.lastIndexOf(')') + 1;
		
		strExpression = strExpression.substring(inic, end);		
		return strExpression;
		
	}
	
	/*
	 * @author Alexandre L. Martins
	 * @version 1
	 * @since 08/15/2014
	 * @throws no one
	 * @param String
	 * @return String
	 * description: identifies the boolean expression in the string for loop.
	 */	
    private String getExpressionFor (String strExpression){
		
		int inic = strExpression.indexOf('(');
		int end = strExpression.lastIndexOf(')') + 1;
		
		strExpression = strExpression.substring(inic, end);		
		
		if(strExpression.contains(";")){
			String arrayStr[] = strExpression.split(";");
			return "(" + arrayStr[1] + ")";
		}
		return strExpression;
		
	}

}
