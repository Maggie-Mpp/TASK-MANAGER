package cs544.Service;

import cs544.DAO.TaskRepository;
import cs544.Domain.Task;
import cs544.Other.Category;
import cs544.Other.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task createTask(Task task) {
        // Set default priority if not provided
        if (task.getPriority() == null) {
            task.setPriority(Priority.MEDIUM);
        }
        return taskRepository.save(task);
    }
    public List<Task> getTasksByCategory(Category categoryName,Long userId) {
        return taskRepository.findByCategoryAndUserId(categoryName,userId);
    }

    public List<Task> getTasksByDueDate(Date dueDate) {
        return taskRepository.findByDueDate(dueDate);
    }

    public List<Task> getTasksByPriority(Priority priority) {
        return taskRepository.findByPriority(priority);
    }



    public Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }
    public List<Task> getTasksByUserId(Long id) {
        return taskRepository.findByUserId(id);
    }


    public Task updateTask(Long taskId, Task task) {
        Task existingTask = taskRepository.findById(taskId).orElse(null);
        if (existingTask != null) {
            existingTask.setTitle(task.getTitle());
            existingTask.setDescription(task.getDescription());
            existingTask.setDescription(task.getDescription());

            return taskRepository.save(existingTask);
        }

        return null;
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}

