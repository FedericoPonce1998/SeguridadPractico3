/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Noe
 */


import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import security.CBCEncryption;
import security.ManejadorArchivosGenerico;
import sun.misc.IOUtils;


public class EncryptFilesServlet extends HttpServlet {
    
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
            out.println("<title>Servlet EncryptFilesServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EncryptFilesServlet at " + request.getContextPath() + "</h1>");
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    private void getExceptionMessage(HttpServletResponse response, Exception ex) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MyServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Error</h1>");
            out.println("<h2>" + ex.getMessage() + "</h2>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
            
        
        try (PrintWriter out = response.getWriter()) {
            Cookie[] cookies = request.getCookies();
        ServletContext sc = getServletContext();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("JSESSIONID")) {
                    User current = (User) sc.getAttribute("currentUser");
                    if (current == null || current.getSessionId() == null || !current.getSessionId().getId().equals(cookie.getValue())) {
                        response.sendRedirect("unauthorized-error.html");
                        return;
                    }
                }
            }
        }
        
        String fileName = request.getParameter("fileName");
        
        response.setContentType("text/html;charset=UTF-8");
            FileInputStream myStream = new FileInputStream(fileName);
            byte[] imageInBytes = IOUtils.readFully(myStream, 0, true); 
       
            //String cadenaOriginal = "";
            String resultadoEncriptado = "";
            //String resultadoDesEncriptado = "";

            String encryptedString = CBCEncryption.encryptByte(imageInBytes);
            resultadoEncriptado = encryptedString;
            
            //String decryptedString = CBCEncryption.decrypt(encryptedString);
            //resultadoDesEncriptado = decryptedString;
           
        
            /*TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MyServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            //out.println("<h1>" + userName + "</h1>");
            //out.println("<h1>" + "La cadena original es: " + cadenaOriginal + "</h1>");
            out.println("<h1>" + "El resultado encriptado es: " + resultadoEncriptado + "</h1>");
            //out.println("<h1>" + "El resultado desencriptado es: " + resultadoDesEncriptado + "</h1>");
            out.println("<h3><a href=\"encryptfiles.html\">Volver</a></h3>");
            out.println("</body>");
            out.println("</html>");
        }
        catch (Exception ex) {
            response.sendRedirect("exception-error.html");    
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
