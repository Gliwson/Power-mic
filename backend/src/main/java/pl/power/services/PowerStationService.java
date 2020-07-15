package pl.power.services;

import org.springframework.data.domain.Pageable;
import pl.power.model.PowerStationDTO;
import pl.power.services.impl.PairPageable;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Map;

public interface PowerStationService {

    Long save(PowerStationDTO powerStationDTO);

    PairPageable<PowerStationDTO> findAll(Pageable pageable);

    PowerStationDTO findById(Long id);

    PowerStationDTO update(Long id, PowerStationDTO powerStationDTO);

    void delete(Long id);

    Long countEventsByIdPowerStation(Long id, String taskType);

    Map<Long, BigDecimal> getDateAndPower(String date);

    @Transactional
    void addAll();
}
