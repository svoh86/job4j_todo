package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.repository.CategoryRepository;
import ru.job4j.todo.repository.PriorityRepository;
import ru.job4j.todo.repository.TaskRepository;
import ru.job4j.todo.util.UserZone;

import java.util.List;
import java.util.Optional;

/**
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Service
@ThreadSafe
@AllArgsConstructor
public class SimpleTaskService implements TaskService {
    private final TaskRepository repository;
    private final PriorityRepository priorityRepository;
    private final CategoryRepository categoryRepository;

    /**
     * Метод принимает задачу и список id категорий задачи.
     * Проверяет, что категории существуют.
     * Задача добавляется в БД.
     *
     * @param task       задача
     * @param categories список id категорий задачи
     * @return true or false
     */
    @Override
    public boolean add(Task task, List<Integer> categories) {
        boolean rsl = false;
        if (categorySet(categories, task)) {
            repository.add(task);
            rsl = true;
        }
        return rsl;
    }

    /**
     * Метод проверяет, что приоритет и категории существуют и обновляет задачу.
     *
     * @param task задача
     * @return true or false
     */
    @Override
    public boolean update(Task task, List<Integer> categories) {
        Optional<Priority> priorityOptional = priorityRepository.findById(task.getPriority().getId());
        return priorityOptional.isPresent() && categorySet(categories, task) && repository.update(task);
    }

    /**
     * Метод ищет список категорий по списку их id, если они есть.
     * Далее этот список категорий устанавливается задаче.
     *
     * @param categories список id категорий задачи
     * @param task       задача
     * @return true or false
     */
    private boolean categorySet(List<Integer> categories, Task task) {
        List<Category> categoryList = categoryRepository.findById(categories);
        task.setCategories(categoryList);
        return categories.size() == categoryList.size();
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id);
    }

    /**
     * Ищет все задачи и устанавливает им время
     * с учетом часового пояса пользователя.
     *
     * @param user пользователь
     * @return список задач с часовым поясом пользователя
     */
    @Override
    public List<Task> findAll(User user) {
        List<Task> taskList = repository.findAll();
        UserZone.setTimeZone(user, taskList);
        return taskList;
    }

    /**
     * Ищет задачи по id и устанавливает им время
     * с учетом часового пояса пользователя.
     *
     * @param id   id задачи
     * @param user пользователь
     * @return Optional задачи
     */
    @Override
    public Optional<Task> findById(int id, User user) {
        Optional<Task> taskOptional = repository.findById(id);
        taskOptional.ifPresent(task -> UserZone.setTimeZone(user, List.of(task)));
        return taskOptional;
    }

    @Override
    public List<Task> findByLikeDescription(String key) {
        return repository.findByLikeDescription(key);
    }

    /**
     * Ищет задачи по флагу "Выполнено/Не выполнено".
     * Устанавливает этим задачам время с учетом часового пояса пользователя.
     *
     * @param flag флаг
     * @param user пользователь
     * @return список задач
     */
    @Override
    public List<Task> findByDone(boolean flag, User user) {
        List<Task> taskList = repository.findByDone(flag, user.getId());
        UserZone.setTimeZone(user, taskList);
        return taskList;
    }

    @Override
    public boolean changeDone(int id) {
        return repository.changeDone(id);
    }

    /**
     * Ищет задачи по id пользователя и устанавливает им время
     * с учетом часового пояса пользователя.
     *
     * @param user пользователь
     * @return список задач
     */
    @Override
    public List<Task> findByUserId(User user) {
        List<Task> taskList = repository.findByUserId(user.getId());
        UserZone.setTimeZone(user, taskList);
        return taskList;
    }
}
