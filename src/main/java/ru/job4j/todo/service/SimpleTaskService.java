package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
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

    @Override
    public Task add(Task task) {
        return repository.add(task);
    }

    @Override
    public boolean update(Task task) {
        Optional<Task> taskOptional = repository.findById(task.getId());
        if (taskOptional.isEmpty() || taskOptional.get().getPriority() == null) {
            return false;
        }
        return repository.update(task);
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
