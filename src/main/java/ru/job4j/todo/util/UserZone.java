package ru.job4j.todo.util;

import org.springframework.ui.Model;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * Служебный класс.
 *
 * @author Svistunov Mikhail
 * @version 1.0
 */
public final class UserZone {

    private UserZone() {
    }

    /**
     * Загружает все доступные часовые зоны
     * и добавляет в модель атрибут "timezones".
     *
     * @param model Model
     */
    public static void findAll(Model model) {
        ArrayList<TimeZone> zones = new ArrayList<>();
        for (String timeId : TimeZone.getAvailableIDs()) {
            zones.add(TimeZone.getTimeZone(timeId));
        }
        model.addAttribute("timezones", zones);
    }

    /**
     * Метод идет по списку задач
     * и устанавливает им время с учетом часового пояса пользователя.
     * Если у пользователя не указан часовой пояс, то используется зона по умолчанию.
     *
     * @param user     пользователь
     * @param taskList список задач
     */
    public static void setTimeZone(User user, List<Task> taskList) {
        for (Task task : taskList) {
            if (user.getZone() == null) {
                user.setZone(TimeZone.getDefault().getID());
            }
            task.setCreated(LocalDateTime.from(
                    task.getCreated()
                            .atZone(TimeZone.getDefault().toZoneId())
                            .withZoneSameInstant(ZoneId.of(user.getZone()))));
        }
    }
}
