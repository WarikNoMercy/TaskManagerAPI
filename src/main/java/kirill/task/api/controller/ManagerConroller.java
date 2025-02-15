package kirill.task.api.controller;

import kirill.task.api.model.Task;
import kirill.task.api.model.TaskSetter;
import kirill.task.api.model.User;
import kirill.task.api.service.TaskService;
import kirill.task.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
public class ManagerConroller {

    private TaskService taskService;
    private UserService userService;

    @Autowired
    public ManagerConroller(TaskService taskService, UserService userService){
        this.taskService = taskService;
        this.userService = userService;
    }

    @PostMapping("/create-task")
    //@PreAuthorize("hasAnyRole('ROLE_MANAGER_SENIOR', 'ROLE_CHIEF')")
    public ResponseEntity<String> createTask(@RequestBody Task task, Authentication authentication){
        User currentUser = userService.findByUsername(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException(
           String.format("User '%s' not found", authentication.getName())
        ));
        taskService.addTask(task, currentUser);
        return new ResponseEntity<>("Таска создана", HttpStatus.OK);
    }

    @GetMapping("/tasks")
   // @PreAuthorize("hasAnyRole('ROLE_MANAGER_SENIOR', 'ROLE_MANAGER')")
    public ResponseEntity<List<Task>> getAllTasks(){
        List<Task> tasks = taskService.getTasks();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/workers")
    //@PreAuthorize("hasAnyRole('ROLE_MANAGER_SENIOR','ROLE_MANAGER')")
    public ResponseEntity<List<User>> getALlWorkers(){
        List<User> workeres = userService.getWorkers();
        return new ResponseEntity<>(workeres, HttpStatus.OK);
    }

    @PutMapping("/set-task")
    //@PreAuthorize("hasAnyRole('ROLE_MANAGER_SENIOR','ROLE_MANAGER')")
    public ResponseEntity<String> setWorker(@RequestBody TaskSetter taskSetter){
        if(taskService.getTaskById(taskSetter.getTaskId()).isPresent()){
            taskService.setTask(taskSetter);
            return new ResponseEntity<>("Таска успешно назначена пользователю : " + taskSetter.getWorkerName(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Таска не найдена", HttpStatus.BAD_REQUEST);
        }
    }
}
