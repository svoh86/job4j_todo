package ru.job4j.todo.service;

import ru.job4j.todo.model.Category;

import java.util.List;

/**
 * Интерфейс для слоя сервис категорий задач
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public interface CategoryService {
    List<Category> findAll();

    List<Category> findById(List<Integer> ids);
}
