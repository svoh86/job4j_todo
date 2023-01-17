package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Хранилище для задач. Hibernate
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Repository
@ThreadSafe
@AllArgsConstructor
public class HbnTaskRepository implements TaskRepository {
    private final CrudRepository crudRepository;
    private static final String DELETE = "DELETE Task WHERE id = :fId";
    private static final String FIND_ALL = "SELECT DISTINCT t FROM Task t JOIN FETCH t.priority "
                                           + "JOIN FETCH t.categories";
    private static final String FIND_BY_ID = "FROM Task t JOIN FETCH t.priority JOIN FETCH t.categories WHERE t.id = :fId";
    private static final String FIND_BY_LIKE_DESCRIPTION = "FROM Task t JOIN FETCH t.priority JOIN FETCH t.categories WHERE description LIKE :fKey";
    private static final String FIND_BY_DONE = "SELECT DISTINCT t FROM Task t JOIN FETCH t.priority "
                                               + "JOIN FETCH t.categories WHERE done = :fDone";
    private static final String UPDATE_DONE = "UPDATE Task SET done = true WHERE id = :fId";

    /**
     * Добавляет задачу в БД
     *
     * @param task задача
     * @return задача
     */
    @Override
    public Task add(Task task) {
        crudRepository.run(session -> session.persist(task));
        return task;
    }

    /**
     * Обновляет задачу в БД
     *
     * @param task задача
     */
    @Override
    public boolean update(Task task) {
        return crudRepository.condition(session -> task.equals(session.merge(task)));
    }

    /**
     * Удаляет задачу из БД
     *
     * @param id идентификатор задачи
     */
    @Override
    public boolean delete(int id) {
        return crudRepository.condition(DELETE, Map.of("fId", id));
    }

    /**
     * Поиск всех задач
     *
     * @return список всех задач
     */
    @Override
    public List<Task> findAll() {
        return crudRepository.query(FIND_ALL, Task.class);
    }

    /**
     * Поиск задач по id
     *
     * @param id id задачи
     * @return Optional задачи
     */
    @Override
    public Optional<Task> findById(int id) {
        return crudRepository.optional(FIND_BY_ID,
                Task.class,
                Map.of("fId", id));
    }

    /**
     * Поиск задач по описанию LIKE %key%
     *
     * @param key ключ
     * @return список задач
     */
    @Override
    public List<Task> findByLikeDescription(String key) {
        return crudRepository.query(
                FIND_BY_LIKE_DESCRIPTION,
                Task.class,
                Map.of("fKey", "%" + key + "%"));
    }

    /**
     * Поиск по состоянию задачи
     *
     * @param flag состояние задачи
     * @return список задач
     */
    @Override
    public List<Task> findByDone(boolean flag) {
        return crudRepository.query(
                FIND_BY_DONE,
                Task.class,
                Map.of("fDone", flag));
    }

    /**
     * Обновляет у задачи поле done
     *
     * @param id id задачи
     */
    @Override
    public boolean changeDone(int id) {
        return crudRepository.condition(
                UPDATE_DONE,
                Map.of("fId", id));
    }
}
