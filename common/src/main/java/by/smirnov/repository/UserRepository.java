package by.smirnov.repository;

import by.smirnov.domain.User;

import java.util.Optional;

public interface UserRepository extends CRUDRepository<Long, User> {

    Optional<User> findByLogin(String login);
}
