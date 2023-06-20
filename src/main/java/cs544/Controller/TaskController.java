package cs544.Controller;

import cs544.Domain.Reminder;
import cs544.Domain.Task;
import cs544.Domain.User;
import cs544.Other.Category;
import cs544.Other.Priority;
import cs544.Service.CustomUserDetails;
import cs544.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {

        this.taskService = taskService;
    }
@PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/alltasks")
    public List<Task> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return tasks;
    }
    @GetMapping
    public List<Task> getAllTasks(Authentication authentication) {
        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();
        List<Task> tasks = taskService.getTasksByUserId(userId);
        return tasks;
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<Task>> getTasksByCategory(@PathVariable String categoryName,Authentication authentication) {
        Category category;
        switch (categoryName) {
            case "work":
                category = Category.WORK;
                break;
            case "personal":
                category = Category.PERSONAL;
                break;
            case "shopping":
                category = Category.SHOPPING;
                break;
            default:
                category = Category.WORK;
                break;
        }
        Long userId = ((CustomUserDetails) authentication.getPrincipal()).getId();

        List<Task> tasks = taskService.getTasksByCategory(category,userId);
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



    @PostMapping("/addTask")
    public ResponseEntity<Task> createTask(@RequestBody Task task,Authentication authentication) {
        CustomUserDetails user = ((CustomUserDetails) authentication.getPrincipal());
        User user1 = new User();
        user1.setId(user.getId());
        Set<String> roles = new HashSet<>();
        user.getAuthorities().forEach(aut->roles.add(aut.getAuthority()));
        user1.setRoles(roles);
        user1.setUsername(user.getUsername());
        user1.setPassword(user.getPassword());
        task.setUser(user1);
        Task createdTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    @GetMapping("/{taskId}/reminder")
    public List<Reminder> getAllRemindersByTaskId(@PathVariable Long taskId){
        return taskService.getTaskById(taskId).getReminders();
    }
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable("id") Long taskId, @RequestBody Task task,Authentication authentication) {

        CustomUserDetails user = ((CustomUserDetails) authentication.getPrincipal());
        User user1 = new User();
        user1.setId(user.getId());
        Set<String> roles = new HashSet<>();
        user.getAuthorities().forEach(aut->roles.add(aut.getAuthority()));
        user1.setRoles(roles);
        user1.setUsername(user.getUsername());
        user1.setPassword(user.getPassword());
        task.setUser(user1);
        Task updatedTask = taskService.updateTask(taskId, task);
        return updatedTask;
    }


    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable("id") Long taskId) {
        taskService.deleteTask(taskId);
    }
}
