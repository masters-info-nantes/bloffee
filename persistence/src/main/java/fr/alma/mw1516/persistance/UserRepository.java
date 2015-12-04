package fr.alma.mw1516.persistance;

import fr.alma.mw1516.model.User;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.io.File;
import java.util.concurrent.ConcurrentNavigableMap;

/**
 * Created on 12/3/15.
 *
 * @author dralagen
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
        ConcurrentNavigableMap<String, String> IMEI2Token = db.treeMap(IMEI_TOKEN_DB);
        return IMEI2Token.get(IMEI);
    }

    public User findUserById(String userId) {
        ConcurrentNavigableMap<String, User> userDb = db.treeMap(USER_DB);
        return userDb.get(userId);
    }

    public User findUserByToken(String token) {
        ConcurrentNavigableMap<String, String> tokenDb = db.treeMap(TOKEN_USER_DB);
        return findUserById(tokenDb.get(token));
    }

    public User findUserByIMEI(String IMEI) {
        ConcurrentNavigableMap<String, String> IMEIDb = db.treeMap(IMEI_USER_DB);
        return findUserById(IMEIDb.get(IMEI));
    }
}
