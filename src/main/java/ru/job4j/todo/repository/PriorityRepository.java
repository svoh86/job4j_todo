package ru.job4j.todo.repository;

import ru.job4j.todo.model.Priority;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для хранилища приоритетов задач
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public interface PriorityRepository {
    List<Priority> findAll();

    Optional<Priority> findById(int id);
}
