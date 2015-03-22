/*Programmer: Alexander L. Martins 
 *date: 08/2014
 *client: FAPESP
 *coach: Ana C. V. Melo
 *description: RequirementCoverageDecision class is used to generate the test requirements for
 *the following criteria: decision.
 *library: jdom(generate XML file).
 */
package br.fapesp.web;

public class IfWhileFor {	
	/*
	 * @author Alexandre L. Martins
	 * @version 1
	 * @since 08/15/2014
	 * @throws no one
	 * @param String
	 * @return boolean
	 * description: identifies if the line contains if, while or for.
	 */	
	public static boolean isIfWhileFor(String line){		
		
		if(line.contains("if") || line.contains("while") || line.contains("for")){
			return true;
		} else {
			return false;
		}
		
	}

}
