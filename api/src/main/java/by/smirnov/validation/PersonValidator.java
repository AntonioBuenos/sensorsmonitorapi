package by.smirnov.validation;

import by.smirnov.domain.User;
import by.smirnov.security.UserDetailsSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static by.smirnov.security.SecurityConstants.LOGIN;
import static by.smirnov.security.SecurityConstants.VALIDATION_ERROR_MESSAGE;

@RequiredArgsConstructor
@Component
public class PersonValidator implements Validator {

    private final UserDetailsSecurityService service;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        try {
            service.loadUserByUsername(user.getLogin());
        } catch (UsernameNotFoundException ignored){
            return;
        }

        errors.rejectValue(LOGIN, "", VALIDATION_ERROR_MESSAGE);
    }

}
