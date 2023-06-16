package cs544.DAO;

import cs544.Domain.Task;
import cs544.Other.Category;
import cs544.Other.Priority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCategory(Category category);

    List<Task> findByDueDate(Date dueDate);

    List<Task> findByPriority(Priority priority);
}
