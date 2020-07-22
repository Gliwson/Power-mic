package pl.power.services.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pl.power.calculator.DateCalculator;
import pl.power.calculator.PairCalculator;
import pl.power.domain.entity.PowerStation;
import pl.power.domain.entity.Task;
import pl.power.constant.TaskType;
import pl.power.repository.PowerStationRepository;
import pl.power.repository.TaskRepository;
import pl.power.domain.xml.Capacity;
import pl.power.domain.xml.Event;
import pl.power.domain.xml.NameAndEic;
import pl.power.mapper.MapperInterface;
import pl.power.model.PowerStationDTO;
import pl.power.services.PowerStationService;
import pl.power.exception.IdIsNullException;
import pl.power.exception.PowerStationNotFoundException;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@Service
@CacheConfig(cacheNames = "powerStations")
public class DefaultPowerStationService extends CrudAbstractService<PowerStation, PowerStationDTO> implements PowerStationService {

    private final PowerStationRepository powerStationRepository;
    private final TaskRepository taskRepository;
    private final ImportDataXmlService xmlService;

    public DefaultPowerStationService(JpaRepository<PowerStation, Long> jpaRepository,
                                      MapperInterface<PowerStation, PowerStationDTO> mapper,
                                      PowerStationRepository powerStationRepository,
                                      TaskRepository taskRepository, ImportDataXmlService xmlService) {
        super(jpaRepository, mapper);
        this.powerStationRepository = powerStationRepository;
        this.taskRepository = taskRepository;
        this.xmlService = xmlService;
    }

    @Override
    @Caching(evict = {@CacheEvict(cacheNames = "powerStations", allEntries = true),
            @CacheEvict(cacheNames = "tasks", allEntries = true)})
    @Transactional
    public PowerStationDTO update(Long id, PowerStationDTO powerStationDTO) {
        if (id == null) {
            throw new IdIsNullException();
        }
        powerStationDTO.setId(id);
        PowerStation station = powerStationRepository.findById(id)
                .orElseThrow(() -> new PowerStationNotFoundException(id));
        station.setName(powerStationDTO.getName());
        station.setPower(powerStationDTO.getPower());
        return powerStationDTO;
    }

    @Override
    @Cacheable
    public Long countEventsByIdPowerStation(Long id, String taskType) {
        if (id == null) {
            throw new IdIsNullException();
        }
        TaskType filter = TaskType.mapStringToTaskType(taskType);
        return powerStationRepository.findAllOneSelect().stream()
                .filter(powerStation -> powerStation.getId().equals(id))
                .map(PowerStation::getTasks)
                .flatMap(Collection::stream)
                .filter(task -> task.getTaskType() == filter)
                .count();
    }

    /**
     * a method that takes a date and returns a map in which
     * the keys will be the ID of the power station, and the values ​​will be the power for the given day.
     */
    @Override
    @Cacheable
    public Map<Long, BigDecimal> getDateAndPowerForTheGivenDay(String date) {
        DateCalculator dateCalculator = new DateCalculator(date);
        return powerStationRepository.findAllOneSelect().stream()
                .map(dateCalculator::subtractPowerLossFromPower)
                .collect(Collectors.toMap(PairCalculator::getKey, PairCalculator::getValue));
    }

    @Override
    @Caching(evict = {@CacheEvict(cacheNames = "powerStations", allEntries = true),
            @CacheEvict(cacheNames = "tasks", allEntries = true)})
    @Transactional
    public void addAll() {
        xmlService.getLinkToList().forEach(remitUMM -> {
            PowerStation powerStation = new PowerStation();
            Event event = remitUMM.getUmm().getEvent();
            NameAndEic asset = remitUMM.getUmm().getAffectedAsset();
            Capacity capacity = remitUMM.getUmm().getCapacity();

            if (!powerStationRepository.existsByName(asset.getName())) {
                powerStation.setName(asset.getName());
                powerStation.setPower(capacity.getInstalledCapacity());
                powerStationRepository.save(powerStation);
            }

            if (!taskRepository.existsByStartDateAndEndDate(event.getEventStart(), event.getEventStop())) {
                powerStationRepository.findByName(asset.getName())
                        .ifPresent(powerStation1 -> {
                            Task task = new Task();
                            task.setPowerLoss(capacity.getUnavailableCapacity());
                            task.setStartDate(event.getEventStart());
                            task.setEndDate(event.getEventStop());
                            task.setTaskType(TaskType.AWARIA);
                            powerStation1.add(task);
                        });
            }

        });
    }

    @Override
    @Cacheable
    public PairPageable<PowerStationDTO> findAll(Pageable pageable) {
        return super.findAll(pageable);
    }

    @Override
    @Cacheable
    public PowerStationDTO findById(Long id) {
        return super.findById(id);
    }

    @Override
    @Caching(evict = {@CacheEvict(cacheNames = "powerStations", allEntries = true),
            @CacheEvict(cacheNames = "tasks", allEntries = true)})
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    @CacheEvict(cacheNames = "powerStations", allEntries = true)
    public Long save(PowerStationDTO dto) {
        return super.save(dto);
    }
}
