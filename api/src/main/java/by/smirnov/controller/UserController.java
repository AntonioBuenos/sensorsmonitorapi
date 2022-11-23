package by.smirnov.controller;

import by.smirnov.domain.User;
import by.smirnov.dto.converter.UserConverter;
import by.smirnov.dto.users.UserResponse;
import by.smirnov.exceptionhandle.AccessForbiddenException;
import by.smirnov.exceptionhandle.NotModifiedException;
import by.smirnov.security.AuthChecker;
import by.smirnov.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static by.smirnov.constants.CommonConstants.ID;
import static by.smirnov.constants.CommonConstants.MAPPING_ID;
import static by.smirnov.constants.ResponseEntityConstants.DELETED_STATUS;
import static by.smirnov.controller.UserControllerConstants.MAPPING_USERS;
import static by.smirnov.controller.UserControllerConstants.USERS;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_USERS)
public class UserController {

    private final UserService service;
    private final UserConverter converter;
    private final AuthChecker authChecker;

    @Operation(security = {@SecurityRequirement(name = "JWT Bearer")})
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping
    public ResponseEntity<Map<String, List<UserResponse>>> index() {
        List<UserResponse> users = service.findAll()
                .stream()
                .map(converter::convert)
                .toList();
        return new ResponseEntity<>(Collections.singletonMap(USERS, users), HttpStatus.OK);
    }


    @Operation(security = {@SecurityRequirement(name = "JWT Bearer")})
    @GetMapping(MAPPING_ID)
    public ResponseEntity<UserResponse> show(@PathVariable(ID) long id, Principal principal) {

        if (!authChecker.isAuthorized(principal.getName(), id)) throw new AccessForbiddenException();

        User user = service.findById(id);

        UserResponse response = converter.convert(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(security = {@SecurityRequirement(name = "JWT Bearer")})
    @DeleteMapping(MAPPING_ID)
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable(ID) long id, Principal principal) {

        User user = service.findById(id);
        if (!authChecker.isAuthorized(principal.getName(), id)) throw new AccessForbiddenException();
        else if (Boolean.TRUE.equals(user.getIsDeleted())) throw new NotModifiedException();

        User deleted = service.delete(id);
        return new ResponseEntity<>(
                Collections.singletonMap(DELETED_STATUS, deleted.getIsDeleted()),
                HttpStatus.OK);
    }
}
