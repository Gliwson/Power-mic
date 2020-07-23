package pl.power.services.impl;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import pl.power.model.PowerStationRemoteDto;
import pl.power.repository.PowerStationWithRemoteServerRepository;
import pl.power.services.PowerStationRemoteService;

import java.util.List;

@Service
@Lazy
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
