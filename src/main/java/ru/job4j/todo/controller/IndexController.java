package ru.job4j.todo.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Для перехода с индексной страницы на tasks
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Controller
@ThreadSafe
public class IndexController {
    @GetMapping("/index")
    public String index() {
        return "redirect:/tasks";
    }
}
