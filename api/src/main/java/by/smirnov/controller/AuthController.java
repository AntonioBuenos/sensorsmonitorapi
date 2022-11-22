package by.smirnov.controller;

import by.smirnov.domain.User;
import by.smirnov.dto.converter.UserConverter;
import by.smirnov.dto.users.AuthChangeRequest;
import by.smirnov.dto.users.AuthRequest;
import by.smirnov.dto.users.AuthResponse;
import by.smirnov.dto.users.UserResponse;
import by.smirnov.exceptionhandle.AccessForbiddenException;
import by.smirnov.exceptionhandle.BadRequestException;
import by.smirnov.exceptionhandle.NotModifiedException;
import by.smirnov.security.AuthChecker;
import by.smirnov.security.JWTUtil;
import by.smirnov.security.RegistrationService;
import by.smirnov.validation.PersonValidator;
import by.smirnov.validation.ValidationErrorConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Objects;

import static by.smirnov.constants.CommonConstants.ID;
import static by.smirnov.constants.CommonConstants.MAPPING_AUTH;
import static by.smirnov.constants.CommonConstants.MAPPING_ID;
import static by.smirnov.constants.CommonConstants.MAPPING_LOGIN;
import static by.smirnov.constants.CommonConstants.MAPPING_REGISTRATION;
import static by.smirnov.constants.ResponseEntityConstants.BAD_LOGIN_MESSAGE;

@RequiredArgsConstructor
@RestController
@RequestMapping(MAPPING_AUTH)
public class AuthController {

    private final RegistrationService service;
    private final PersonValidator personValidator;
    private final JWTUtil jwtUtil;
    private final UserConverter converter;
    private final AuthenticationManager authenticationManager;
    private final AuthChecker authChecker;

    @PostMapping(MAPPING_REGISTRATION)
    public ResponseEntity<UserResponse> performRegistration(
            @RequestBody @Valid AuthRequest request,
            BindingResult bindingResult)
            {

        if (bindingResult.hasErrors()) {
            throw new BadRequestException(ValidationErrorConverter.getErrors(bindingResult).toString());
        }

        User user = converter.convert(request);
        personValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(ValidationErrorConverter.getErrors(bindingResult).toString());
        }

        User registered = service.register(user);
        UserResponse response = converter.convert(registered);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping(MAPPING_LOGIN)
    public ResponseEntity<AuthResponse> performLogin(@RequestBody AuthRequest request) {

        String login = request.getLogin();
        UsernamePasswordAuthenticationToken inputToken =
                new UsernamePasswordAuthenticationToken(login, request.getPassword());

        try {
            authenticationManager.authenticate(inputToken);
        } catch (BadCredentialsException e) {
            throw new BadRequestException(BAD_LOGIN_MESSAGE);
        }

        String token = jwtUtil.generateToken(login);

        return ResponseEntity.ok(
                AuthResponse
                        .builder()
                        .login(login)
                        .token(token)
                        .build()
        );
    }

    @Operation(security = {@SecurityRequirement(name = "JWT Bearer")})
    @PutMapping(MAPPING_ID)
    public ResponseEntity<AuthResponse> changeCredentials(@PathVariable(ID) long id,
                                                          @RequestBody @Valid AuthChangeRequest request,
                                                          BindingResult bindingResult,
                                                          Principal principal) {

        String login = request.getLogin();
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(login, request.getPassword());

        authenticationManager.authenticate(authInputToken);

        if (bindingResult.hasErrors()) {
            throw new BadRequestException(ValidationErrorConverter.getErrors(bindingResult).toString());
        }

        if (Objects.isNull(principal) || authChecker.isAuthorized(principal.getName(), id)) {
            throw new AccessForbiddenException();
        }

        User user = converter.convert(request, id);
        if (Boolean.TRUE.equals(user.getIsDeleted())) throw new NotModifiedException();

        personValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(ValidationErrorConverter.getErrors(bindingResult).toString());
        }

        service.register(user);
        String token = jwtUtil.generateToken(user.getLogin());

        return ResponseEntity.ok(
                AuthResponse
                        .builder()
                        .login(login)
                        .token(token)
                        .build()
        );
    }
}


