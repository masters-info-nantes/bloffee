package fr.alma.mw1516.services;

import fr.alma.mw1516.services.exception.NotFoundException;
import fr.alma.mw1516.services.exception.UnauthorizedException;
import fr.alma.mw1516.services.exception.UnprocessableEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created on 12/4/15.
 *
 * @author Adrien Garandel, Nicolas Brondin
 */
@ControllerAdvice
public class ExceptionHandlingController {

    private class Error {
        private int error;
        private String message;

        public Error() {
        }

        public Error(int error, String message) {
            this.error = error;
            this.message = message;
        }

        public int getError() {
            return error;
        }

        public void setError(int error) {
            this.error = error;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public @ResponseBody Error handleErrorNotFound(Exception e) {

        return new Error(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public @ResponseBody Error handleErrorUnauthorized(Exception e) {

        return new Error(HttpStatus.UNAUTHORIZED.value(), "Bad Api-Key value");
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UnprocessableEntityException.class)
    public @ResponseBody Error handleErrorUnprocessableEntity(Exception e) {

        return new Error(HttpStatus.UNPROCESSABLE_ENTITY.value(), e.getMessage());
    }
}
