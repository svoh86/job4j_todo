package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;
import ru.job4j.todo.util.UserSession;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * Контроллер пользователей
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Controller
@AllArgsConstructor
@ThreadSafe
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    /**
     * Показывает страницу регистрации пользователя
     *
     * @param model       Model
     * @param fail        флаг, что пользователь уже существует.
     * @param success     флаг, что регистрация успешна.
     * @param httpSession HttpSession
     * @return
     */
    @GetMapping("/add")
    public String add(Model model,
                      @RequestParam(name = "fail", required = false) Boolean fail,
                      @RequestParam(name = "success", required = false) Boolean success,
                      HttpSession httpSession) {
        UserSession.getUser(model, httpSession);
        model.addAttribute("fail", fail != null);
        model.addAttribute("success", success != null);
        return "user/add";
    }

    /**
     * Добавляет данные из формы в БД.
     *
     * @param user пользователь
     * @return сообщение об успешной/неуспешной регистрации
     */
    @PostMapping("/create")
    public String create(@ModelAttribute User user) {
        Optional<User> optionalUser = service.add(user);
        if (optionalUser.isEmpty()) {
            return "redirect:/user/add?fail=true";
        }
        return "redirect:/user/add?success=true";
    }

    /**
     * Показывает страницу авторизации пользователя.
     * Добавляет в модель атрибуты для валидации данных (fail).
     *
     * @param model Model
     * @param fail  флаг, что входные данные невалидные
     * @return user/loginPage
     */
    @GetMapping("/loginPage")
    public String loginPage(Model model,
                            @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "user/loginPage";
    }

    /**
     * Ищет пользователя в БД. Если его там нет, то возвращает страницу с параметром fail=true.
     * Иначе переходит на страницу со всеми задачами.
     * Получает объект httpSession из запроса и устанавливает ей параметр "user".
     *
     * @param user пользователь
     * @param req  запрос
     * @return tasks/all или user/loginPage?fail=true
     */
    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userOptional = service.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if (userOptional.isEmpty()) {
            return "redirect:/user/loginPage?fail=true";
        }
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("user", userOptional.get());
        return "redirect:/tasks/all";
    }

    /**
     * Выходит из сессии.
     *
     * @param session HttpSession
     * @return станицу авторизации пользователя.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/loginPage";
    }


}
