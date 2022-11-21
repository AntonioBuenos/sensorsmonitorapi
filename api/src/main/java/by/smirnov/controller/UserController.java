package by.smirnov.controller;

import by.smirnov.domain.User;
import by.smirnov.dto.converter.UserConverter;
import by.smirnov.dto.users.AuthChangeRequest;
import by.smirnov.dto.users.UserResponse;
import by.smirnov.exceptionhandle.AccessForbiddenException;
import by.smirnov.exceptionhandle.BadRequestException;
import by.smirnov.exceptionhandle.NotModifiedException;
import by.smirnov.security.AuthChecker;
import by.smirnov.service.UserService;
import by.smirnov.validation.ValidationErrorConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static by.smirnov.constants.CommonConstants.ID;
import static by.smirnov.constants.CommonConstants.MAPPING_ID;
import static by.smirnov.constants.CommonConstants.MAPPING_REST;
import static by.smirnov.constants.ResponseEntityConstants.DELETED_STATUS;
import static by.smirnov.controller.UserControllerConstants.MAPPING_USERS;
import static by.smirnov.controller.UserControllerConstants.USERS;

@RestController
@RequiredArgsConstructor
@RequestMapping(MAPPING_REST + MAPPING_USERS)
public class UserController {

    private final UserService service;
    private final UserConverter converter;
    private final AuthChecker authChecker;

    @GetMapping
    public ResponseEntity<Map<String, List<UserResponse>>> index(Pageable pageable) {
        List<UserResponse> users = service.findAll(pageable)
                .stream()
                .map(converter::convert)
                .toList();
        return new ResponseEntity<>(Collections.singletonMap(USERS, users), HttpStatus.OK);
    }


    @GetMapping(MAPPING_ID)
    public ResponseEntity<UserResponse> show(@PathVariable(ID) long id, Principal principal) {

        if (authChecker.isAuthorized(principal.getName(), id)) throw new AccessForbiddenException();

        User user = service.findById(id);

        UserResponse response = converter.convert(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(MAPPING_ID)
    public ResponseEntity<UserResponse> update(@PathVariable(name = ID) Long id,
                                               @RequestBody @Valid AuthChangeRequest request,
                                               BindingResult bindingResult,
                                               Principal principal) {

        if (bindingResult.hasErrors()) {
            throw new BadRequestException(ValidationErrorConverter.getErrors(bindingResult).toString());
        }

        User user = service.findById(id);
        if (!authChecker.isAuthorized(principal.getName(), id)) throw new AccessForbiddenException();
        else if (Boolean.TRUE.equals(user.getIsDeleted())) throw new NotModifiedException();

        User changed = service.update(converter.convert(request, id));
        UserResponse response = converter.convert(changed);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

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
