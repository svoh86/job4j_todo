package ru.job4j.todo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    @EqualsAndHashCode.Exclude
    private boolean done;
    @ManyToOne
    @JoinColumn(name = "todo_user_id")
    private User user;

    public Task(String description, boolean done) {
        this.description = description;
        this.done = done;
    }

    public Task(String description, LocalDateTime created, boolean done) {
        this.description = description;
        this.created = created;
        this.done = done;
    }
}
