package fr.alma.mw1516.persistance;

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
public class CreateDb {

    private static final String USER_DB = "User";
    private static final String TOKEN_USER_DB = "Token";
    private static final String IMEI_USER_DB = "IMEIUser";
    private static final String IMEI_TOKEN_DB = "IMEIToken";

    public static void main(String[] args) {

        DB db = DBMaker.fileDB(new File("bloffee.db"))
                .closeOnJvmShutdown()
                .encryptionEnable("password")
                .make();

        ConcurrentNavigableMap<String, User> user = db.treeMap(USER_DB);

        User user1 = new User("jsmith", "John", "Smith");
        User user2 = new User("msmith", "Micheline", "Smith");

        user.put(user1.getId(), user1);
        user.put(user2.getId(), user2);
        db.commit();

        ConcurrentNavigableMap<String, String> IMEI2User = db.treeMap(IMEI_USER_DB);

        IMEI2User.put("358621493054809", "msmith");
        IMEI2User.put("444786932124868", "jsmith");

        ConcurrentNavigableMap<String, TokenDB> token = db.treeMap(TOKEN_USER_DB);
        ConcurrentNavigableMap<String, String> IMEI2token = db.treeMap(IMEI_TOKEN_DB);

        Date expireTime = new Date();
        expireTime.setTime(expireTime.getTime() + 10*60*1000);
        System.out.println(expireTime.toString());

        token.put("65462557-6f92-46d8-9060-1ac1de36fcb5", new TokenDB("msmith", expireTime));
        IMEI2token.put("358621493054809", "65462557-6f92-46d8-9060-1ac1de36fcb5");
        token.put("ca8b354a-2993-487c-9efc-dce0704c3ab4", new TokenDB("jsmith", expireTime));
        IMEI2token.put("444786932124868", "ca8b354a-2993-487c-9efc-dce0704c3ab4");

        db.commit();
        db.close();
    }
}
