package tana.todo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tana.todo.entities.Task;
import tana.todo.entities.User;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByUser(User user);
}
