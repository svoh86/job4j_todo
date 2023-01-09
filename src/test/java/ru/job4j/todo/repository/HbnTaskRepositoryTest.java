package ru.job4j.todo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.todo.config.HibernateConfiguration;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class HbnTaskRepositoryTest {
    private final SessionFactory sf = new HibernateConfiguration().sf();
    private final HbnTaskRepository repository = new HbnTaskRepository(new CrudRepository(sf));
    private final HbnUserRepository repositoryUser = new HbnUserRepository(new CrudRepository(sf));
    private final User user = new User("name", "login", "password");

    @AfterEach
    private void afterEach() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery("DELETE Task")
                    .executeUpdate();
            session.createQuery("DELETE User")
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }

    @Test
    public void whenAddAndFindAll() {
        Task task = new Task("desc", false, new Priority(1, "low", 1));
        repositoryUser.add(user);
        task.setUser(user);
        repository.add(task);
        List<Task> taskList = List.of(task);
        assertThat(taskList).isEqualTo(repository.findAll());
    }

    @Test
    public void whenUpdateAndFindAll() {
        Task task = new Task("desc", false, new Priority(1, "low", 1));
        Task anotherTask = new Task("new desc", false, new Priority(1, "low", 1));
        repositoryUser.add(user);
        task.setUser(user);
        anotherTask.setUser(user);
        repository.add(task);
        anotherTask.setId(task.getId());
        repository.update(anotherTask);
        Task task1 = repository.findById(task.getId()).get();
        assertThat(task1.getDescription()).isEqualTo("new desc");
    }

    @Test
    public void whenAddThenDeleteAndFindAll() {
        Task task = new Task("desc", false, new Priority(1, "low", 1));
        Task anotherTask = new Task("new desc", false, new Priority(1, "low", 1));
        repositoryUser.add(user);
        task.setUser(user);
        anotherTask.setUser(user);
        repository.add(task);
        repository.add(anotherTask);
        repository.delete(anotherTask.getId());
        List<Task> taskList = List.of(task);
        assertThat(taskList).isEqualTo(repository.findAll());
    }

    @Test
    public void whenAddAndFindById() {
        Task task = new Task("desc", false, new Priority(1, "low", 1));
        repositoryUser.add(user);
        task.setUser(user);
        repository.add(task);
        Task task1 = repository.findById(task.getId()).get();
        assertThat(task1).isEqualTo(task);
    }

    @Test
    public void whenAddAndFindByLikeDescription() {
        Task task = new Task("desc", false);
        Task anotherTask = new Task("new", true);
        repositoryUser.add(user);
        task.setUser(user);
        anotherTask.setUser(user);
        repository.add(task);
        repository.add(anotherTask);
        List<Task> likeDescription = repository.findByLikeDescription("es");
        List<Task> taskList = List.of(task);
        assertThat(likeDescription).isEqualTo(taskList);
    }

    @Test
    public void whenAddAndFindByDone() {
        Task task = new Task("desc", false, new Priority(1, "low", 1));
        Task anotherTask = new Task("new", true, new Priority(1, "low", 1));
        repositoryUser.add(user);
        task.setUser(user);
        anotherTask.setUser(user);
        repository.add(task);
        repository.add(anotherTask);
        List<Task> listByDone = repository.findByDone(true);
        List<Task> taskList = List.of(anotherTask);
        assertThat(listByDone).isEqualTo(taskList);
    }
}