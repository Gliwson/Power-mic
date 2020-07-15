package pl.power.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.power.aspect.LogController;
import pl.power.model.PowerStationRemoteDto;
import pl.power.services.PowerStationRemoteService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/feign", produces = MediaType.APPLICATION_JSON_VALUE)
public class FeignController {

    Logger log = LoggerFactory.getLogger(FeignController.class);

    private final PowerStationRemoteService remoteService;

    public FeignController(PowerStationRemoteService remoteService) {
        this.remoteService = remoteService;
    }

    @LogController()
    @GetMapping("/getpowerStations")
    public List<PowerStationRemoteDto> getPowerStationsWithFeign() {
        log.info("method: getPowerStationsWithFeign feign send");
        return remoteService.getAll();
    }

}
