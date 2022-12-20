package ru.job4j.todo.service;

import ru.job4j.todo.model.User;

import java.util.Optional;

/**
 * Интерфейс для слоя сервис пользователей
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public interface UserService {
    Optional<User> add(User user);

    boolean update(User user);

    boolean delete(int id);

    Optional<User> findById(int id);

    Optional<User> findByLoginAndPassword(String login, String password);

}
