package pl.power.services.impl;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import pl.power.constant.TaskType;
import pl.power.domain.entity.PowerStation;
import pl.power.domain.entity.Task;
import pl.power.exception.IdIsNullException;
import pl.power.exception.PowerStationNotFoundException;
import pl.power.exception.TaskNotFoundException;
import pl.power.mapper.MapperInterface;
import pl.power.model.CreateTaskDTO;
import pl.power.model.TaskDTO;
import pl.power.repository.PowerStationRepository;
import pl.power.repository.TaskRepository;
import pl.power.services.TaskService;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "tasks")
public class DefaultTaskService extends CrudAbstractService<Task, TaskDTO> implements TaskService {

    private final TaskRepository taskRepository;
    private final PowerStationRepository powerStationRepository;
    private final ModelMapper modelMapper;

    public DefaultTaskService(JpaRepository<Task, Long> jpaRepository, MapperInterface<Task, TaskDTO> mapper,
                              TaskRepository taskRepository, PowerStationRepository powerStationRepository,
                              ModelMapper modelMapper) {
        super(jpaRepository, mapper);
        this.taskRepository = taskRepository;
        this.powerStationRepository = powerStationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @CacheEvict(cacheNames = "tasks", allEntries = true)
    @Transactional
    public Long add(CreateTaskDTO createTaskDTO) {
        Task task = modelMapper.map(createTaskDTO, Task.class);
        Optional<PowerStation> powerStationOptional = powerStationRepository.findById(task.getId());
        PowerStation powerStation = powerStationOptional
                .orElseThrow(() -> new PowerStationNotFoundException(task.getId()));
        task.setId(null);
        powerStation.add(task);
        powerStationRepository.save(powerStation);
        return taskRepository.findLastSaved();
    }

    @Cacheable
    public Long countEventsByIdPowerStation(Long id, String taskType) {
        if (id == null) {
            throw new IdIsNullException();
        }
        TaskType filter = TaskType.mapStringToTaskType(taskType);
        return taskRepository.findAllOneSelect()
                .stream()
                .filter(task -> task.getPowerStation().getId().equals(id))
                .filter(value -> value.getTaskType() == filter)
                .count();
    }

    @Override
    @CacheEvict(cacheNames = "tasks", allEntries = true)
    @Transactional
    public TaskDTO update(CreateTaskDTO createTaskDTO) {
        Task task = taskRepository.findById(createTaskDTO.getId())
                .orElseThrow(() -> new TaskNotFoundException(createTaskDTO.getId()));
        task.setPowerLoss(createTaskDTO.getPowerLoss());
        task.setTaskType(createTaskDTO.getTaskType());
        task.setStartDate(createTaskDTO.getStartDate());
        task.setEndDate(createTaskDTO.getEndDate());
        return mapper.toDTO(task);
    }

    @Override
    public File exportReportPDF() throws IOException {
        List<Task> tasksAll = taskRepository.findAll();
        Set<Long> longList = tasksAll.stream()
                .map(value -> value.getPowerStation().getId())
                .collect(Collectors.toSet());
        powerStationRepository.findAllById(longList);
        List<TaskDTO> taskDTOS = mapper.toDTOs(tasksAll);
        File pdf = File.createTempFile("output.", ".pdf");

        try {
            File file = ResourceUtils.getFile("classpath:report/TasksPdfTemplate.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(taskDTOS);
            Map<String, Object> parameters = new HashMap<>();

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            JRDesignStyle jrDesignStyle = new JRDesignStyle();
            jrDesignStyle.setDefault(true);
            jrDesignStyle.setPdfEncoding("windows-1250");

            jasperPrint.addStyle(jrDesignStyle);
            JasperExportManager.exportReportToPdfStream(jasperPrint, new FileOutputStream(pdf));
        } catch (JRException | IOException e) {
            e.printStackTrace();

        }
        return pdf;
    }

    @Override
    @Cacheable
    public PairPageable<TaskDTO> findAll(Pageable pageable) {
        Page<Task> tasksPage = taskRepository.findAll(pageable);
        long totalElements = tasksPage.getTotalElements();
        List<Task> tasks = tasksPage.toList();
        Set<Long> longList = tasks.stream()
                .map(value -> value.getPowerStation().getId())
                .collect(Collectors.toSet());
        powerStationRepository.findAllById(longList);
        List<TaskDTO> taskDTOS = mapper.toDTOs(tasks);
        return new PairPageable<>(totalElements, taskDTOS);
    }

    @Override
    @Cacheable
    public TaskDTO findById(Long id) {
        return super.findById(id);
    }

    @Override
    @CacheEvict(cacheNames = "tasks", allEntries = true)
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @CacheEvict(cacheNames = "tasks", allEntries = true)
    public Long save(TaskDTO dto) {
        return super.save(dto);
    }
}
