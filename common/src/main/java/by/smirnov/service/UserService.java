package by.smirnov.service;

import by.smirnov.domain.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    User findById(Long id);

    User findByLogin(String login);

    List<User> findAll(Pageable pageable);

    User update(User toBeUpdated);

    User delete(Long id);

    void hardDelete(Long id);
}
