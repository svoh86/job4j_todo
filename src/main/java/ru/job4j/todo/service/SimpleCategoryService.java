package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Service
@ThreadSafe
@AllArgsConstructor
public class SimpleCategoryService implements CategoryService {
    private final CategoryRepository repository;

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Category> findById(int id) {
        return repository.findById(id);
    }
}
