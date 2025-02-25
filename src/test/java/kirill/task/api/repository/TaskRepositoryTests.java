package kirill.task.api.repository;

import kirill.task.api.model.Task;
import kirill.task.api.model.TaskStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TaskRepositoryTests {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void taskRepository_findTasksByWorker(){
        Task task1 = new Task();
        task1.setWorker("user1");

        Task task2 = new Task();
        task2.setWorker("user1");

        Task task3 = new Task();
        task3.setWorker("user2");

        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);

        List<Task> tasks = taskRepository.findByWorker("user1");
        List<Task> tasks2 = taskRepository.findByWorker("user3");
        Assertions.assertThat(tasks.size()).isEqualTo(2);
        Assertions.assertThat(tasks2.size()).isEqualTo(0);
    }

    @Test
    public void taskRepository_findAllTodoTasks(){
        Task task1 = new Task();
        task1.setStatus(TaskStatus.TODO);

        Task task2 = new Task();
        task2.setStatus(TaskStatus.TODO);

        Task task3 = new Task();
        task3.setStatus(TaskStatus.IN_PROGRESS);

        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);

        List<Task> tasks = taskRepository.findTodoTasks();

        Assertions.assertThat(tasks.size()).isEqualTo(2);
    }
}
