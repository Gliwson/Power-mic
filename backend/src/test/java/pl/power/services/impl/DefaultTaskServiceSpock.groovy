package pl.power.services.impl

import org.modelmapper.ModelMapper
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import pl.power.constant.TaskType
import pl.power.domain.entity.PowerStation
import pl.power.domain.entity.Task
import pl.power.exception.IdIsNullException
import pl.power.exception.PowerStationNotFoundException
import pl.power.exception.TaskNotFoundException
import pl.power.mapper.MapperInterface
import pl.power.model.CreateTaskDTO
import pl.power.model.TaskDTO
import pl.power.repository.PowerStationRepository
import pl.power.repository.TaskRepository
import spock.lang.Specification
import spock.lang.Unroll

import java.sql.Timestamp

@Unroll
class DefaultTaskServiceSpock extends Specification {

    Task task
    PowerStation powerStation
    TaskDTO taskDTO
    CreateTaskDTO createTaskDTO
    DefaultTaskService service

    JpaRepository jpaRepository
    MapperInterface<Task, TaskDTO> mapperInterface
    TaskRepository taskRepository
    PowerStationRepository powerStationRepository
    ModelMapper modelMapper
    PageRequest pageable

    List<Task> taskList = new ArrayList<>()
    List<TaskDTO> taskListDTO = new ArrayList<>()

    void setup() {
        jpaRepository = Mock(JpaRepository)
        mapperInterface = Mock()
        taskRepository = Mock(TaskRepository)
        powerStationRepository = Mock(PowerStationRepository)
        modelMapper = Mock(ModelMapper)

        service = new DefaultTaskService(
                jpaRepository,
                mapperInterface,
                taskRepository,
                powerStationRepository,
                modelMapper
        )

        taskDTO = new TaskDTO()
        taskDTO.setId(1L)
        taskDTO.setNamePowerStation("Lublin")
        taskDTO.setPowerLoss(new BigDecimal(100))
        taskDTO.setStartDate(Timestamp.valueOf("2018-01-20 10:00:00"))
        taskDTO.setEndDate(Timestamp.valueOf("2018-01-20 11:00:00"))

        task = new Task()
        task.setId(1L)
        task.setPowerLoss(new BigDecimal(100))
        task.setTaskType(TaskType.AWARIA)
        task.setStartDate(Timestamp.valueOf("2018-01-20 10:00:00"))
        task.setEndDate(Timestamp.valueOf("2018-01-20 11:00:00"))

        Task task1 = new Task()
        task1.setId(2L)
        task1.setPowerLoss(new BigDecimal(100))
        task1.setTaskType(TaskType.AWARIA)
        task1.setStartDate(Timestamp.valueOf("2018-03-20 10:00:00"))
        task1.setEndDate(Timestamp.valueOf("2018-03-20 11:00:00"))

        Task task2 = new Task()
        task2.setId(3L)
        task2.setPowerLoss(new BigDecimal(200))
        task2.setTaskType(TaskType.AWARIA)
        task2.setStartDate(Timestamp.valueOf("2018-02-20 10:00:00"))
        task2.setEndDate(Timestamp.valueOf("2018-02-20 11:00:00"))

        taskList.add(task)
        taskList.add(task1)
        taskList.add(task2)


        powerStation = new PowerStation()
        powerStation.setId(1L)
        powerStation.setName("Lublin")
        powerStation.setPower(new BigDecimal(200))
        powerStation.add(task)
        powerStation.add(task1)
        powerStation.add(task2)

        createTaskDTO = new CreateTaskDTO()
        createTaskDTO.setId(1L)
        createTaskDTO.setPowerLoss(new BigDecimal(100))
        createTaskDTO.setStartDate(Timestamp.valueOf("2018-01-20 10:00:00"))
        createTaskDTO.setEndDate(Timestamp.valueOf("2018-01-20 11:00:00"))


        taskDTO = new TaskDTO()
        taskDTO.setId(1L)
        taskDTO.setPowerLoss(new BigDecimal(100))
        taskDTO.setTaskType(TaskType.AWARIA)
        taskDTO.setStartDate(Timestamp.valueOf("2018-01-20 10:00:00"))
        taskDTO.setEndDate(Timestamp.valueOf("2018-01-20 11:00:00"))
        taskDTO.setNamePowerStation(powerStation.getName())

        TaskDTO taskDTO1 = new TaskDTO()
        taskDTO1.setId(2L)
        taskDTO1.setPowerLoss(new BigDecimal(100))
        taskDTO1.setTaskType(TaskType.AWARIA)
        taskDTO1.setStartDate(Timestamp.valueOf("2018-03-20 10:00:00"))
        taskDTO1.setEndDate(Timestamp.valueOf("2018-03-20 11:00:00"))
        taskDTO1.setNamePowerStation(powerStation.getName())

        TaskDTO task2DTO = new TaskDTO()
        task2DTO.setId(3L)
        task2DTO.setPowerLoss(new BigDecimal(200))
        task2DTO.setTaskType(TaskType.AWARIA)
        task2DTO.setStartDate(Timestamp.valueOf("2018-02-20 10:00:00"))
        task2DTO.setEndDate(Timestamp.valueOf("2018-02-20 11:00:00"))
        task2DTO.setNamePowerStation(powerStation.getName())

        taskListDTO.add(taskDTO)
        taskListDTO.add(taskDTO1)
        taskListDTO.add(task2DTO)
    }

