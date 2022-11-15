package by.smirnov.dto.converter;

import by.smirnov.domain.Role;
import by.smirnov.domain.User;
import by.smirnov.dto.users.AuthChangeRequest;
import by.smirnov.dto.users.AuthRequest;
import by.smirnov.dto.users.UserResponse;
import by.smirnov.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class UserConverter {

    private final ModelMapper modelMapper;
    private final UserService service;

    public User convert(AuthRequest request){
        User created = modelMapper.map(request, User.class);
        created.setRole(Role.ROLE_VIEWER);
        created.setCreationDate(Timestamp.valueOf(LocalDateTime.now()));
        created.setIsDeleted(false);
        return created;
    }

    public User convert(AuthRequest request, Long id){
        User old = service.findById(id);

        old.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        return old;
    }

    public User convert(AuthChangeRequest request, Long id){
        User old = service.findById(id);
        old.setLogin(request.getNewLogin());
        old.setPassword(request.getNewPassword());
        old.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        return old;
    }

    public UserResponse convert(User user){
        return modelMapper.map(user, UserResponse.class);
    }
}
