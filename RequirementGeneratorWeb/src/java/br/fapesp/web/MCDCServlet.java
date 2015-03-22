/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.fapesp.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream; 
import javax.servlet.ServletOutputStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;

/**
 *
 * @author alexandre
 */
@WebServlet(name = "MCDCServlet", urlPatterns = {"/MCDCServlet"})
public class MCDCServlet extends HttpServlet {

    private static final long serialVersionUID = 1L; 
         
    public MCDCServlet() { 
        super(); 
    } 
  
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
        doPost(request, response); 
    } 
  
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
        
        File fileOut = new File(System.getProperty("user.home") + "/logOutPro");
        BufferedReader br = new BufferedReader(new FileReader(fileOut));
        response.setContentType("application/txt"); 
        PrintWriter out = response.getWriter(); 
        
        while(br.ready()){
            out.println(br.readLine()); 
        }
        
        out.close(); 
        
//        InputStream is  = getClass().getClassLoader().getResourceAsStream(System.getProperty("user.home") + "/logOutPro");
//        ByteArrayOutputStream ba = new ByteArrayOutputStream();
//        
//        byte[] b =
//        
//        ServletOutputStream out = response.getOutputStream();
//        response.reset(); 
//        response.setHeader("Content-Disposition", "attachment;filename=MCDC.txt");
//        response.setContentType("text/txt");
//        out.write(b);     
//        out.flush();     
//        out.close();
        //response.setHeader ("Content-Disposition", "attachment; filename=" + "logOutPro");
    }   

}
