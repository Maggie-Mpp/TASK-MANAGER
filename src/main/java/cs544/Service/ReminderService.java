package cs544.Service;

import cs544.DAO.ReminderRepository;
import cs544.Domain.Reminder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReminderService {
    private final ReminderRepository reminderRepository;

    @Autowired
    public ReminderService(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public List<Reminder> getAllReminders() {
        return reminderRepository.findAll();
    }

    public Reminder getReminderById(Long reminderId) {
        return reminderRepository.findById(reminderId).orElse(null);
    }

    public Reminder createReminder(Reminder reminder) {
        return reminderRepository.save(reminder);
    }

    public Reminder updateReminder(Long reminderId, Reminder reminder) {
        Reminder existingReminder = reminderRepository.findById(reminderId).orElse(null);
        if (existingReminder != null) {
            existingReminder.setMessage(reminder.getMessage());
            return reminderRepository.save(existingReminder);
        }
        return null;
    }

    public void deleteReminder(Long reminderId) {
        reminderRepository.deleteById(reminderId);
    }
}
