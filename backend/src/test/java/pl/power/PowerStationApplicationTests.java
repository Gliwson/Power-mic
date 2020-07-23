package pl.power;

import com.alla.getallevents.GetAllEventsRequest;
import com.alla.getallevents.GetAllEventsResponse;
import com.alla.getevent.GetEventByIdRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ClassUtils;
import org.springframework.ws.client.core.WebServiceTemplate;
import s0314.getpowerstations.GetAllPowerStationsRequest;
import s0314.getpowerstations.GetByIdPowerStationsRequest;
import s0314.gettask.GetAllTasksRequest;
import s0314.gettask.GetAllTasksResponse;
import spock.lang.Specification;

@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PowerStationApplicationTests extends Specification {

    @Autowired
    private MockMvc mockMvc;

    private final Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

    @BeforeEach
    public void init() throws Exception {
        marshaller.setPackagesToScan(
                ClassUtils.getPackageName(GetAllEventsRequest.class),
                ClassUtils.getPackageName(GetEventByIdRequest.class),
                ClassUtils.getPackageName(GetAllTasksRequest.class),
                ClassUtils.getPackageName(GetAllPowerStationsRequest.class),
                ClassUtils.getPackageName(GetByIdPowerStationsRequest.class)

        );
        marshaller.afterPropertiesSet();
    }

    @Test
    @Disabled
    void testRemoteSendAndReceive() {
        WebServiceTemplate ws = new WebServiceTemplate(marshaller);
        GetAllEventsRequest request = new GetAllEventsRequest();
        GetAllEventsResponse eventsResponse = (GetAllEventsResponse) ws.marshalSendAndReceive("http://s0313:" + 9966 + "/ws", request);

    }

    @Test
    @Disabled
    void getAllTasksSoapTest() {
        WebServiceTemplate ws = new WebServiceTemplate(marshaller);

        GetAllTasksRequest request = new GetAllTasksRequest();

        GetAllTasksResponse object = (GetAllTasksResponse) ws.marshalSendAndReceive("http://s0314:8085/power/ws", request);
        object.getTasks().forEach(event -> System.out.println(event.toString()));
    }


}
