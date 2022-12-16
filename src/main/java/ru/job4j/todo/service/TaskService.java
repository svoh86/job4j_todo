package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для слоя сервис задач
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public interface TaskService {
    Task add(Task task);

    void update(Task task);

    void delete(int id);

    List<Task> findAll();

    Optional<Task> findById(int id);

    List<Task> findByLikeDescription(String key);

    List<Task> findByDone(boolean flag);
}
