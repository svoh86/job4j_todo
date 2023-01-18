package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.Map;

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
    private static final String FIND_BY_ID = "FROM Category WHERE id IN (:fIds)";

    /**
     * Поиск всех категорий задач
     *
     * @return список всех категорий
     */
    @Override
    public List<Category> findAll() {
        return crudRepository.query(FIND_ALL, Category.class);
    }

    /**
     * Поиск списка категорий задач по id
     *
     * @param ids список id категорий
     * @return список категорий
     */
    @Override
    public List<Category> findById(List<Integer> ids) {
        return crudRepository.query(FIND_BY_ID,
                Category.class,
                Map.of("fIds", ids));
    }
}
