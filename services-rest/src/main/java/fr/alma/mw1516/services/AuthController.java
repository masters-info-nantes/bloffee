package fr.alma.mw1516.services;

import fr.alma.mw1516.model.User;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Arnaud Thimel : thimel@codelutin.com
 */
@RestController
public class AuthController {
    

    @RequestMapping(value="auth", method = RequestMethod.POST)
    @ResponseBody
    public String auth(@RequestHeader(value = "Api-Key", required = true) String apiKey, @RequestParam(value = "imei", required = true) String imei) {

        return imei;
    }
    
    @RequestMapping(value="token", method = RequestMethod.GET)
    @ResponseBody
    public User token(@RequestHeader(value = "Api-Key", required = true) String apiKey, @RequestHeader(value = "Authorization", required = true) String token) {
    	return Authentication.getInstance().getUser(token);
    }

}
