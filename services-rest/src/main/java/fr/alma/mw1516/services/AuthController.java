package fr.alma.mw1516.services;

import fr.alma.mw1516.model.Token;
import fr.alma.mw1516.model.User;
import fr.alma.mw1516.services.exception.*;

import org.springframework.web.bind.annotation.*;

/**
 * Token based authentication server used with coffee machines
 *
 * @author Adrien Garandel, Nicolas Brondin
 */

@RestController
public class AuthController {

    final String apiKey = "reQSFGgFSbgc54uyhjg35hgf23vJhg432JNkjH";

    /**
     * Generate token based on IMEI
     *
     * @param apiKey Authorization key to access the API
     * @param imei International Mobile Equipment Identity from NFC chip
     * @return Generated token
     *
     * @throws UnauthorizedException Api key missing or invalid / Token  expired
     * @throws NotFoundException IMEI not found
     * @throws UnprocessableEntityException Malformed IMEI, must be 15 digits long
     */
    @RequestMapping(value = "auth", method = RequestMethod.POST)
    @ResponseBody
    public Token auth(@RequestHeader(value = "Api-Key", required = true) String apiKey, @RequestParam(value = "imei", required = true) Long imei) {
        if (!this.apiKey.equals(apiKey)) {
        	UnauthorizedException ex = new UnauthorizedException();
        	Log.getInstance().sendAuthenticationLog(imei, null, ex.getMessage());
            throw ex;
        }

        Authentication authService = Authentication.getInstance();

        try {
        	Token token = authService.getToken(imei);
        	try {
				Log.getInstance().sendAuthenticationLog(imei, authService.getUser(token.getToken()), "");
			} catch (UserNotFoundException | TokenInvalidFormatException | TokenExpiredException e) {
				e.printStackTrace();
			}
            return token;
            

        } catch (IMEINotFoundException e) {
        	NotFoundException ex = new NotFoundException("Unregistered IMEI, can't authenticate, please contact the registration administrator");
            Log.getInstance().sendAuthenticationLog(imei, null, ex.getMessage());
        	throw ex;
        } catch (IMEIInvalidFormatException e) {
        	UnprocessableEntityException ex = new UnprocessableEntityException("Malformed IMEI, must be 15 digits long");
            Log.getInstance().sendAuthenticationLog(imei, null, ex.getMessage());
            throw ex;
        }

    }

    /**
     * Get user information according to token
     *
     * @param apiKey Authorization key to access the API
     * @param token Authentication token
     * @return User information according to token
     *
     * @throws UnauthorizedException Api key missing or invalid / Token  expired
     * @throws NotFoundException No user found from this token
     * @throws UnprocessableEntityException Malformed token
     */
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
            throw new NotFoundException("No user found from this token");
        } catch (TokenInvalidFormatException e) {
            throw new UnprocessableEntityException("Malformed token");
        } catch (TokenExpiredException e) {
            throw new UnauthorizedException("Token expired");
        }

    }

}
