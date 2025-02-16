package kirill.task.api.controller;

import kirill.task.api.model.Task;
import kirill.task.api.model.TaskSetter;
import kirill.task.api.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PutMapping("/take-task/{id}")
    @PreAuthorize("hasRole('ROLE_EMPLOYEE_SENIOR')")
    public ResponseEntity<String> takeTask(@PathVariable("id") Long id, Authentication authentication){
        Optional<Task> taskOp = taskService.getTaskById(id);
        if(taskOp.isPresent()){
            TaskSetter taskSetter = new TaskSetter(id, authentication.getName());
            taskService.setWorker(taskSetter);
            return new ResponseEntity<>("Вы взяли таску : " + taskOp.get().getSubject(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Таска не найдена", HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/set-solve/{id}")
    public ResponseEntity<String> setSolve(@PathVariable("id") Long id, Authentication authentication){
        Optional<Task> taskOp = taskService.getTaskById(id);
        if(taskOp.isPresent()){
            Task task = taskOp.get();
            if(task.getWorker().equals(authentication.getName())){
                taskService.setSolve(task);
                return new ResponseEntity<>("Статус изменен", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("Это не ваша таска", HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>("Таска не найдена", HttpStatus.NOT_FOUND);
        }
    }
}
