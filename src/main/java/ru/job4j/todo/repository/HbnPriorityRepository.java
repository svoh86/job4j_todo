package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Хранилище для приоритетов задач. Hibernate
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Repository
@ThreadSafe
@AllArgsConstructor
public class HbnPriorityRepository implements PriorityRepository {
    private final CrudRepository crudRepository;
    private static final String FIND_ALL = "FROM Priority";
    private static final String FIND_BY_ID = "FROM Priority WHERE id = :fId";

    /**
     * Поиск всех приоритетов задач
     *
     * @return список всех задач
     */
    @Override
    public List<Priority> findAll() {
        return crudRepository.query(FIND_ALL, Priority.class);
    }

    /**
     * Поиск приоритетов задач по id
     *
     * @param id id задачи
     * @return Optional задачи
     */
    @Override
    public Optional<Priority> findById(int id) {
        return crudRepository.optional(FIND_BY_ID,
                Priority.class,
                Map.of("fId", id));
    }
}
