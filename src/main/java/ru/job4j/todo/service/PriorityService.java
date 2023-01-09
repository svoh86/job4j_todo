package ru.job4j.todo.service;

import ru.job4j.todo.model.Priority;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для слоя сервис приоритетов задач
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public interface PriorityService {
    List<Priority> findAll();

    Optional<Priority> findById(int id);
}
