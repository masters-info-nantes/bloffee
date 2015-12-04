package fr.alma.mw1516.services;

import fr.alma.mw1516.model.User;
import fr.alma.mw1516.services.exception.*;
import org.springframework.web.bind.annotation.*;

/**
 * @author Adrien Garandel, Nicolas Brondin
 */

@RestController
public class AuthController {

    final String apiKey = "reQSFGgFSbgc54uyhjg35hgf23vJhg432JNkjH";

    @RequestMapping(value = "auth", method = RequestMethod.POST)
    @ResponseBody
    public String auth(@RequestHeader(value = "Api-Key", required = true) String apiKey, @RequestParam(value = "imei", required = true) Long imei) {
        if (!this.apiKey.equals(apiKey)) {
            throw new UnauthorizedException();
        }

        Authentication authService = Authentication.getInstance();

        try {
            return authService.getToken(imei);

        } catch (IMEINotFoundException e) {
            throw new NotFoundException("IMEI not found");
        } catch (IMEIInvalidFormatException e) {
            throw new UnprocessableEntityException("IMEI invalid format");
        }

    }

    @RequestMapping(value = "token", method = RequestMethod.GET)
    @ResponseBody
    public User token(@RequestHeader(value = "Api-Key", required = true) String apiKey, @RequestHeader(value = "Authorization", required = true) String token) {
        if (!this.apiKey.equals(apiKey)) {
            throw new UnauthorizedException();
        }

        Authentication authService = Authentication.getInstance();

        try {
            return authService.getUser(token);
        } catch (UserNotFoundException e) {
            throw new NotFoundException("User not found");
        } catch (TokenInvalidFormatException e) {
            throw new UnprocessableEntityException("Token invalid format");
        }

    }

}
