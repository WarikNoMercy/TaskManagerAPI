package kirill.task.api.service;

import kirill.task.api.model.Task;
import kirill.task.api.model.TaskSetter;
import kirill.task.api.model.TaskStatus;
import kirill.task.api.repository.TaskRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTests {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    public void taskService_getAllTasks(){
        List<Task> mockTasks = new ArrayList<>();
        Task task1 = new Task();
        Task task2 = new Task();
        mockTasks.add(task1);
        mockTasks.add(task2);

        when(taskRepository.findAll()).thenReturn(mockTasks);

        List<Task> tasks = taskService.getTasks();

        Assertions.assertThat(tasks.size()).isEqualTo(2);
    }

    @Test
    public void taskService_getTaskById(){
        Task task = new Task();

        when(taskRepository.findById(any())).thenReturn(Optional.of(task));

        Optional<Task> taskOptional = taskService.getTaskById(any());

        Assertions.assertThat(taskOptional.isPresent()).isTrue();
    }

    @Test
    public void taskService_setWorker(){
        Task task = new Task();
        TaskSetter taskSetter = new TaskSetter();
        taskSetter.setWorkerName("worker");
        taskSetter.setTaskId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.setWorker(taskSetter);

        Assertions.assertThat(task.getWorker()).isEqualTo("worker");
        Assertions.assertThat(task.getStatus()).isEqualTo(TaskStatus.IN_PROGRESS);

    }

    @Test
    public void taskService_setSubject(){
        Task task = new Task();
        TaskSetter taskSetter = new TaskSetter();
        taskSetter.setSubject("subject");
        taskSetter.setTaskId(1L);

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.setSubject(taskSetter);

        Assertions.assertThat(task.getSubject()).isEqualTo("subject");
    }

    @Test
    public void taskService_getMyTasks(){
        Task task1 = new Task();
        task1.setWorker("worker");
        Task task2 = new Task();
        task2.setWorker("worker");
        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);

        when(taskRepository.findByWorker("worker")).thenReturn(tasks);

        List<Task> myTasks = taskService.getMyTasks("worker");

        Assertions.assertThat(myTasks.size()).isEqualTo(2);

    }

    @Test
    public void taskService_getTodoTasks(){
        Task task1 = new Task();
        task1.setStatus(TaskStatus.TODO);
        Task task2 = new Task();
        task2.setStatus(TaskStatus.TODO);
        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);

        when(taskRepository.findTodoTasks()).thenReturn(tasks);

        List<Task> todoTasks = taskService.getTodoTasks();

        Assertions.assertThat(todoTasks.size()).isEqualTo(2);

    }
}
