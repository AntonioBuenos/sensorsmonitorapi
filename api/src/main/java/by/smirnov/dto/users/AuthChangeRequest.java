package by.smirnov.dto.users;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static by.smirnov.validation.ValidationConstants.NOT_BLANK_MESSAGE;
import static by.smirnov.validation.ValidationConstants.PASSWORD_MAX_SIZE;
import static by.smirnov.validation.ValidationConstants.PASSWORD_MIN_SIZE;
import static by.smirnov.validation.ValidationConstants.STANDARD_MAX_SIZE;
import static by.smirnov.validation.ValidationConstants.STANDARD_MIN_SIZE;
import static by.smirnov.validation.ValidationConstants.STANDARD_SIZE_MESSAGE;

@Getter
@Setter
public class AuthChangeRequest extends AuthRequest{

    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=STANDARD_MIN_SIZE, max=STANDARD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String newLogin;

    @NotBlank(message = NOT_BLANK_MESSAGE)
    @Size(min=PASSWORD_MIN_SIZE, max=PASSWORD_MAX_SIZE, message = STANDARD_SIZE_MESSAGE)
    private String newPassword;
}
