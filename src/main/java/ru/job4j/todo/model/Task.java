package ru.job4j.todo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Модель данных задач
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Task {
    /**
     * Идентификатор задачи
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Описание задачи
     */
    private String description;
    /**
     * Дата создания
     */
    @EqualsAndHashCode.Exclude
    private LocalDateTime created = LocalDateTime.now();
    /**
     * Статус выполнения задачи
     */
    @EqualsAndHashCode.Exclude
    private boolean done;
    /**
     * Внешний ключ на пользователя
     */
    @ManyToOne
    @JoinColumn(name = "todo_user_id")
    private User user;
    /**
     * Внешний ключ на приоритет
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_id")
    private Priority priority;
    /**
     * Список категорий задач
     */
    @ManyToMany
    @JoinTable(
            name = "tasks_categories",
            joinColumns = {@JoinColumn(name = "task_id")},
            inverseJoinColumns = {@JoinColumn(name = "categories_id")}
    )
    private List<Category> categories = new ArrayList<>();

    public Task(String description, boolean done) {
        this.description = description;
        this.done = done;
    }

    public Task(String description, LocalDateTime created, boolean done) {
        this.description = description;
        this.created = created;
        this.done = done;
    }

    public Task(String description, boolean done, Priority priority) {
        this.description = description;
        this.done = done;
        this.priority = priority;
    }

    public Task(String description, boolean done, Priority priority, List<Category> categories) {
        this.description = description;
        this.done = done;
        this.priority = priority;
        this.categories = categories;
    }
}
