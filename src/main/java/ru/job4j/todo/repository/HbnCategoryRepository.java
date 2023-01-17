package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Хранилище для категорий задач. Hibernate
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Repository
@ThreadSafe
@AllArgsConstructor
public class HbnCategoryRepository implements CategoryRepository {
    private final CrudRepository crudRepository;
    private static final String FIND_ALL = "FROM Category";
    private static final String FIND_BY_ID = "FROM Category WHERE id = :fId";

    /**
     * Поиск всех категорий задач
     *
     * @return список всех задач
     */
    @Override
    public List<Category> findAll() {
        return crudRepository.query(FIND_ALL, Category.class);
    }

    /**
     * Поиск категорий задач по id
     *
     * @param id id задачи
     * @return Optional задачи
     */
    @Override
    public Optional<Category> findById(int id) {
        return crudRepository.optional(FIND_BY_ID,
                Category.class,
                Map.of("fId", id));
    }
}
