package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;

/**
 * Хранилище для пользователей. Hibernate
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Repository
@ThreadSafe
@AllArgsConstructor
public class HbnUserRepository implements UserRepository {
    private final SessionFactory sf;
    private static final String UPDATE = "UPDATE User SET name = :fName, login = :fLogin, password = :fPassword WHERE id = :fId";
    private static final String DELETE = "DELETE User WHERE id = :fId";
    private static final String FIND_BY_ID = "from User WHERE id = :fId";
    private static final String FIND_BY_LOGIN_AND_PASSWORD = "from User WHERE login = :fLogin AND password = :fPassword";

    /**
     * Сохранить в базе.
     *
     * @param user пользователь
     * @return Optional пользователя
     */
    @Override
    public Optional<User> add(User user) {
        Optional<User> userDB = Optional.empty();
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            userDB = Optional.of(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return userDB;
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    @Override
    public boolean update(User user) {
        Session session = sf.openSession();
        int i = 0;
        try {
            session.beginTransaction();
            i = session.createQuery(UPDATE)
                    .setParameter("fName", user.getName())
                    .setParameter("fLogin", user.getLogin())
                    .setParameter("fPassword", user.getPassword())
                    .setParameter("fId", user.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return i != 0;
    }

    /**
     * Удалить пользователя по id.
     *
     * @param id id пользователя
     */
    @Override
    public boolean delete(int id) {
        Session session = sf.openSession();
        int i = 0;
        try {
            session.beginTransaction();
            i = session.createQuery(DELETE)
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return i != 0;
    }

    /**
     * Найти пользователя по id
     *
     * @param id id пользователя
     * @return Optional пользователя
     */
    @Override
    public Optional<User> findById(int id) {
        Session session = sf.openSession();
        Query<User> query = session.createQuery(FIND_BY_ID, User.class)
                .setParameter("fId", id);
        Optional<User> userOptional = query.uniqueResultOptional();
        session.close();
        return userOptional;
    }

    /**
     * Найти пользователя по логину и паролю
     *
     * @param login    логин
     * @param password пароль
     * @return Optional пользователя
     */
    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        Session session = sf.openSession();
        Query<User> query = session.createQuery(FIND_BY_LOGIN_AND_PASSWORD, User.class)
                .setParameter("fLogin", login)
                .setParameter("fPassword", password);
        Optional<User> userOptional = query.uniqueResultOptional();
        session.close();
        return userOptional;
    }
}
