package cs544.Domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String message;

    private Date reminder_date;

    @JsonIgnore
    @ManyToOne
    private Task task;

    public Reminder(String message, Date dueDate, Task task) {
        this.message = message;
        this.reminder_date = dueDate;
        this.task = task;
    }
}



//    public Reminder() {
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//    public Date getReminder_date() {
//        return reminder_date;
//    }
//
//    public void setReminder_date(Date reminder_date) {
//        this.reminder_date = reminder_date;
//    }
//
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public Task getTask() {
//        return task;
//    }
//
//    public void setTask(Task task) {
//        this.task = task;
//    }
//
//}
