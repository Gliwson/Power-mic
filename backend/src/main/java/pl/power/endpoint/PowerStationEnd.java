package pl.power.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.power.model.PowerStationDTO;
import pl.power.services.PowerStationService;
import s0314.getpowerstations.GetAllPowerStationsRequest;
import s0314.getpowerstations.GetAllPowerStationsResponse;
import s0314.getpowerstations.GetByIdPowerStationsRequest;
import s0314.getpowerstations.GetByIdPowerStationsResponse;

import java.util.List;

@Endpoint
public class PowerStationEnd {

    private final PowerStationService stationService;

    public PowerStationEnd(PowerStationService stationService) {
        this.stationService = stationService;
    }


    @PayloadRoot(namespace = "http://s0314/getPowerStations", localPart = "getAllPowerStationsRequest")
    @ResponsePayload
    public GetAllPowerStationsResponse getStudentById(@RequestPayload GetAllPowerStationsRequest request) {
        List<PowerStationDTO> all = stationService.findAll();

        return null;
    }

    @PayloadRoot(namespace = "http://s0314/getPowerStations", localPart = "getByIdPowerStationsRequest")
    @ResponsePayload
    public GetByIdPowerStationsResponse getStudentById(@RequestPayload GetByIdPowerStationsRequest request) {

        return null;
    }
}
