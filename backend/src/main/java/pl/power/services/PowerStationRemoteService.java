package pl.power.services;

import pl.power.model.PowerStationRemoteDto;

import java.util.List;

public interface PowerStationRemoteService {

    List<PowerStationRemoteDto> getAll();
}
