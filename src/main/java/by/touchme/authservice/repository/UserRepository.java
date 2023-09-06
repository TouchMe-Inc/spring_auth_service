package by.touchme.authservice.repository;

import by.touchme.authservice.entity.Role;
import by.touchme.authservice.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class UserRepository {

    List<User> userList;

    UserRepository() {
        this.userList = new ArrayList<>();
        this.userList.add(new User(1L, "user", "12345678", Set.of(Role.ROLE_USER)));
        this.userList.add(new User(2L, "journalist", "12345678", Set.of(Role.ROLE_JOURNALIST, Role.ROLE_USER)));
        this.userList.add(new User(3L, "admin", "12345678", Set.of(Role.ROLE_ADMIN, Role.ROLE_JOURNALIST, Role.ROLE_USER)));
    }

    public Optional<User> findByUsername(String username) {
        return userList.stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }
}
