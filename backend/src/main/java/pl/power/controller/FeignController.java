package pl.power.controller;


import com.alla.getallevents.EventXML;
import com.alla.getallevents.GetAllEventsRequest;
import com.alla.getallevents.GetAllEventsResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.power.aspect.LogController;
import pl.power.feignRepository.EventWithRemoteServerRepository;
import pl.power.model.PowerStationRemoteDto;
import pl.power.services.PowerStationRemoteService;
import s0314.gettask.GetAllTasksRequest;
import s0314.gettask.GetAllTasksResponse;
import s0314.gettask.TaskXML;

import java.util.List;

@Profile("prod")
@RestController
@RequestMapping(value = "/api/feign", produces = MediaType.APPLICATION_JSON_VALUE)
public class FeignController {


    private final PowerStationRemoteService remoteService;
    private final EventWithRemoteServerRepository remoteServerRepository;

    public FeignController(PowerStationRemoteService remoteService, EventWithRemoteServerRepository remoteServerRepository) {
        this.remoteService = remoteService;
        this.remoteServerRepository = remoteServerRepository;
    }

    @LogController()
    @GetMapping("/getpowerStations")
    public List<PowerStationRemoteDto> getPowerStationsWithFeign() {
        return remoteService.getAll();
    }

    @LogController()
    @GetMapping("/getpowerStations2")
    public List<EventXML> getEventWithFeign() {
        GetAllEventsRequest request = new GetAllEventsRequest();
        GetAllEventsResponse powerStation = remoteServerRepository.getPowerStation(request);
        return powerStation.getMylist();
    }

    @LogController()
    @GetMapping("/getpower")
    public List<TaskXML> getEventWithFeign3() {
        GetAllTasksRequest request = new GetAllTasksRequest();
        GetAllTasksResponse powerStation = remoteServerRepository.getEvents(request);
        return powerStation.getTasks();
    }

}
