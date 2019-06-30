/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.security.KeyPair;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author ceibal
 */
@WebServlet(urlPatterns = {"/firmaCifradoDescifrado"})
public class firmaCifradoDescifrado extends HttpServlet {

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
            out.println("<title>Servlet firmaCifradoDescifrado</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet firmaCifradoDescifrado at " + request.getContextPath() + "</h1>");
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
            
            //Parametros 
            String textoClave = request.getParameter("textoClave");
            
            response.setContentType("text/html;charset=UTF-8"); 
            
            boolean showErrorMessage = false;
            boolean verif = false;
            String firma = "";
            if (textoClave.equals("")) {
                showErrorMessage = true;
            }
            else {
                EncryptFirma encrypt = new EncryptFirma();
            
                if(!encrypt.existenClaves()){
                    //Si no existe creo las claves en /tmp
                    KeyPair key = encrypt.generateKey();
                    encrypt.guardoClaves(key);
                }
            
                //Cargo las claves (publica y privada)
                KeyPair key = encrypt.CargoClave();

                //Codificacion Base64Binary de clave privada
                final String priv = DatatypeConverter.printBase64Binary( key.getPrivate().getEncoded() ); 

                //Realizo la firma para cifrar
                firma = encrypt.firmar(textoClave, priv);

                //Codificacion Base64Binary de clave publica
                final String pub = DatatypeConverter.printBase64Binary( key.getPublic().getEncoded() );

                //Verifica la firma
                verif = encrypt.verificarFirma( textoClave, firma, pub );
            }
            //Instancio EncryptFirma
            
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MyServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            if (showErrorMessage) {
                out.println("<h1>Por favor, ingrese una firma valida</h1>");
            }
            else {
                out.println("<h1>Datos obtenidos</h1>");
                out.println("<h4>Texto de origen: " + textoClave + "</h4>");
                out.println("<h4>Firma: " + firma + "</h4>");            
                if(verif == true){
                    out.println("<h4>Verifica?: SI </h4>");
                }else{
                    out.println("<h4>Verifica?: NO </h4>");
                }
            }
            
            out.println("<h3><a href=\"firmaCifradoDescifrado.html\">Volver</a></h3>");
            out.println("</body>");
            out.println("</html>");
            
        }catch(Exception ex){
            response.sendRedirect("exception-error.html");   
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
