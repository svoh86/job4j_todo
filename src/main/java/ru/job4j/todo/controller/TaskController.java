package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.SimplePriorityService;
import ru.job4j.todo.service.TaskService;
import ru.job4j.todo.util.UserSession;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Контроллер задач
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
@Controller
@ThreadSafe
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService service;
    private final SimplePriorityService priorityService;

    /**
     * Показывает основную страницу со всеми задачами
     *
     * @param model Model
     * @return tasks/tasks
     */
    @GetMapping
    public String tasks(Model model, HttpSession httpSession) {
        UserSession.getUser(model, httpSession);
        model.addAttribute("tasks", service.findAll());
        return "tasks/tasks";
    }

    /**
     * Показывает страницу с формой добавления задачи
     *
     * @return tasks/add
     */
    @GetMapping("/add")
    public String addTask(Model model, HttpSession httpSession) {
        UserSession.getUser(model, httpSession);
        model.addAttribute("priorities", priorityService.findAll());
        return "tasks/add";
    }

    /**
     * Добавляет данные из формы в БД
     *
     * @param task задача
     * @return redirect:/tasks/all
     */
    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task, Model model, HttpSession httpSession) {
        User user = UserSession.getUser(model, httpSession);
        task.setCreated(LocalDateTime.now());
        task.setUser(user);
        service.add(task);
        return "redirect:/tasks/all";
    }

    /**
     * Показывает страницу со всеми задачами
     *
     * @param model Model
     * @return tasks/all
     */
    @GetMapping("/all")
    public String allTasks(Model model,
                           @RequestParam(name = "fail", required = false) Boolean fail,
                           HttpSession httpSession) {
        UserSession.getUser(model, httpSession);
        model.addAttribute("all", service.findAll());
        model.addAttribute("fail", fail != null);
        return "tasks/all";
    }

    /**
     * Показывает страницу с выполненными заданиями
     *
     * @param model Model
     * @return tasks/done
     */
    @GetMapping("/done")
    public String doneTasks(Model model, HttpSession httpSession) {
        UserSession.getUser(model, httpSession);
        model.addAttribute("done", service.findByDone(true));
        return "tasks/done";
    }

    /**
     * Показывает страницу с новыми заданиями
     *
     * @param model Model
     * @return tasks/new
     */
    @GetMapping("/new")
    public String newTasks(Model model, HttpSession httpSession) {
        UserSession.getUser(model, httpSession);
        model.addAttribute("newTasks", service.findByDone(false));
        return "tasks/new";
    }

    /**
     * Показывает страницу с конкретной задачей
     *
     * @param model  Model
     * @param taskId id задачи
     * @return tasks/update
     */
    @GetMapping("/{taskId}")
    public String updateTask(Model model, @PathVariable("taskId") int taskId, HttpSession httpSession) {
        UserSession.getUser(model, httpSession);
        Optional<Task> taskDb = service.findById(taskId);
        if (taskDb.isEmpty()) {
            return "redirect:/tasks/all?fail=true";
        }
        model.addAttribute("task", taskDb.get());
        return "tasks/update";
    }

    /**
     * Меняет состояние задачи на "Выполнено"
     *
     * @param taskId id задачи
     * @return redirect:/tasks/done или errorPage
     */
    @GetMapping("/done/{taskId}")
    public String doneTask(@PathVariable("taskId") int taskId, Model model) {
        boolean done = service.changeDone(taskId);
        if (!done) {
            model.addAttribute("message", "Изменения состояния не произошло!");
            return "errorPage";
        }
        return "redirect:/tasks/done";
    }

    /**
     * Показывает страницу редактирования описания задачи
     *
     * @param model       Model
     * @param taskId      id задачи
     * @param httpSession HttpSession
     * @return tasks/edit
     */
    @GetMapping("/edit/{taskId}")
    public String formUpdateTask(Model model, @PathVariable("taskId") int taskId, HttpSession httpSession) {
        UserSession.getUser(model, httpSession);
        Optional<Task> taskDb = service.findById(taskId);
        if (taskDb.isEmpty()) {
            return "redirect:/tasks/all?fail=true";
        }
        model.addAttribute("task", taskDb.get());
        model.addAttribute("priorities", priorityService.findAll());
        httpSession.setAttribute("task", taskDb.get());
        return "tasks/edit";
    }

    /**
     * Обновляет задачу данными из формы
     *
     * @param task        задача
     * @param httpSession HttpSession
     * @return redirect:/tasks/all или errorPage
     */
    @PostMapping("/edit")
    public String editTask(@ModelAttribute Task task, HttpSession httpSession, Model model) {
        Task taskSession = (Task) httpSession.getAttribute("task");
        task.setId(taskSession.getId());
        task.setCreated(LocalDateTime.now());
        task.setDone(taskSession.isDone());
        task.setUser(taskSession.getUser());
        boolean update = service.update(task);
        if (!update) {
            model.addAttribute("message", "Обновление задачи не произошло!");
            return "errorPage";
        }
        return "redirect:/tasks/all";
    }

    /**
     * Удаление конкретной задачи
     *
     * @param taskId id задачи
     * @return redirect:/tasks/all или errorPage
     */
    @GetMapping("/delete/{taskId}")
    public String deleteTask(@PathVariable("taskId") int taskId, Model model) {
        boolean delete = service.delete(taskId);
        if (!delete) {
            model.addAttribute("message", "Удаление задачи не произошло!");
            return "errorPage";
        }
        return "redirect:/tasks/all";
    }
}
