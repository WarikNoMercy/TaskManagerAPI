package kirill.task.api.service;

import kirill.task.api.model.Task;
import kirill.task.api.model.TaskSetter;
import kirill.task.api.model.TaskStatus;
import kirill.task.api.model.User;
import kirill.task.api.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }


    public void addTask(Task task, User user) {
        task.setUser(user);
        task.setCreatedBy(user.getUsername());
        task.setStatus(TaskStatus.TODO);
        task.setDateCreated(new Date());
        taskRepository.save(task);
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }


    public Optional<Task> getTaskById(Long id){
        return taskRepository.findById(id);
    }

    public void setWorker(TaskSetter taskSetter) {
        Task task = taskRepository.findById(taskSetter.getTaskId()).get();
        task.setWorker(taskSetter.getWorkerName());
        task.setStatus(TaskStatus.IN_PROGRESS);
        taskRepository.save(task);
    }

    public void setSubject(TaskSetter taskSetter){
        Task task = taskRepository.findById(taskSetter.getTaskId()).get();
        task.setSubject(taskSetter.getSubject());
        taskRepository.save(task);
    }
    public List<Task> getMyTasks(String name) {
        return taskRepository.findByWorker(name);
    }

    public List<Task> getTodoTasks(){
        return taskRepository.findTodoTasks();
    }

    public void setSolve(Task task) {
        task.setStatus(TaskStatus.DONE);
        task.setDateSolved(new Date());
        taskRepository.save(task);
    }
}
