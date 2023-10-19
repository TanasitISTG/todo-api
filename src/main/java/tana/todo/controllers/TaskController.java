package tana.todo.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import tana.todo.dtos.CreateUpdateTaskDTO;
import tana.todo.dtos.TaskDTO;
import tana.todo.entities.Task;
import tana.todo.services.TaskService;
import tana.todo.utils.ListMapper;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final ModelMapper modelMapper;
    private final ListMapper listMapper;

    @GetMapping
    public List<TaskDTO> getAllTasksByUser(@RequestHeader("Authorization") String authorizationHeader) {
        List<Task> tasks = taskService.getTasksFromUser(authorizationHeader);
        return listMapper.mapList(tasks, TaskDTO.class, modelMapper);
    }

    @PostMapping
    public TaskDTO createTask(@Valid @RequestBody CreateUpdateTaskDTO task, @RequestHeader("Authorization") String authorizationHeader) {
        Task newTask = taskService.createTask(task, authorizationHeader);
        return modelMapper.map(newTask, TaskDTO.class);
    }

    @PutMapping("/{id}")
    public TaskDTO updateTask(@PathVariable int id, @Valid @RequestBody CreateUpdateTaskDTO task) {
        Task updatedTask = taskService.updateTask(id, task);
        return modelMapper.map(updatedTask, TaskDTO.class);
    }
}
