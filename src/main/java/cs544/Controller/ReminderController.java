package cs544.Controller;

import cs544.Domain.Reminder;
import cs544.Service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reminders")
public class ReminderController {
    private final ReminderService reminderService;

    @Autowired
    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @GetMapping
    public List<Reminder> getAllReminders() {
        return reminderService.getAllReminders();
    }

    @GetMapping("/{id}")
    public Reminder getReminderById(@PathVariable("id") Long reminderId) {
        return reminderService.getReminderById(reminderId);
    }

    @PostMapping("/")
    public Reminder createReminder(@RequestBody Reminder reminder) {
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
