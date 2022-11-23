package by.smirnov.repository;

import by.smirnov.domain.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Primary
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(User.class, id));
    }

    @Transactional(readOnly = true)
    public Optional<User> findByLogin(String login) {
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery("select p from User p where p.login = :login", User.class);
        query.setParameter("login", login);

        return Optional.ofNullable(query.uniqueResult());
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery(
                "select p from User p where p.isDeleted = false order by p.id", User.class
        ).getResultList();
    }

    @Transactional
    public User create(User object) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(object);
        return session.get(User.class, object.getId());
    }

    @Transactional
    public User update(User user) {
        Session session = sessionFactory.getCurrentSession();
        User personToBeUpdated = session.get(User.class, user.getId());
        personToBeUpdated.setRole(user.getRole());
        personToBeUpdated.setModificationDate(Timestamp.valueOf(LocalDateTime.now()));
        personToBeUpdated.setLogin(user.getLogin());
        personToBeUpdated.setPassword(user.getPassword());
        return personToBeUpdated;
    }

    @Transactional
    public User delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        User personToBeDeleted = session.get(User.class, id);
        personToBeDeleted.setIsDeleted(true);
        personToBeDeleted.setTerminationDate(Timestamp.valueOf(LocalDateTime.now()));
        return session.get(User.class, id);
    }

    @Transactional
    public void hardDelete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(User.class, id));
    }
}
