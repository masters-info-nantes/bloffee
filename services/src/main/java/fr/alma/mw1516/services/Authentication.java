package fr.alma.mw1516.services;

import fr.alma.mw1516.model.User;
import fr.alma.mw1516.persistance.UserRepository;

/**
 * Created on 12/3/15.
 *
 * @author dralagen
 */
public class Authentication {


    private static Authentication instance;

    public static Authentication getInstance() {
        if (instance == null) {
            instance = new Authentication();
        }

        return instance;
    }

    public User getUser(String token) {
        return UserRepository.getInstance().getUser(token);
    }
}
