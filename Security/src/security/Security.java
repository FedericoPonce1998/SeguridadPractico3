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
        //String originalString = "ManyaCampeon";
        
        String [] lineasArchivo = ManejadorArchivosGenerico.leerArchivo("src/Security/Prueba1.txt");
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
        
        
        
        //System.out.println("Cadena Original - " + originalString);
        //String encryptedString = CBCEncryption.encrypt(originalString);
        //System.out.println("Cadena Encriptada - " + encryptedString);
        //String decryptedString = CBCEncryption.decrypt(encryptedString);
        //System.out.println("Después de la desencriptación - " + decryptedString);
    }
}
