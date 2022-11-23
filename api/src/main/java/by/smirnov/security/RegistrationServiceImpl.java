package by.smirnov.security;

import by.smirnov.domain.User;
import by.smirnov.repository.UserRepository;
import by.smirnov.repository.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RegistrationServiceImpl implements RegistrationService{

    private final UserRepositoryImpl repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public User register(User object) {
        object.setPassword(passwordEncoder.encode(object.getPassword()));
        return repository.create(object);
    }
}