    def "adding a task to the power station and return task ID"() {
        when:
        def result = service.add(createTaskDTO)

        then:
        1 * modelMapper.map(_, _) >> task
        1 * powerStationRepository.findById(1L) >> Optional.of(powerStation)
        1 * powerStationRepository.save(powerStation) >> powerStation
        1 * taskRepository.findLastSaved() >> 1
        notThrown(PowerStationNotFoundException)


        expect:
        result == 1
    }

    def "should throw exception if powerStation is null "() {
        when:
        service.add(createTaskDTO)

        then:
        1 * modelMapper.map(_, _) >> task
        1 * powerStationRepository.findById(1L) >> Optional.ofNullable(null)
        0 * powerStationRepository.save(powerStation) >> powerStation
        0 * taskRepository.findLastSaved() >> 1
        thrown(PowerStationNotFoundException)
    }

    def "should find all TaskDTO by paging"() {
        given:
        pageable = PageRequest.of(0, 3)
        def taskPage = new PageImpl<>(taskList, pageable, taskList.size())

        when:
        def result = service.findAll(pageable)

        then:
        1 * taskRepository.findAll(pageable) >> taskPage
        1 * powerStationRepository.findAllById(_)
        1 * mapperInterface.toDTOs(_) >> taskListDTO

        expect:
        result.totalElements == 3
        result.getElements().get(0).getNamePowerStation() == taskList.get(0).getPowerStation().getName()
        result.getElements().get(0).getPowerLoss() == taskList.get(0).getPowerLoss()
        result.getElements().get(0).getTaskType() == taskList.get(0).getTaskType()
        result.getElements().get(0).getEndDate() == taskList.get(0).getEndDate()
    }

    def "should return the number of events by specifying the id of the power plant and type task"() {
        when:
        def result = service.countEventsByIdPowerStation(1, "AWARIA")

        then:
        1 * taskRepository.findAllOneSelect() >> taskList

        expect:
        result == 3
    }

    def "should throw exception if id is null"() {
        when:
        service.countEventsByIdPowerStation(null, _ as String)

        then:
        thrown(IdIsNullException)
        0 * taskRepository.findAllOneSelect()
    }

    def "should throw exception if not found task"() {
        when:
        service.update(createTaskDTO)

        then:
        1 * taskRepository.findById(createTaskDTO.getId()) >> Optional.ofNullable(null)
        thrown(TaskNotFoundException)
        0 * mapperInterface.toDTO(_)
    }

    def "should update task"() {
        when:
        def result = service.update(createTaskDTO)

        then:
        1 * taskRepository.findById(createTaskDTO.getId()) >> Optional.ofNullable(task)
        notThrown(TaskNotFoundException)
        1 * mapperInterface.toDTO(_) >> taskDTO
        result == taskDTO
    }
}
