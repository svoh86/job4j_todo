package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.time.LocalDateTime;
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
    private static final String UPDATE = "UPDATE Task SET description = :fDescription, "
                                         + "created = :fCreated, done = :fDone WHERE id = :fId";
    private static final String DELETE = "DELETE Task WHERE id = :fId";
    private static final String FIND_ALL = "FROM Task";
    private static final String FIND_BY_ID = "FROM Task WHERE id = :fId";
    private static final String FIND_BY_LIKE_DESCRIPTION = "FROM Task WHERE description LIKE :fKey";
    private static final String FIND_BY_DONE = "FROM Task WHERE done = :fDone";
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
        return crudRepository.condition(
                UPDATE,
                Map.of(
                        "fDescription", task.getDescription(),
                        "fCreated", LocalDateTime.now(),
                        "fDone", task.isDone(),
                        "fId", task.getId()));
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
