package ru.job4j.todo.repository;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для хранилища задач
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public interface TaskRepository {
    Task add(Task task);

    boolean update(Task task);

    boolean delete(int id);

    List<Task> findAll();

    Optional<Task> findById(int id);

    List<Task> findByLikeDescription(String key);

    List<Task> findByDone(boolean flag, int userId);

    boolean changeDone(int id);

    List<Task> findByUserId(int id);
}
