package pl.power.services.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.power.model.PowerStationRemoteDto;
import pl.power.feignRepository.PowerStationWithRemoteServerRepository;
import pl.power.services.PowerStationRemoteService;

import java.util.List;
@Profile("prod")
@Service
public class PowerStationRemoteServiceImpl implements PowerStationRemoteService {
    private final PowerStationWithRemoteServerRepository remoteServerRepository;

    public PowerStationRemoteServiceImpl(PowerStationWithRemoteServerRepository remoteServerRepository) {
        this.remoteServerRepository = remoteServerRepository;
    }

    @Override
    public List<PowerStationRemoteDto> getAll() {
        return remoteServerRepository.getPowerStation2();
    }
}
