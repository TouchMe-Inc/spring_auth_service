package by.touchme.authservice.handler;

import by.touchme.authservice.exception.AuthException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

/**
 * Controller that catches all thrown exceptions.
 */
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class, AuthException.class})
    public ResponseEntity<Object> handleNotValidException(Exception ex, WebRequest request) {
        return prepareErrorMessage(ex, request, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<Object> handleJwtException(Exception ex, WebRequest request) {
        return prepareErrorMessage(ex, request, HttpStatus.FORBIDDEN);
    }

    /**
     * Any unhandled exceptions.
     */
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest request) {
        return prepareErrorMessage(ex, request, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> prepareErrorMessage(Exception ex, WebRequest request, HttpStatus status) {
        String requestUri = ((ServletWebRequest) request).getRequest().getRequestURI();
        ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), requestUri);
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), status);
    }
}