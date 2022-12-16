package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
public class TaskController {
    private final TaskService service;

    /**
     * Показывает основную страницу со всеми задачами
     *
     * @param model Model
     * @return task
     */
    @GetMapping("/tasks")
    public String task(Model model) {
        model.addAttribute("tasks", service.findAll());
        return "tasks";
    }

    /**
     * Показывает страницу с формой добавления задачи
     *
     * @param model Model
     * @return addTask
     */
    @GetMapping("/addTask")
    public String addTask(Model model) {
        return "addTask";
    }

    /**
     * Добавляет данные из формы в БД
     *
     * @param task задача
     * @return redirect:/allTasks
     */
    @PostMapping("/createTask")
    public String createTask(@ModelAttribute Task task) {
        task.setCreated(LocalDateTime.now());
        service.add(task);
        return "redirect:/allTasks";
    }

    /**
     * Показывает страницу со всеми задачами
     *
     * @param model Model
     * @return allTasks
     */
    @GetMapping("/allTasks")
    public String allTasks(Model model) {
        model.addAttribute("allTasks", service.findAll());
        return "allTasks";
    }

    /**
     * Показывает страницу с выполненными заданиями
     *
     * @param model Model
     * @return doneTasks
     */
    @GetMapping("/doneTasks")
    public String doneTasks(Model model) {
        model.addAttribute("doneTasks", service.findByDone(true));
        return "doneTasks";
    }

    /**
     * Показывает страницу с новыми заданиями
     *
     * @param model Model
     * @return newTasks
     */
    @GetMapping("/newTasks")
    public String newTasks(Model model) {
        model.addAttribute("newTasks", service.findByDone(false));
        return "newTasks";
    }

    /**
     * Показывает страницу с конкретной задачей
     *
     * @param model  Model
     * @param taskId id задачи
     * @return updateTask
     */
    @GetMapping("/tasks/{taskId}")
    public String updateTask(Model model, @PathVariable("taskId") int taskId) {
        Optional<Task> taskDb = service.findById(taskId);
        if (taskDb.isEmpty()) {
            return "redirect:/tasks";
        }
        model.addAttribute("task", taskDb.get());
        return "updateTask";
    }

    /**
     * Меняет состояние задачи на "Выполнено"
     *
     * @param model  Model
     * @param taskId id задачи
     * @return updateTask
     */
    @GetMapping("/doneTask/{taskId}")
    public String doneTask(Model model, @PathVariable("taskId") int taskId) {
        Task task = service.findById(taskId).get();
        if (!task.isDone()) {
            task.setDone(true);
        }
        service.update(task);
        model.addAttribute("task", task);
        return "updateTask";
    }

    /**
     * Показывает страницу редактирования описания задачи
     *
     * @param model       Model
     * @param taskId      id задачи
     * @param httpSession HttpSession
     * @return editTask
     */
    @GetMapping("/editTask/{taskId}")
    public String formUpdateTask(Model model, @PathVariable("taskId") int taskId, HttpSession httpSession) {
        Task task = service.findById(taskId).get();
        model.addAttribute("task", task);
        httpSession.setAttribute("task", task);
        return "editTask";
    }

    /**
     * Обновляет задачу данными из формы
     *
     * @param task        задача
     * @param httpSession HttpSession
     * @return redirect:/allTasks
     */
    @PostMapping("/editTask")
    public String editTask(@ModelAttribute Task task, HttpSession httpSession) {
        Task taskSession = (Task) httpSession.getAttribute("task");
        task.setId(taskSession.getId());
        task.setCreated(LocalDateTime.now());
        task.setDone(taskSession.isDone());
        service.update(task);
        return "redirect:/allTasks";
    }

    /**
     * Удаление конкретной задачи
     *
     * @param taskId id задачи
     * @return redirect:/allTasks
     */
    @GetMapping("/deleteTask/{taskId}")
    public String deleteTask(@PathVariable("taskId") int taskId) {
        service.delete(taskId);
        return "redirect:/allTasks";
    }
}
