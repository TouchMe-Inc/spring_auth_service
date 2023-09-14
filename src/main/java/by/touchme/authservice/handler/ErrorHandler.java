package by.touchme.authservice.handler;

import by.touchme.authservice.exception.AuthException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

    /**
     * Validation via @Valid annotation failed or Empty required body.
     *
     * @param ex MethodArgumentNotValidException or HttpMessageNotReadableException
     * @return Object with error message
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<Object> handleNotValidException(Exception ex, WebRequest request) {
        return prepareErrorMessage(ex, request, HttpStatus.BAD_REQUEST);
    }

    /**
     * Errors related directly to the authorization process.
     *
     * @param ex AuthException
     * @return Object with error message
     */
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<Object>  handleNotFoundException(Exception ex, WebRequest request) {
        return prepareErrorMessage(ex, request, HttpStatus.BAD_REQUEST);
    }

    /**
     * Unauthorized access.
     *
     * @param ex AccessDeniedException
     * @return Object with error message
     */
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