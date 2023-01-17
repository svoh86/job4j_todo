package ru.job4j.todo.service;

import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для слоя сервис категорий задач
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public interface CategoryService {
    List<Category> findAll();

    Optional<Category> findById(int id);
}
