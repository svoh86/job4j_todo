package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.repository.PriorityRepository;
import ru.job4j.todo.repository.TaskRepository;

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

    @Override
    public Task add(Task task) {
        return repository.add(task);
    }

    /**
     * Метод проверяет, что приоритет существует и обновляет задачу.
     *
     * @param task задача
     * @return true or false
     */
    @Override
    public boolean update(Task task) {
        Optional<Priority> priorityOptional = priorityRepository.findById(task.getPriority().getId());
        return priorityOptional.isPresent() && repository.update(task);
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
