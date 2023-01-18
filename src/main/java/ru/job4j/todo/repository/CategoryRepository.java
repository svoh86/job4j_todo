package ru.job4j.todo.repository;

import ru.job4j.todo.model.Category;

import java.util.List;

/**
 * Интерфейс для хранилища категорий задач
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public interface CategoryRepository {
    List<Category> findAll();

    List<Category> findById(List<Integer> ids);
}
