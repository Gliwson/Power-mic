package pl.power.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
//@WebMvcTest([TaskController.class])
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("dev")
class TaskControllerSpockTest extends Specification {

    @Autowired
    private MockMvc mockMvc
    def "when get is performed then the response has status 200 and content is 'Hello world!'"() {
        expect:
        mockMvc.perform(get("/power/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
    }

    def "GetTasks"() {
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
}
