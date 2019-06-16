/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import security.CBCEncryption;
import security.ManejadorArchivosGenerico;

/**
 *
 * @author federicoponcedeleon
 */
public class FilesServlet extends HttpServlet {

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
            out.println("<title>Servlet FilesServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet FilesServlet at " + request.getContextPath() + "</h1>");
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
    
    private void getExceptionMessage(HttpServletResponse response, Exception ex) throws IOException {
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MyServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Error</h1>");
           // out.println("<h2>" + ex.getMessage() + "</h2>");
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
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fileName = request.getParameter("fileName");
        
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            String [] lineasArchivo = ManejadorArchivosGenerico.leerArchivo(fileName);

            String cadenaOriginal = "";
            String resultadoEncriptado = "";
            String resultadoDesEncriptado = "";

            if (lineasArchivo.length == 0) {
                throw new Exception("file not found");
            }
            
            for (int j = 0; j< lineasArchivo.length; j++){
                String[] lineas = lineasArchivo[j].split(" ");

                for (int i=0; i<lineas.length; i++){
                    String originalString = lineas[i];

                    cadenaOriginal += originalString + " ";
                    String encryptedString = CBCEncryption.encrypt(originalString);
                    resultadoEncriptado = resultadoEncriptado + encryptedString;

                    String decryptedString = CBCEncryption.decrypt(encryptedString);
                    resultadoDesEncriptado = resultadoDesEncriptado + decryptedString + " ";
                }

            }
        
            /*TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MyServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            //out.println("<h1>" + userName + "</h1>");
            out.println("<h1>" + "La cadena original es: " + cadenaOriginal + "</h1>");
            out.println("<h1>" + "El resultado encriptado es: " + resultadoEncriptado + "</h1>");
            out.println("<h1>" + "El resultado desencriptado es: " + resultadoDesEncriptado + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
        catch (Exception ex) {
            PrintWriter out = response.getWriter();
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
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
