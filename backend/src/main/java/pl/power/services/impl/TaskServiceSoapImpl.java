package pl.power.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.power.repository.TaskRepository;
import s0314.gettask.TaskXML;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceSoapImpl {

    private final ModelMapper mapper;
    private final TaskRepository repository;

    public TaskServiceSoapImpl(ModelMapper mapper, TaskRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Transactional
    public List<TaskXML> getAll() {
        List<pl.power.domain.entity.Task> all = repository.findAll();
        return all.stream()
                .map(task -> {
                    TaskXML mapTask = mapper.map(task, TaskXML.class);
                    mapTask.setNamePowerStation(task.getPowerStation().getName());
                    return mapTask;
                }).collect(Collectors.toList());

    }
}
