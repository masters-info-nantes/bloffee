package fr.alma.mw1516.persistance;

import fr.alma.mw1516.model.User;

/**
 * Created on 12/3/15.
 *
 * @author dralagen
 */
public class UserRepository {

    private static UserRepository instance;

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public User getUser(String userId) {
        return new User(userId, "John", "Smith");
    }
}
