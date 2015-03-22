/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.fapesp.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alexandre
 */
@WebServlet(name = "LoadMCDC", urlPatterns = {"/LoadMCDC"})
public class LoadMCDC extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Files Information</title>");            
                out.println("</head>");
                out.println("<body bgcolor=\"#E6E6FA\">"); 
                out.println("<table border=\"0\" align=\"center\" width=1000>");
                out.println("<thead>");
                out.println("<tr bgcolor=\"#1E90FF\">");
                out.println("<th>");
                out.println("<font size=\"15\" color=\"white\" face = \"verdana\">Requeriment Tool - MC/DC</font>");                
                out.println("</th>");
                out.println("</th>");
                out.println("</thead>");
                out.println("<tbody>");
                out.println("<tr>");
                out.println("<td></td>");
                out.println("</tr>");
                out.println("</tbody>");
                out.println("</table>"); 
                
                out.println("<br>");
                out.println("<br>");
                                
                out.println("<table border=\"0\">");
                out.println("<thead>");
                out.println("<tr>");
                out.println("<th>");                
                out.println("</th>");
                out.println("</th>");
                out.println("</thead>");
                out.println("<tbody>");
                out.println("<tr  bgcolor=\"#1E90FF\">");
                out.println("<td>");
                
                out.println("</td>");
                out.println("</tr>");
                out.println("</tbody>");
                out.println("</table>"); 
                out.println("<br>"); 
                           RequerimentMCDC_V_4.main(null);
                out.println("<table border=\"0\">");
                out.println("<thead>");
                out.println("<tr>");
                out.println("<th>");                
                out.println("</th>");
                out.println("</th>");
                out.println("</thead>");
                out.println("<tbody>");
                out.println("<tr  bgcolor=\"#1E90FF\">");
                out.println("<td>");
                out.println("<font size=\"4\" color=\"white\" face = \"verdana\">Download requieriment file: <a href = \"MCDCServlet\">MC/DC File</a><br> </font>");                    
                out.println("</td>");
                out.println("</tr>");
                out.println("</tbody>");
                out.println("</table>");                    
                                
                out.println("<br>");
                out.println("<table border=\"0\">");
                out.println("<thead>");
                out.println("<tr>");
                out.println("<th>");                
                out.println("</th>");
                out.println("</th>");
                out.println("</thead>");
                out.println("<tbody>");
                out.println("<tr  bgcolor=\"#1E90FF\">");
                out.println("<td>");                
                out.println("</td>");
                out.println("</tr>");
                out.println("</tbody>");
                out.println("</table>");                
                out.println("</body>");
                out.println("</html>");            
            
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
