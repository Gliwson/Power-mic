package pl.power.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles
import pl.power.TestSecConfig
import pl.power.model.TaskDTO
import pl.power.services.TaskService
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@Unroll
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ActiveProfiles("dev")
@Import(value = TestSecConfig.class)
class TaskControllerSpockTest extends Specification {

    @Autowired
    private TestRestTemplate restTemplate

    @Autowired
    TaskService taskService

    @Autowired
    ObjectMapper mapper

    def "http_get tasks by id'"() {
        when:
        def result = restTemplate.getForEntity('/api/tasks/2',TaskDTO)

        then:
        result.statusCode == HttpStatus.OK
        result.body.namePowerStation == 'Pątnów 1 B1'

    }
}
