package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для слоя сервис задач
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public interface TaskService {
    boolean add(Task task, List<Integer> categories);

    boolean update(Task task, List<Integer> categories);

    boolean delete(int id);

    List<Task> findAll(User user);

    Optional<Task> findById(int id, User user);

    List<Task> findByLikeDescription(String key);

    List<Task> findByDone(boolean flag, User user);

    boolean changeDone(int id);

    List<Task> findByUserId(User user);
}
