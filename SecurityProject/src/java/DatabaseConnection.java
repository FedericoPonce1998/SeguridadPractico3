import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author federicoponcedeleon
 */

public class DatabaseConnection {
    private final MongoClient mongoClient;
    private final DB mongoDataBase;
    
    public DatabaseConnection(String name) throws UnknownHostException {
        mongoClient = new MongoClient("localhost:27017");
        this.mongoDataBase = this.mongoClient.getDB("SecurityProject3");
    }
    
    public boolean registerUser(String username, String password) {
        if (username.equals("") || password.equals("") || existsUser(username)) return false;
        
        User newUser = new User(username, password);
        DBCollection col = this.mongoDataBase.getCollection("Users");
        Map<String, String> k = new HashMap<>();
        k.put("username", newUser.getName());
        DBObject exists = col.findOne(k);
        if (exists != null) {
            return false;
        }
        
        k.put("password", newUser.getHashedPassword());
        
        DBObject toInsert = new BasicDBObject(k);
        WriteResult result = col.insert(toInsert);
        
        return (Double) result.getLastError().get("ok") > 0.0;
    }
    
    private boolean existsUser(String username) {        
        DBCollection col = this.mongoDataBase.getCollection("Users");
        BasicDBObject query = new BasicDBObject();
        query.put("username", username);
        
        DBObject founded = col.findOne(query);
        return founded != null;
    }
    
    public boolean login(String username, String password) {
        if (username.equals("") || password.equals("")) return false;
        
        User newUser = new User(username, password);
        DBCollection col = this.mongoDataBase.getCollection("Users");
        BasicDBObject query = new BasicDBObject();
        User user = new User(username, password);
        query.put("username", user.getName());
        query.put("password", user.getHashedPassword());
        
        DBObject founded = col.findOne(query);
        
        return founded != null;
    }
}
