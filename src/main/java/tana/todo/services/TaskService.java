package tana.todo.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import tana.todo.dtos.CreateUpdateTaskDTO;
import tana.todo.entities.Task;
import tana.todo.entities.User;
import tana.todo.exceptions.TaskNotFoundException;
import tana.todo.repositories.TaskRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public List<Task> getTasksFromUser(String authorizationHeader) {
        User user = userService.getUserByAuthorizationHeader(authorizationHeader);
        return taskRepository.findAllByUser(user);
    }

    public Task createTask(CreateUpdateTaskDTO task, String authorizationHeader) {
        User user = userService.getUserByAuthorizationHeader(authorizationHeader);
        Task newTask = modelMapper.map(task, Task.class);
        newTask.setUser(user);
        return taskRepository.saveAndFlush(newTask);
    }

    public Task updateTask(int taskId, CreateUpdateTaskDTO task) {
        Task existingTask = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("Task with id " + taskId + " not found"));
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setStatus(task.getStatus());
        return taskRepository.saveAndFlush(existingTask);
    }
}
