package pl.power.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.power.model.CreateTaskDTO;
import pl.power.model.TaskDTO;
import pl.power.services.TaskService;
import pl.power.services.impl.PairPageable;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @CrossOrigin(origins = "http://localhost:8089")
    @GetMapping
    public Page<TaskDTO> getTasks(@RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "20") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        PairPageable<TaskDTO> pairPageable = taskService.findAll(pageable);
        return new PageImpl<>(pairPageable.getElements(), pageable, pairPageable.getTotalElements());
    }

    @GetMapping("/{id}")
    public TaskDTO getTaskById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Long createTask(@Valid @RequestBody CreateTaskDTO createTaskDTO) {
        return taskService.add(createTaskDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteTaskById(@PathVariable Long id) {
        taskService.delete(id);
    }

    @PatchMapping("/")
    public TaskDTO updateTask(@Valid @RequestBody CreateTaskDTO createTaskDTO) {
        return taskService.update(createTaskDTO);
    }

    @GetMapping("/{id}/{taskType}")
    public Long getNumberOfEvents(@PathVariable Long id, @PathVariable String taskType) {
        return taskService.countEventsByIdPowerStation(id, taskType);
    }

}
