package fr.alma.mw1516.persistance;

import fr.alma.mw1516.model.User;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.io.File;
import java.util.concurrent.ConcurrentNavigableMap;

/**
 * Created on 12/3/15.
 *
 * @author Adrien Garandel, Nicolas Brondin
 */
public class UserRepository {

    private static final String USER_DB = "User";
    private static final String TOKEN_USER_DB = "Token";
    private static final String IMEI_USER_DB = "IMEIUser";
    private static final String IMEI_TOKEN_DB = "IMEIToken";

    private static UserRepository instance;
    private final DB db;

    private UserRepository() {
        db = DBMaker.fileDB(new File("bloffee.db"))
                .closeOnJvmShutdown()
                .encryptionEnable("password")
                .make();
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();

        }
        return instance;
    }

    public String findTokenByIMEI(String IMEI) {
        if (IMEI == null)
            return null;

        ConcurrentNavigableMap<String, String> IMEI2Token = db.treeMap(IMEI_TOKEN_DB);
        return IMEI2Token.get(IMEI);
    }

    public User findUserById(String userId) {
        if (userId == null)
            return null;

        ConcurrentNavigableMap<String, User> userDb = db.treeMap(USER_DB);
        return userDb.get(userId);
    }

    public User findUserByToken(String token) {
        if (token == null)
            return null;

        ConcurrentNavigableMap<String, String> tokenDb = db.treeMap(TOKEN_USER_DB);
        return findUserById(tokenDb.get(token));
    }

    public User findUserByIMEI(String IMEI) {
        if (IMEI == null)
            return null;

        ConcurrentNavigableMap<String, String> IMEIDb = db.treeMap(IMEI_USER_DB);
        return findUserById(IMEIDb.get(IMEI));
    }

    public void createToken(String token, String imei, User user) {
        ConcurrentNavigableMap<String, String> tokenDb = db.treeMap(TOKEN_USER_DB);
        tokenDb.put(token, user.getId());

        ConcurrentNavigableMap<String, String> IMEI2Token = db.treeMap(IMEI_TOKEN_DB);
        IMEI2Token.put(imei, token);

        db.commit();
    }
}
