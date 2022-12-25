package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * В данный класс вынесены команды Hibernate,
 * чтобы ими можно было пользоваться из нескольких репозиториев.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Repository
@AllArgsConstructor
public class CrudRepository {
    /**
     * Объект конфигуратор. В нем происходит создания пулов, загрузка кешей, проверка моделей.
     */
    private final SessionFactory sf;

    /**
     * Метод принимает "команду" и передает ее на выполнение в метод tx().
     *
     * @param command команда
     */
    public void run(Consumer<Session> command) {
        tx(session -> {
            command.accept(session);
            return null;
        });
    }

    /**
     * Метод принимает запрос и Map с параметрами запроса.
     * Далее создается команда, которую передаем на выполнение в метод tx().
     *
     * @param query запрос
     * @param args  Map с параметрами запроса
     * @return boolean
     */
    public boolean condition(String query, Map<String, Object> args) {
        Predicate<Session> predicate = session -> {
            var sq = session.createQuery(query);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.executeUpdate() != 0;
        };
        return tx(predicate::test);
    }

    /**
     * Метод принимает запрос, класс объекта и Map с параметрами запроса.
     * Далее создается команда, которую передаем на выполнение в метод tx().
     *
     * @param query запрос
     * @param cl    класс объекта
     * @param args  Map с параметрами запроса
     * @param <T>   общий тип
     * @return Optional
     */
    public <T> Optional<T> optional(String query, Class<T> cl, Map<String, Object> args) {
        Function<Session, Optional<T>> command = session -> {
            var sq = session.createQuery(query, cl);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return Optional.ofNullable(sq.getSingleResult());
        };
        return tx(command);
    }

    /**
     * Метод принимает запрос и класс объекта
     * Далее создается команда, которую передаем на выполнение в метод tx().
     *
     * @param query запрос
     * @param cl    класс объекта
     * @param <T>   общий тип
     * @return список объектов типа Т
     */
    public <T> List<T> query(String query, Class<T> cl) {
        Function<Session, List<T>> command = session -> session
                .createQuery(query, cl)
                .list();
        return tx(command);
    }

    /**
     * Метод принимает запрос, класс объекта и Map с параметрами запроса.
     * Далее создается команда, которую передаем на выполнение в метод tx().
     *
     * @param query запрос
     * @param cl    класс объекта
     * @param args  Map с параметрами запроса
     * @param <T>   общий тип
     * @return список объектов типа Т
     */
    public <T> List<T> query(String query, Class<T> cl, Map<String, Object> args) {
        Function<Session, List<T>> command = session -> {
            var sq = session
                    .createQuery(query, cl);
            for (Map.Entry<String, Object> arg : args.entrySet()) {
                sq.setParameter(arg.getKey(), arg.getValue());
            }
            return sq.list();
        };
        return tx(command);
    }

    /**
     * Метод выполняет абстрактную операцию.
     * Он принимает некую "команду", открывает сессию, начинает транзакцию и выполняет эту команду.
     * Команда принимается в виде объекта функционального интерфейса,
     * благодаря чему достигается абстрактность операции.
     * Методу tx() не важно, придет команда на вставку данных, изменение, удаление и т.д.
     * Он не знает её внутреннюю реализацию, он просто получает команду и выполняет её.
     *
     * @param command команда
     * @param <T>     общий тип
     * @return общий тип
     */
    public <T> T tx(Function<Session, T> command) {
        Session session = sf.openSession();
        try (session) {
            Transaction tx = session.beginTransaction();
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (Exception e) {
            Transaction tx = session.getTransaction();
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }
}
