package pl.power.services;

import org.springframework.data.domain.Pageable;
import pl.power.model.CreateTaskDTO;
import pl.power.model.TaskDTO;
import pl.power.services.impl.PairPageable;

public interface TaskService {

    Long add(CreateTaskDTO createTaskDTO);

    void delete(Long id);

    PairPageable<TaskDTO> findAll(Pageable pageable);

    TaskDTO findById(Long id);

    Long countEventsByIdPowerStation(Long id, String taskType);

    TaskDTO update(CreateTaskDTO createTaskDTO);
}
