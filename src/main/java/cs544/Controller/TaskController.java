package cs544.Controller;

import cs544.Domain.Task;
import cs544.Other.Category;
import cs544.Other.Priority;
import cs544.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }
    @GetMapping
    public List<Task> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return tasks;
    }
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<Task>> getTasksByCategory(@PathVariable String categoryName) {
        Category category =Category.WORK;
        switch (categoryName){
            case  "work":category=Category.WORK;
            case  "personal":category=Category.PERSONAL;
            case  "shopping":category=Category.SHOPPING;
            default:category=Category.WORK;

        }
        List<Task> tasks = taskService.getTasksByCategory(category);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/due-date/{dueDate}")
    public ResponseEntity<List<Task>> getTasksByDueDate(@PathVariable Date dueDate) {
        List<Task> tasks = taskService.getTasksByDueDate(dueDate);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable Priority priority) {
        List<Task> tasks = taskService.getTasksByPriority(priority);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable("id") Long taskId) {
        return taskService.getTaskById(taskId);
    }


    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable("id") Long taskId, @RequestBody Task task) {
        return taskService.updateTask(taskId, task);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") Long taskId) {
        taskService.deleteTask(taskId);
    }
}
