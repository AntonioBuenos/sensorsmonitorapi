package by.smirnov.service;

import by.smirnov.domain.User;

import java.util.List;

public interface UserService {

    User findById(Long id);

    User findByLogin(String login);

    List<User> findAll();

    User update(User toBeUpdated);

    User delete(Long id);

    void hardDelete(Long id);
}
