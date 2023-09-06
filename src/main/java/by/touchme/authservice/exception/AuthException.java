package by.touchme.authservice.exception;

/**
 * The class {@code NewsNotFoundException} and its subclasses
 * are a form of {@code RuntimeException} that indicates conditions
 * that a reasonable application might want to catch.
 */
public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
}
