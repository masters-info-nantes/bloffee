package fr.alma.mw1516.services;

import org.springframework.web.bind.annotation.*;

/**
 * @author Arnaud Thimel : thimel@codelutin.com
 */
@RestController
@RequestMapping("auth")
public class AuthController {
    

    @RequestMapping(value="auth", method = RequestMethod.POST)
    @ResponseBody
    public String auth(@RequestHeader(value = "Api-Key", required = true) String apiKey, @RequestParam(value = "imei", required = true) String imei) {

        return imei;
    }
    
    @RequestMapping(value="token", method = RequestMethod.GET)
    @ResponseBody
    public User token(@RequestHeader(value = "Api-Key", required = true) String apiKey, @RequestHeader(value = "Authorization", required = true) String token) {
    	User u = new User(token, "Adrien", "Garandel");
        return u;
    }

}
