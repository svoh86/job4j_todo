package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

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

    /**
     * Показывает основную страницу со всеми задачами
     *
     * @param model Model
     * @return task
     */
    @GetMapping
    public String task(Model model) {
        model.addAttribute("tasks", service.findAll());
        return "tasks";
    }

    /**
     * Показывает страницу с формой добавления задачи
     *
     * @param model Model
     * @return add
     */
    @GetMapping("/add")
    public String addTask(Model model) {
        return "add";
    }

    /**
     * Добавляет данные из формы в БД
     *
     * @param task задача
     * @return redirect:/tasks/all
     */
    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task) {
        task.setCreated(LocalDateTime.now());
        service.add(task);
        return "redirect:/tasks/all";
    }

    /**
     * Показывает страницу со всеми задачами
     *
     * @param model Model
     * @return all
     */
    @GetMapping("/all")
    public String allTasks(Model model) {
        model.addAttribute("all", service.findAll());
        return "all";
    }

    /**
     * Показывает страницу с выполненными заданиями
     *
     * @param model Model
     * @return done
     */
    @GetMapping("/done")
    public String doneTasks(Model model) {
        model.addAttribute("done", service.findByDone(true));
        return "done";
    }

    /**
     * Показывает страницу с новыми заданиями
     *
     * @param model Model
     * @return new
     */
    @GetMapping("/new")
    public String newTasks(Model model) {
        model.addAttribute("newTasks", service.findByDone(false));
        return "new";
    }

    /**
     * Показывает страницу с конкретной задачей
     *
     * @param model  Model
     * @param taskId id задачи
     * @return update
     */
    @GetMapping("/{taskId}")
    public String updateTask(Model model, @PathVariable("taskId") int taskId) {
        Optional<Task> taskDb = service.findById(taskId);
        if (taskDb.isEmpty()) {
            return "redirect:/tasks";
        }
        model.addAttribute("task", taskDb.get());
        return "update";
    }

    /**
     * Меняет состояние задачи на "Выполнено"
     *
     * @param taskId id задачи
     * @return redirect:/tasks/done
     */
    @GetMapping("/done/{taskId}")
    public String doneTask(@PathVariable("taskId") int taskId) {
        service.changeDone(taskId);
        return "redirect:/tasks/done";
    }

    /**
     * Показывает страницу редактирования описания задачи
     *
     * @param model       Model
     * @param taskId      id задачи
     * @param httpSession HttpSession
     * @return edit
     */
    @GetMapping("/edit/{taskId}")
    public String formUpdateTask(Model model, @PathVariable("taskId") int taskId, HttpSession httpSession) {
        Task task = service.findById(taskId).get();

        model.addAttribute("task", task);
        httpSession.setAttribute("task", task);
        return "edit";
    }

    /**
     * Обновляет задачу данными из формы
     *
     * @param task        задача
     * @param httpSession HttpSession
     * @return redirect:/tasks/all
     */
    @PostMapping("/edit")
    public String editTask(@ModelAttribute Task task, HttpSession httpSession) {
        Task taskSession = (Task) httpSession.getAttribute("task");
        task.setId(taskSession.getId());
        task.setCreated(LocalDateTime.now());
        task.setDone(taskSession.isDone());
        service.update(task);
        return "redirect:/tasks/all";
    }

    /**
     * Удаление конкретной задачи
     *
     * @param taskId id задачи
     * @return redirect:/tasks/all
     */
    @GetMapping("/delete/{taskId}")
    public String deleteTask(@PathVariable("taskId") int taskId) {
        service.delete(taskId);
        return "redirect:/tasks/all";
    }
}
