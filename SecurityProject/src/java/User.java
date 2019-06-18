
import static java.awt.SystemColor.text;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.servlet.http.HttpSession;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author federicoponcedeleon
 */
public class User {
    private String name;
    private String hashedPassword;
    private HttpSession sessionId; 
    
    public User(String name, String pass) {
        this.name = name;
        this.hashedPassword = User.hashPassword(pass);
        this.sessionId = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public HttpSession getSessionId() {
        return sessionId;
    }

    public void setSessionId(HttpSession sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isAutenticated() {
        return this.sessionId != null;
    }   
    
    public static String hashPassword(String toHash) {
        if (toHash.equals("")) return "";
        
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(toHash.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException ex) {
            return "";
        }
    }
}
