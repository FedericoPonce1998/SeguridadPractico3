package security;

/**
 *
 * @author federicoponcedeleon
 */
public class Security {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String [] lineasArchivo = ManejadorArchivosGenerico.leerArchivo("src/Security/Carta_Presentacion_Proyecto.txt");
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
                
        System.out.println("La cadena original es: " + cadenaOriginal);
        System.out.println("El resultado encriptado es: " + resultadoEncriptado);
        System.out.println("El resultado desencriptado es: " + resultadoDesEncriptado);
        
    }
}
