package pl.power.repository;

import com.alla.getallevents.GetAllEventsRequest;
import com.alla.getallevents.GetAllEventsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import s0314.gettask.GetAllTasksRequest;
import s0314.gettask.GetAllTasksResponse;


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

