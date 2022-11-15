package by.smirnov.dto.users;

import by.smirnov.domain.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@Setter
public class UserResponse {

    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String login;

}
