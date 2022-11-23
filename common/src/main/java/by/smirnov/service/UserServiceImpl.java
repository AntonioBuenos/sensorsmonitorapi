package by.smirnov.service;

import by.smirnov.domain.User;
import by.smirnov.exception.NoSuchEntityException;
import by.smirnov.repository.UserRepository;
import by.smirnov.repository.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public User findById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(NoSuchEntityException::new);
    }

    @Override
    public User findByLogin(String login){
        return repository.findByLogin(login).orElseThrow(NoSuchEntityException::new);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public User update(User toBeUpdated) {
        return repository.update(toBeUpdated);
    }

    @Transactional
    @Override
    public User delete(Long id) {
        return repository.delete(id);
    }

    @Transactional
    @Override
    public void hardDelete(Long id){
        repository.hardDelete(id);
    }
}
