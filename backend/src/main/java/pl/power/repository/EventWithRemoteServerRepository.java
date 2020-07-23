package pl.power.repository;

import com.alla.getallevents.GetAllEventsRequest;
import com.alla.getallevents.GetAllEventsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import pl.power.config.MySoapClientConfiguration;
import s0314.gettask.GetAllTasksRequest;
import s0314.gettask.GetAllTasksResponse;

@Profile("prod")
@FeignClient(
        name = "calculatorServer",
        url = "http://s0313:9966",
        configuration = MySoapClientConfiguration.class)
public interface EventWithRemoteServerRepository {

    @PostMapping(value = "/ws/", consumes = MediaType.TEXT_XML_VALUE, produces = MediaType.TEXT_XML_VALUE)
    GetAllEventsResponse getPowerStation(GetAllEventsRequest add);

    @PostMapping(value = "/power/ws/", consumes = MediaType.TEXT_XML_VALUE, produces = MediaType.TEXT_XML_VALUE)
    GetAllTasksResponse getEvents(GetAllTasksRequest add);

}

