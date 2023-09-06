package by.touchme.authservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * The news object contains main details about News.
 */
@Data
@AllArgsConstructor
public class User {

    /**
     * Unique identifier of the user.
     */
    private Long id;

    /**
     * Username of the user.
     */
    private String username;

    /**
     * Password of the user.
     */
    private String password;

    /**
     * Role of the user.
     */
    private Set<Role> roles;

    public boolean confirmPassword(String password) {
        return this.password.equals(password);
    }
}
