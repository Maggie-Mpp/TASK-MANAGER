package cs544.Controller;

import cs544.DAO.TaskRepository;
import cs544.Domain.Reminder;
import cs544.Domain.Task;
import cs544.Service.ReminderService;
import cs544.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/reminders")
public class ReminderController {
    private final ReminderService reminderService;
    @Autowired
    TaskService taskService;

    @Autowired
    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @GetMapping("/{taskId}")
    public List<Reminder> getAllReminders(@PathVariable Long taskId) {

        return reminderService.getRemindersByTaskId(taskId);
    }

    @GetMapping("/{id}")
    public Reminder getReminderById(@PathVariable("id") Long reminderId) {
        return reminderService.getReminderById(reminderId);
    }

    @PostMapping("/")
    public Reminder createReminder(@RequestBody String message, Date reminder_date,Long taskId) {
        Task task = taskService.getTaskById(taskId);
        Reminder  reminder = new Reminder(message,reminder_date,task);
        return reminderService.createReminder(reminder);
    }

    @PutMapping("/{id}")
    public Reminder updateReminder(@PathVariable("id") Long reminderId, @RequestBody Reminder reminder) {
        return reminderService.updateReminder(reminderId, reminder);
    }

    @DeleteMapping("/{id}")
    public void deleteReminder(@PathVariable("id") Long reminderId) {
        reminderService.deleteReminder(reminderId);
    }
}
