package ru.job4j.todo.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.todo.config.HibernateConfiguration;
import ru.job4j.todo.model.User;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class HbnUserRepositoryTest {
    private final SessionFactory sf = new HibernateConfiguration().sf();
    private final HbnUserRepository repository = new HbnUserRepository(new CrudRepository(sf));

    @AfterEach
    private void afterEach() {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
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
    public void whenAddAndFindById() {
        User user = new User("user", "login", "password");
        Optional<User> optionalUser = repository.add(user);
        Optional<User> userById = repository.findById(optionalUser.get().getId());
        assertThat(userById.get().getName()).isEqualTo("user");
    }

    @Test
    public void whenUpdateAndFindById() {
        User user = new User("user", "login", "password");
        User user2 = new User("new user", "login", "password");
        Optional<User> optionalUser = repository.add(user);
        Optional<User> userById = repository.findById(optionalUser.get().getId());
        user2.setId(userById.get().getId());
        repository.update(user2);
        assertThat(repository.findById(optionalUser.get().getId()).get().getName())
                .isEqualTo("new user");
    }

    @Test
    public void whenDelete() {
        User user = new User("user", "login", "password");
        Optional<User> optionalUser = repository.add(user);
        Optional<User> userById = repository.findById(optionalUser.get().getId());
        assertThat(repository.delete(userById.get().getId())).isTrue();
    }

    @Test
    public void whenAddAndFindByLoginAndPassword() {
        User user = new User("user", "login", "password");
        User user2 = new User("new user", "login2", "password");
        Optional<User> optionalUser = repository.add(user);
        repository.add(user2);
        Optional<User> userById = repository.findById(optionalUser.get().getId());
        Optional<User> userByLoginAndPassword = repository.findByLoginAndPassword("login", "password");
        assertThat(userById.get()).isEqualTo(userByLoginAndPassword.get());
    }
}