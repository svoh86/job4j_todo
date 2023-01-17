package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.CategoryRepository;
import ru.job4j.todo.repository.PriorityRepository;
import ru.job4j.todo.repository.TaskRepository;

import java.util.ArrayList;
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
     * Проходит по всему списку категорий и добавляет категории в список категорий, если они есть.
     * Далее этот список категорий устанавливается задаче.
     *
     * @param task       задача
     * @param categories список id категорий задачи
     * @return задачу
     */
    @Override
    public Task add(Task task, List<Integer> categories) {
        categorySet(categories, task);
        return repository.add(task);
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
        categorySet(categories, task);
        return priorityOptional.isPresent() && repository.update(task);
    }

    private void categorySet(List<Integer> categories, Task task) {
        List<Category> categoryList = new ArrayList<>();
        for (int id : categories) {
            Optional<Category> categoryOptional = categoryRepository.findById(id);
            categoryOptional.ifPresent(categoryList::add);
        }
        task.setCategories(categoryList);
    }

    @Override
    public boolean delete(int id) {
        return repository.delete(id);
    }

    @Override
    public List<Task> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Task> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public List<Task> findByLikeDescription(String key) {
        return repository.findByLikeDescription(key);
    }

    @Override
    public List<Task> findByDone(boolean flag) {
        return repository.findByDone(flag);
    }

    @Override
    public boolean changeDone(int id) {
        return repository.changeDone(id);
    }
}
