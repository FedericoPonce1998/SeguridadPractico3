/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        String originalString = "password";
        System.out.println("Original String to encrypt - " + originalString);
        String encryptedString = CBCEncryption.encrypt(originalString);
        System.out.println("Encrypted String - " + encryptedString);
        String decryptedString = CBCEncryption.decrypt(encryptedString);
        System.out.println("After decryption - " + decryptedString);
    }
    
}
