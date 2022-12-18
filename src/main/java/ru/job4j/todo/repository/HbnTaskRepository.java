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
    private final SessionFactory sf;
    private static final String UPDATE = "UPDATE Task SET description = :fDescription, "
                                         + "created = :fCreated, done = :fDone WHERE id = :fId";
    private static final String DELETE = "DELETE Task WHERE id = :fId";
    private static final String FIND_ALL = "from Task";
    private static final String FIND_BY_ID = "from Task WHERE id = :fId";
    private static final String FIND_BY_LIKE_DESCRIPTION = "from Task WHERE description LIKE :fKey";
    private static final String FIND_BY_DONE = "from Task WHERE done = :fDone";
    private static final String UPDATE_DONE = "UPDATE Task SET done = true WHERE id = :fId";

    /**
     * Добавляет задачу в БД
     *
     * @param task задача
     * @return задача
     */
    @Override
    public Task add(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return task;
    }

    /**
     * Обновляет задачу в БД
     *
     * @param task задача
     */
    @Override
    public boolean update(Task task) {
        Session session = sf.openSession();
        int i = 0;
        try {
            session.beginTransaction();
            i = session.createQuery(UPDATE)
                    .setParameter("fDescription", task.getDescription())
                    .setParameter("fCreated", LocalDateTime.now())
                    .setParameter("fDone", task.isDone())
                    .setParameter("fId", task.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return i != 0;
    }

    /**
     * Удаляет задачу из БД
     *
     * @param id идентификатор задачи
     */
    @Override
    public boolean delete(int id) {
        Session session = sf.openSession();
        int i = 0;
        try {
            session.beginTransaction();
            i = session.createQuery(DELETE)
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return i != 0;
    }

    /**
     * Поиск всех задач
     *
     * @return список всех задач
     */
    @Override
    public List<Task> findAll() {
        Session session = sf.openSession();
        Query<Task> query = session.createQuery(FIND_ALL, Task.class);
        List<Task> taskList = query.list();
        session.close();
        return taskList;
    }

    /**
     * Поиск задач по id
     *
     * @param id id задачи
     * @return Optional задачи
     */
    @Override
    public Optional<Task> findById(int id) {
        Session session = sf.openSession();
        Query<Task> query = session.createQuery(FIND_BY_ID, Task.class)
                .setParameter("fId", id);
        Optional<Task> optionalTask = query.uniqueResultOptional();
        session.close();
        return optionalTask;
    }

    /**
     * Поиск задач по описанию LIKE %key%
     *
     * @param key ключ
     * @return список задач
     */
    @Override
    public List<Task> findByLikeDescription(String key) {
        Session session = sf.openSession();
        Query<Task> query = session.createQuery(FIND_BY_LIKE_DESCRIPTION, Task.class)
                .setParameter("fKey", "%" + key + "%");
        List<Task> taskList = query.list();
        session.close();
        return taskList;
    }

    /**
     * Поиск по состоянию задачи
     *
     * @param flag состояние задачи
     * @return список задач
     */
    @Override
    public List<Task> findByDone(boolean flag) {
        Session session = sf.openSession();
        Query<Task> query = session.createQuery(FIND_BY_DONE, Task.class)
                .setParameter("fDone", flag);
        List<Task> taskList = query.list();
        session.close();
        return taskList;
    }

    /**
     * Обновляет у задачи поле done
     *
     * @param id id задачи
     */
    @Override
    public boolean changeDone(int id) {
        Session session = sf.openSession();
        int i = 0;
        try {
            session.beginTransaction();
            i = session.createQuery(UPDATE_DONE)
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return i != 0;
    }
}
