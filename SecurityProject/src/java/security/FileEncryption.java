/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;



/**
 *
 * @author federicoponcedeleon
 */
public class FileEncryption {
 
    public static byte[] encrypt(String key, byte[] plainText) throws Exception {
      Key secretKey = new SecretKeySpec(key.getBytes(), "AES");
      IvParameterSpec vectObj = new IvParameterSpec("92AE31A79FEEB2A3".getBytes("UTF-8"));
      //92AE31A79FEEB2A3
      Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
      
      aes.init(Cipher.ENCRYPT_MODE, secretKey, vectObj);
      byte[] encrypted = aes.doFinal(plainText);
      
      return encrypted;
    }
    
    public static byte[] decrypt(String key, byte[] encryptedText) throws Exception {
      Key sectretKey = new SecretKeySpec(key.getBytes(), "AES");
      IvParameterSpec vectObj = new IvParameterSpec("92AE31A79FEEB2A3".getBytes("UTF-8"));
      Cipher aes = Cipher.getInstance("AES/CBC/PKCS5Padding");
      
      aes.init(Cipher.DECRYPT_MODE, sectretKey, vectObj);
      
      byte[] decrypted = aes.doFinal(encryptedText);
      
      return decrypted;
    }
    
    public static void fullEncryption(String fileName, String fileNameDestination, String key) throws Exception {
        File file = new File(fileName);
        byte[] byteFile = FileEncryption.readFileToByteArray(file);
        
        byte[] encryptedFile = FileEncryption.encrypt(key, byteFile);
        
        FileEncryption.writeFile(fileNameDestination, encryptedFile);
        
    }
    
    public static void fullDecryption(String fileName, String fileNameDestination, String key) throws Exception {
        File file = new File(fileName);
        byte[] byteFile = FileEncryption.readFileToByteArray(file);
        
        byte[] decryptedFile = FileEncryption.decrypt(key, byteFile);
        
        FileEncryption.writeFile(fileNameDestination, decryptedFile);
    }
    
    /**
     * This method uses java.io.FileInputStream to read
     * file content into a byte array
     * @param file
     * @return
     */
    public static byte[] readFileToByteArray(File file){
        FileInputStream fis = null;
        // Creating a byte array using the length of the file
        // file.length returns long which is cast to int
        byte[] bArray = new byte[(int) file.length()];
        try{
            fis = new FileInputStream(file);
            fis.read(bArray);
            fis.close();        
            
        }catch(IOException ioExp){
            ioExp.printStackTrace();
        }
        return bArray;
    }
    
    public static void writeFile(String fileName, byte[] fileContent) throws FileNotFoundException, IOException {
        
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(fileContent);
            //fos.close(); There is no more need for this line since you had created the instance of "fos" inside the try. And this will automatically close the OutputStream
         }
	}
}
