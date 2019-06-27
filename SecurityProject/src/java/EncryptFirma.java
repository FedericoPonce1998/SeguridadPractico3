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


/**
 *
 * @author Noe
 */
public class EncryptFirma {
    // String to hold name of the encryption algorithm.
    public static final String ALGORITHM = "RSA/ECB/PKCS1Padding";

    // String to hold the name of the private key file.
    public static final String PRIVATE_KEY_FILE = "/tmp/private.key";

    // String to hold name of the public key file.
    public static final String PUBLIC_KEY_FILE = "/tmp/public.key";

    public static final int KEY_SIZE = 1024;
    
       
}
