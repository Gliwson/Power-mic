package pl.power.repository;

import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import pl.power.model.PowerStationRemoteDto;

import java.util.List;

@FeignClient(name = "S0313", url = "http://S0313:9966/api/v1/stations/")
public interface PowerStationWithRemoteServerRepository {

    String AUTH_TOKEN = "Authorization";

    @GetMapping
    @Headers("Content-Type: application/json")
    List<PowerStationRemoteDto> getPowerStation(@RequestHeader(AUTH_TOKEN) String bearerToken);

    @GetMapping
    @Headers("Content-Type: application/json")
    List<PowerStationRemoteDto> getPowerStation2();

}
