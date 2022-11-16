package by.smirnov.security;

import by.smirnov.domain.Role;
import by.smirnov.domain.User;
import by.smirnov.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@RequiredArgsConstructor
@Component
public class AuthChecker {

    private final UserService service;

    public boolean isAuthorized(String login, Long id){
        User authenticatedUser = service.findByLogin(login);
        Role authUserRole = authenticatedUser.getRole();
        if(authUserRole == Role.ROLE_ADMINISTRATOR){
            return true;
        }
        Long authenticatedId = authenticatedUser.getId();
        return Objects.equals(id, authenticatedId);
    }
}
