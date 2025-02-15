package kirill.task.api.controller;

import kirill.task.api.model.Task;
import kirill.task.api.repository.TaskRepository;
import kirill.task.api.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    private TaskService taskService;

    @Autowired
    public EmployeeController(TaskService taskService){
        this.taskService = taskService;
    }

    @GetMapping("/mytasks")
    public ResponseEntity<List<Task>> getMyTasks(Authentication authentication){
        List<Task> myTasks = taskService.getMyTasks(authentication.getName());
        return new ResponseEntity<>(myTasks, HttpStatus.OK);
    }

    @GetMapping("/tasks")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE_SENIOR')")
    public ResponseEntity<List<Task>> getAllTasks(){
        List<Task> todoTasks = taskService.getTodoTasks();
        return new ResponseEntity<>(todoTasks, HttpStatus.OK);
    }
}
