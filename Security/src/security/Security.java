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
        String originalString = "ManyaCampeon";
        System.out.println("Cadena Original - " + originalString);
        String encryptedString = CBCEncryption.encrypt(originalString);
        System.out.println("Cadena Encriptada - " + encryptedString);
        String decryptedString = CBCEncryption.decrypt(encryptedString);
        System.out.println("Después de la desencriptación - " + decryptedString);
    }
}
