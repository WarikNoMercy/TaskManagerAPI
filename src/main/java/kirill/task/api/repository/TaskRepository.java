package kirill.task.api.repository;

import kirill.task.api.model.Task;
import kirill.task.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByWorker(String username);

    @Query("select t from Task t where t.status = TaskStatus.TODO")
    List<Task> findTodoTasks();
}
