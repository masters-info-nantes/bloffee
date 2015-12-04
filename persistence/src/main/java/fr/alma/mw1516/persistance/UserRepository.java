package fr.alma.mw1516.persistance;

import fr.alma.mw1516.model.Token;
import fr.alma.mw1516.model.User;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.io.File;
import java.util.Date;
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
    private DB db;

    private UserRepository() {
    }

    private DB openDB() {
        if (db == null || db.isClosed()) {
            db = DBMaker.fileDB(new File("bloffee.db"))
                    .closeOnJvmShutdown()
                    .encryptionEnable("password")
                    .make();
        }
        return db;
    }

    private void closeDB() {
        if (!db.isClosed()) {
            db.close();
        }
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public Token findTokenByIMEI(String IMEI) {
        if (IMEI == null)
            return null;

        openDB();

        ConcurrentNavigableMap<String, String> IMEI2Token = db.treeMap(IMEI_TOKEN_DB);
        String tokenId = IMEI2Token.get(IMEI);

        TokenDB tokenDB = getTokenDB(tokenId);

        Token token = new Token(tokenId, tokenDB.getExpireDate());

        closeDB();
        return token;
    }

    public User findUserById(String userId) {
        if (userId == null)
            return null;

        openDB();

        ConcurrentNavigableMap<String, User> userDb = db.treeMap(USER_DB);
        User user = userDb.get(userId);
        closeDB();
        return user;
    }

    public User findUserByToken(String token) {
        if (token == null)
            return null;

        TokenDB tokenDB = getTokenDB(token);

        return findUserById(tokenDB.getUserId());
    }

    private TokenDB getTokenDB(String token) {
        openDB();

        ConcurrentNavigableMap<String, TokenDB> tokenDb = db.treeMap(TOKEN_USER_DB);
        TokenDB tokenDB = tokenDb.get(token);

        closeDB();

        return tokenDB;
    }

    public User findUserByIMEI(String IMEI) {
        if (IMEI == null)
            return null;

        openDB();

        ConcurrentNavigableMap<String, String> IMEIDb = db.treeMap(IMEI_USER_DB);
        User userById = findUserById(IMEIDb.get(IMEI));

        closeDB();
        return userById;
    }

    public void createToken(String token, String imei, User user) {
        openDB();

        Date expireTime = new Date();
        expireTime.setTime(expireTime.getTime() + 10*60*1000);

        TokenDB value = new TokenDB(user.getId(), expireTime);

        ConcurrentNavigableMap<String, TokenDB> tokenDb = db.treeMap(TOKEN_USER_DB);
        tokenDb.put(token, value);

        ConcurrentNavigableMap<String, String> IMEI2Token = db.treeMap(IMEI_TOKEN_DB);
        IMEI2Token.put(imei, token);

        db.commit();
        closeDB();
    }

    public Token findToken(String tokenId) {
        TokenDB tokenDB = getTokenDB(tokenId);
        if (tokenDB == null)
            return null;

        return new Token(tokenId, tokenDB.getExpireDate());
    }
}
