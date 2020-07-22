package pl.power.services.impl

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.modelmapper.ModelMapper
import pl.power.constant.TaskType
import pl.power.domain.entity.PowerStation
import pl.power.domain.entity.Task
import pl.power.mapper.MapperInterface
import pl.power.model.CreateTaskDTO
import pl.power.model.TaskDTO
import pl.power.repository.PowerStationRepository
import pl.power.repository.TaskRepository
import spock.lang.Specification
import spock.lang.Unroll

import java.sql.Timestamp

@Unroll
@ExtendWith(MockitoExtension.class)
class DefaultTaskServiceSpock extends Specification {

//    TaskRepository taskRepository = Mock()
    @InjectMocks
    DefaultTaskService service

    @Mock
    private TaskRepository taskRepository

    @Mock
    private PowerStationRepository powerStationRepository

    @Mock
    private ModelMapper modelMapper

    private Task task
    private PowerStation powerStation
    private TaskDTO taskDTO
    private CreateTaskDTO createTaskDTO

    @Mock
    private MapperInterface<Task, TaskDTO> mapperInterface

    @BeforeEach
    void init() {
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

        powerStation = new PowerStation()
        powerStation.setId(1L)
        powerStation.setName("Lublin")
        powerStation.setPower(new BigDecimal(200))
        powerStation.add(task)

        createTaskDTO = new CreateTaskDTO()
        createTaskDTO.setId(1L)
        createTaskDTO.setPowerLoss(new BigDecimal(100))
        createTaskDTO.setStartDate(Timestamp.valueOf("2018-01-20 10:00:00"))
        createTaskDTO.setEndDate(Timestamp.valueOf("2018-01-20 11:00:00"))
    }

    def "adding a task to the power station"() {
        given:
        modelMapper.map(_, _) >> task
        powerStationRepository.findById(_ as Long) >> powerStation
        taskRepository.findLastSaved() >> 1

        when:
        service.add(createTaskDTO)

        then:
        1 * modelMapper._(*_)
        1 * powerStationRepository._(*_)
        1 * taskRepository._(*_)

    }

    def "CountEventsByIdPowerStation"() {
    }

    def "Update"() {
    }

    def "FindAll"() {
    }

    def "FindById"() {
    }

    def "Delete"() {
    }

    def "Save"() {
    }
}
