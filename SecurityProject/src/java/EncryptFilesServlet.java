 
 
import java.io.FileInputStream; 
import java.io.FileNotFoundException; 
import java.io.IOException; 
import java.io.PrintWriter; 
import java.nio.file.Files; 
import java.nio.file.Path; 
import java.nio.file.Paths; 
import java.security.InvalidKeyException; 
import java.security.Key; 
import static java.util.Arrays.stream; 
import java.util.Base64; 
import javax.crypto.Cipher; 
import javax.security.auth.kerberos.KerberosKey; 
import javax.servlet.ServletContext; 
import javax.servlet.ServletException; 
import javax.servlet.http.Cookie; 
import javax.servlet.http.HttpServlet; 
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 
import javax.servlet.http.HttpSession; 
import security.CBCEncryption; 
import security.ManejadorArchivosGenerico; 
import sun.misc.BASE64Decoder; 
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
        String key = request.getParameter("key"); 
        String initVector = request.getParameter("initVector"); 
        String fileNameDestination = request.getParameter("fileNameDestination"); 
        String decrypt = request.getParameter("decrypt"); 
         
        response.setContentType("text/html;charset=UTF-8"); 
        String output = ""; 
        if (decrypt != null) { 
            this.decryptFile(fileName, fileNameDestination); 
            output = "<h1>El archivo se ha desencriptado en " + fileNameDestination + "</h1>"; 
        } 
        else { 
            this.encryptFile(fileName, fileNameDestination, key, initVector); 
            output = "<h1>El archivo se ha encriptado en " + fileNameDestination + "</h1>"; 
        } 
             
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
            if (decrypt == null) { 
                 
            } 
            out.println(output); 
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
 
    private void encryptFile(String fileName, String fileNameDestination, String key, String initVector) throws FileNotFoundException, IOException { 
        FileInputStream myStream = new FileInputStream(fileName); 
        byte[] imageInBytes = Files.readAllBytes(Paths.get(fileName)); 
 
        //String cadenaOriginal = ""; 
        String resultadoEncriptado = ""; 
        //String resultadoDesEncriptado = ""; 
 
        String encryptedString = CBCEncryption.encryptByte(Base64.getEncoder().encode(imageInBytes), key, initVector); 
        resultadoEncriptado = encryptedString; 
 
        ManejadorArchivosGenerico.escribirArchivo(fileNameDestination, new String[] {resultadoEncriptado}); 
    } 
     
    private void decryptFile(String fileName, String fileNameDestination) throws FileNotFoundException, IOException, InvalidKeyException { 
        try (FileInputStream fileIn = new FileInputStream(fileName)) { 
            byte[] fileIv = new byte[16]; 
            fileIn.read(fileIv); 
            byte[] imageInBytes = Files.readAllBytes(Paths.get(fileName)); 
            String parsedBytes = Base64.getEncoder().encodeToString(fileIn.); 
            String decryptedString = CBCEncryption.decrypt(parsedBytes); 
            //String cadenaOriginal = ""; 
            ManejadorArchivosGenerico.escribirArchivo(fileNameDestination, new String[] {decryptedString}); 
        } catch (Exception ex) { 
 
        } 
    } 
     
    private void foo(String fileName) { 
 
        FileStream stream = new FileStream(fileName, FileMode.Open) 
        while (true) 
        { 
            byte[] convertedImage; 
            // All ints are 4-bytes 
            int size; 
            byte[] sizeBytes = new byte[4]; 
            // Read size 
            int numRead = stream.Read(sizeBytes, 0, 4); 
            if (numRead <= 0) { 
                break; 
            } 
            // Convert to int 
            size = BitConverter.ToInt32(sizeBytes, 0); 
            // Allocate the buffer 
            convertedImage = new byte[size]; 
            stream.Read(convertedImage, 0, size); 
            // Do what you will with the array 
            listOfArrays.Add(convertedImage); 
        } // end while 
} 
    } 
} 
