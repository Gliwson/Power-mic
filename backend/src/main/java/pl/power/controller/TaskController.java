package pl.power.controller;

import org.modelmapper.ModelMapper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.power.aspect.LogController;
import pl.power.domain.entity.TaskDocument;
import pl.power.elasticRepository.TaskDocumentRepository;
import pl.power.model.CreateTaskDTO;
import pl.power.model.TaskDTO;
import pl.power.services.TaskService;
import pl.power.services.impl.PairPageable;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
public class TaskController {

    private final TaskService taskService;
    private final TaskDocumentRepository taskDocumentRepository;
    private final ModelMapper mapper;

    public TaskController(TaskService taskService, TaskDocumentRepository taskDocumentRepository, ModelMapper mapper) {
        this.taskService = taskService;
        this.taskDocumentRepository = taskDocumentRepository;
        this.mapper = mapper;
    }

    @GetMapping("/hello")
    public String salutation() {
        return "Hello world!";
    }

    //    @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    @LogController
    @GetMapping
    public Page<TaskDTO> findAll(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                 @RequestParam(name = "size", defaultValue = "22", required = false) int size
    ) {
        PageRequest pageable = PageRequest.of(page, size);
        PairPageable<TaskDTO> pairPageable = taskService.findAll(pageable);
        return new PageImpl<>(pairPageable.getElements(), pageable, pairPageable.getTotalElements());
    }

    //    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @LogController
    @GetMapping("/{id}")
    public TaskDTO findById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @Secured("ROLE_ADMIN")
    @LogController
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Long create(@Valid @RequestBody CreateTaskDTO createTaskDTO) {
        return taskService.add(createTaskDTO);
    }

    @Secured("ROLE_ADMIN")
    @LogController
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        taskService.delete(id);
    }

    @Secured("ROLE_ADMIN")
    @LogController
    @PatchMapping("/")
    public TaskDTO update(@Valid @RequestBody CreateTaskDTO createTaskDTO) {
        return taskService.update(createTaskDTO);
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @LogController
    @GetMapping("/{id}/{taskType}")
    public Long getNumberOfEvents(@PathVariable Long id, @PathVariable String taskType) {
        return taskService.countEventsByIdPowerStation(id, taskType);
    }

    @GetMapping("/search/{name}")
    public List<TaskDocument> getByName(@PathVariable String name) {
        return taskDocumentRepository.findByNamePowerStationLike(name);
    }

    @GetMapping("/add")
    public void add() {
        List<TaskDTO> all = taskService.findAll();
        all.forEach(taskDTO -> {
            TaskDocument map = mapper.map(taskDTO, TaskDocument.class);
            taskDocumentRepository.save(map);
        });
    }

    @GetMapping("/pdf")
    public ResponseEntity<InputStreamResource> getPdf() {
        File file;
        long length = 0;
        FileInputStream inputStream = null;
        try {
            file = taskService.exportReportPDF();
            length = file.length();
            inputStream = new FileInputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (inputStream == null) {
            return ResponseEntity
                    .notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(length);
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(inputStream));
    }
}
