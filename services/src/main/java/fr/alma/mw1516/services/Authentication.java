package fr.alma.mw1516.services;

import fr.alma.mw1516.model.User;
import fr.alma.mw1516.persistance.UserRepository;
import fr.alma.mw1516.services.exception.IMEIInvalidFormatException;
import fr.alma.mw1516.services.exception.IMEINotFoundException;
import fr.alma.mw1516.services.exception.TokenInvalidFormatException;
import fr.alma.mw1516.services.exception.UserNotFoundException;

import java.util.UUID;

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

    public User getUser(String token) throws UserNotFoundException, TokenInvalidFormatException {
        if (!checkToken(token))
            throw new TokenInvalidFormatException();

        User u = UserRepository.getInstance().getUser(token);
        if (u == null) {
            throw new UserNotFoundException();
        }
        return u;
    }
    
    private boolean checkIMEI(Long imei)
    {
        return imei.toString().length() == 15;
    }
    
    private boolean checkToken(String token)
    {
        return token.length() == UUID.randomUUID().toString().length();
    }
    
    public String getToken(Long imei) throws IMEINotFoundException, IMEIInvalidFormatException {

        if (!checkIMEI(imei))
            throw new IMEIInvalidFormatException();

    	String token = UUID.randomUUID().toString();
    	//Remember to save the token in the database!!!
        if (token.equals("")) {
            throw new IMEINotFoundException();
        }
    	return token;
    }
}
