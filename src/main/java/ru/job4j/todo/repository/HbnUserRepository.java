package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Map;
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
    private final CrudRepository crudRepository;
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
        crudRepository.run(session -> session.persist(user));
        return Optional.ofNullable(user);
    }

    /**
     * Обновить в базе пользователя.
     *
     * @param user пользователь.
     */
    @Override
    public boolean update(User user) {
        return crudRepository.condition(
                UPDATE,
                Map.of(
                        "fName", user.getName(),
                        "fLogin", user.getLogin(),
                        "fPassword", user.getPassword(),
                        "fId", user.getId()
                ));
    }

    /**
     * Удалить пользователя по id.
     *
     * @param id id пользователя
     */
    @Override
    public boolean delete(int id) {
        return crudRepository.condition(
                DELETE,
                Map.of("fId", id)
        );
    }

    /**
     * Найти пользователя по id
     *
     * @param id id пользователя
     * @return Optional пользователя
     */
    @Override
    public Optional<User> findById(int id) {
        return crudRepository.optional(FIND_BY_ID, User.class, Map.of("fId", id));
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
        return crudRepository.optional(
                FIND_BY_LOGIN_AND_PASSWORD,
                User.class,
                Map.of(
                        "fLogin", login,
                        "fPassword", password
                ));
    }
}
