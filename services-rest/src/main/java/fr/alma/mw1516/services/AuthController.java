package fr.alma.mw1516.services;

import fr.alma.mw1516.model.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Adrien Garandel, Nicolas Brondin
 */

@RestController
public class AuthController {
    
	final String apiKey = "reQSFGgFSbgc54uyhjg35hgf23vJhg432JNkjH";
	
    @RequestMapping(value="auth", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> auth(@RequestHeader(value = "Api-Key", required = true) String apiKey, @RequestParam(value = "imei", required = true) Long imei) {
    	Authentication authService = Authentication.getInstance();
    	if(this.apiKey.equals(apiKey)) {
    		if(authService.checkIMEI(imei)) {
    			String token = authService.getToken(imei);
    			if( token != null) {
    				return new ResponseEntity<String>(token, HttpStatus.OK);
    			}
    			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    		}
    		return new ResponseEntity<String>(HttpStatus.UNPROCESSABLE_ENTITY);
    	}
    	return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
    }
    
    @RequestMapping(value="token", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<User> token(@RequestHeader(value = "Api-Key", required = true) String apiKey, @RequestHeader(value = "Authorization", required = true) String token) {  	
    	Authentication authService = Authentication.getInstance();
    	if(this.apiKey.equals(apiKey)) {
    		if(authService.checkToken(token)) {
    			User user = authService.getUser(token);
    			if( token != null) {
    				return new ResponseEntity<User>(user, HttpStatus.OK);
    			}
    			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
    		}
    		return new ResponseEntity<User>(HttpStatus.UNPROCESSABLE_ENTITY);
    	}
    	return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
    }

}
