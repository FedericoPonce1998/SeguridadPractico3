package security;



import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Noe
 */
public class Security extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        getRequest(request, response);
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
        getData(request, response);
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
    
    
    private void getData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getParameterValues("userName")[0];
        
        response.setContentType("text/html;charset=UTF-8");
        
        String [] lineasArchivo = ManejadorArchivosGenerico.leerArchivo("C:\\Prueba1.txt");
        String cadenaOriginal = "";
        String resultadoEncriptado = "";
        String resultadoDesEncriptado = "";
                
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
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
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
    }

private void getRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MyServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>GET!</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
