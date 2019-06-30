/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Key;

import javax.crypto.Cipher;
import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import static java.util.Objects.hash;


/**
 *
 * @author Noe
 */

public class EncryptFirma {
    // Algoritmo a utlizar
    public static final String ALGORITHM = "RSA/ECB/PKCS1Padding";

    // ruta de clave privada
    public static final String PRIVATE_KEY_FILE = "/tmp/private.key";

    // ruta de clave publica 
    public static final String PUBLIC_KEY_FILE = "/tmp/public.key";
    
    //largo de la clave
    public static final int KEY_SIZE = 1024;
    
    /**
    * Generador de clave privada y publica, RSA
    * De largo dado por KEY_SIZE
    */
    public static KeyPair generateKey() throws NoSuchAlgorithmException {
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(KEY_SIZE);
            return keyGen.generateKeyPair();
    }
    
    /**
    * Metodo para guardar las claves creadas
    *
    */
    public static void guardoClaves(KeyPair key) throws IOException,FileNotFoundException {
            File privateKeyFile = new File(PRIVATE_KEY_FILE);
            File publicKeyFile = new File(PUBLIC_KEY_FILE);

            // Creo los archivos de las claves
            privateKeyFile.createNewFile();
            publicKeyFile.createNewFile();

            // Guardo la clave publica
            ObjectOutputStream publicKeyOS = new ObjectOutputStream(new FileOutputStream(publicKeyFile));
            publicKeyOS.writeObject(key.getPublic());
            publicKeyOS.close();

            // Guardo la clave privada
            ObjectOutputStream privateKeyOS = new ObjectOutputStream(new FileOutputStream(privateKeyFile));
            privateKeyOS.writeObject(key.getPrivate());
            privateKeyOS.close();
    }

    /**
    * Cargo las claves a utilizar
    *
    * @return KeyPair
    */
    public static KeyPair CargoClave() throws IOException,FileNotFoundException,ClassNotFoundException {

            ObjectInputStream inputStream;

            // Cargo clave publica
            inputStream = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
            PublicKey pubKey = (PublicKey) inputStream.readObject();
            inputStream.close();

            // Cargo clave privada
            inputStream = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
            PrivateKey privKey = (PrivateKey) inputStream.readObject();
            inputStream.close();

            return new KeyPair(pubKey, privKey);
    }
    
    /**
    * Metodo que se fija si existe la clave publica y privada
    *
    * @return retorna si existe o no 
    */
    public static boolean existenClaves() {

            File privateKey = new File(PRIVATE_KEY_FILE);
            File publicKey = new File(PUBLIC_KEY_FILE);

            return (privateKey.exists() && publicKey.exists());
    }
    
    public static byte[] encrypt(String text, Key key) {
            byte[] cipherText = null;
            try {
                    final Cipher cipher = Cipher.getInstance(ALGORITHM);
                    cipher.init(Cipher.ENCRYPT_MODE, key);
                    cipherText = cipher.doFinal(text.getBytes());
            } catch (Exception e) {
                    e.printStackTrace();
            }
            return cipherText;
    }
    
    public static String decrypt(byte[] text, Key key) {
            byte[] dectyptedText = null;
            try {
                    final Cipher cipher = Cipher.getInstance(ALGORITHM);

                    cipher.init(Cipher.DECRYPT_MODE, key);
                    dectyptedText = cipher.doFinal(text);

            } catch (Exception ex) {
                    ex.printStackTrace();
            }

            return new String(dectyptedText);
    }  
    
    public static String hash(String texto) throws NoSuchAlgorithmException {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        byte[] bytes = texto.getBytes();
        byte[] hash = sha256.digest(bytes);

        return DatatypeConverter.printBase64Binary( hash );
    }
    
    public static String firmar(String texto,String clave_base64) throws NoSuchAlgorithmException,InvalidKeySpecException {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(DatatypeConverter.parseBase64Binary(clave_base64)));

		return DatatypeConverter.printBase64Binary( encrypt( hash(texto), privateKey ) );
    }    
    
    public static boolean verificarFirma(String texto,String encriptado_base64,String clave_base64) throws NoSuchAlgorithmException,InvalidKeySpecException {
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(DatatypeConverter.parseBase64Binary(clave_base64)));

		return hash(texto).equals( decrypt( DatatypeConverter.parseBase64Binary(encriptado_base64), publicKey) );
	}
}
