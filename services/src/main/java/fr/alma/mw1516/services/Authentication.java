package fr.alma.mw1516.services;

import java.util.UUID;

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
    
    public boolean checkIMEI(Long imei)
    {
    	if(imei.toString().length()==15)
    		return true;
    	return false;
    }
    
    public boolean checkToken(String token)
    {
    	if(token.length()==UUID.randomUUID().toString().length())
    		return true;
    	return false;
    }
    
    public String getToken(Long imei)
    {
    	String token = UUID.randomUUID().toString();
    	//Remember to save the token in the database!!!
    	return token;
    }
}
