package ru.job4j.todo.util;

import org.springframework.ui.Model;
import ru.job4j.todo.model.User;

import javax.servlet.http.HttpSession;

/**
 * Служебный класс.
 * Добавляет в модель атрибут "user" и возвращает пользователя.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public final class UserSession {

    private UserSession() {
    }

    public static User getUser(Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость!");
        }
        model.addAttribute("user", user);
        return user;
    }
}
