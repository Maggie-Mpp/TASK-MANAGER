package cs544.DAO;

import cs544.Domain.Task;
import cs544.Other.Category;
import cs544.Other.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCategoryAndUserId(Category category,Long userId);

    List<Task> findByDueDate(Date dueDate);

    List<Task> findByPriority(Priority priority);
    List<Task> findByUserId(Long id);
}
