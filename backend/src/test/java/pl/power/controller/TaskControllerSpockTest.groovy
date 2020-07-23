package pl.power.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.context.annotation.Primary
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import pl.power.TestSecConfig
import pl.power.constant.TaskType
import pl.power.model.TaskDTO
import pl.power.services.TaskService
import spock.lang.Specification
import spock.lang.Unroll
import spock.mock.DetachedMockFactory

import java.sql.Timestamp

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@Unroll
@AutoConfigureMockMvc()
@WebMvcTest(controllers = [TaskController])
//@SpringBootTest()
@ActiveProfiles("dev")
@Import(value = TestSecConfig.class)
@TestPropertySource("classpath:application-dev.properties")
class TaskControllerSpockTest extends Specification {

    protected MockMvc mvc

    @Autowired
    TaskService taskService;

    @Autowired
    ObjectMapper mapper


    def "gef'"() {
        given:
        def taskDto = new TaskDTO()
        taskDto.setId(1L)
        taskDto.setNamePowerStation('Lublin 2020')
        taskDto.setPowerLoss(new BigDecimal(200))
        taskDto.setStartDate(Timestamp.valueOf('2018-01-20 10:00:00'))
        taskDto.setEndDate(Timestamp.valueOf('2018-01-20 10:00:00'))
        taskDto.setTaskType(TaskType.AWARIA)

        Map request = [
                id              : 1L,
                namePowerStation: 'Lublin 2020',
                taskType        : 'AWARIA',
                powerLoss       : 200,
                startDate       : '2018-01-20 10:00:00',
                endDate         : '2018-01-20 10:00:00'

        ]
        def ev1JsonString = mapper.writeValueAsString(request)

//        and:
//        1 * taskService.findById(2L) >> taskDto

        expect:
        mvc.perform(get('/api/tasks/2'))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString == ev1JsonString


    }

    def "when get is performed then the response has status 200 and content is 'Hello world!'"() {
        expect: "Status is 200 and the response is 'Hello world!'"
        mvc.perform(get("/api/tasks/hello"))
                .andExpect(status().isOk())
                .andReturn()
                .response
                .contentAsString == "Hello world!"
    }

    def "GetTaskById"() {
    }

    def "CreateTask"() {
    }

    def "DeleteTaskById"() {
    }

    def "UpdateTask"() {
    }

    def "GetNumberOfEvents"() {
    }

    @TestConfiguration
    static class StubConfig {


        DetachedMockFactory detachedMockFactory = new DetachedMockFactory()

        @Bean
        @Primary
        TaskService registrationService() {
            return detachedMockFactory.Stub(TaskService)
        }
    }
}
