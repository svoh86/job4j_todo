package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.repository.PriorityRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Service
@ThreadSafe
@AllArgsConstructor
public class SimplePriorityService implements PriorityService {
    private final PriorityRepository repository;

    @Override
    public List<Priority> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Priority> findById(int id) {
        return repository.findById(id);
    }
}
