package pl.power;

import com.alla.getallevents.GetAllEventsRequest;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.util.ClassUtils;
import org.springframework.ws.client.core.WebServiceTemplate;
import s0314.gettask.GetAllTasksRequest;
import s0314.gettask.GetAllTasksResponse;


@Log4j2
@Disabled
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PowerStationApplicationTests {

    private Jaxb2Marshaller marshaller = new Jaxb2Marshaller();

    @BeforeEach
    public void init() throws Exception {
        marshaller.setPackagesToScan(ClassUtils.getPackageName(GetAllEventsRequest.class),ClassUtils.getPackageName(GetAllTasksRequest.class));
        marshaller.afterPropertiesSet();
    }

//    @Test
//    @Disabled
//    public void testRemoteSendAndReceive() {
//        WebServiceTemplate ws = new WebServiceTemplate(marshaller);
//
//        GetAllEventsRequest request = new GetAllEventsRequest();
//
//        GetAllEventsResponse object = (GetAllEventsResponse) ws.marshalSendAndReceive("http://s0313:" + 9966 + "/ws", request);
//        object.getMylist().forEach(event -> System.out.println(event.toString()));
//    }

    @Test
    public void TestLocalSendAndReceive() {
        WebServiceTemplate ws = new WebServiceTemplate(marshaller);

        GetAllTasksRequest request = new GetAllTasksRequest();

        GetAllTasksResponse object = (GetAllTasksResponse) ws.marshalSendAndReceive("http://s0314:8085/power/ws", request);
        object.getTasks().forEach(event -> System.out.println(event.toString()));
    }


}
