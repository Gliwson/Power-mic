package pl.power.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.power.model.PowerStationDTO;
import pl.power.services.PowerStationService;
import pl.power.services.impl.PairPageable;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:8089")
@RestController
@RequestMapping("/powerstations")
public class PowerStationController {

    private final PowerStationService powerStationService;

    public PowerStationController(PowerStationService powerStationService) {
        this.powerStationService = powerStationService;
    }

    @GetMapping
    public Page<PowerStationDTO> getPowerStations(@RequestParam(name = "page", defaultValue = "0") int page,
                                                  @RequestParam(name = "size", defaultValue = "20") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        PairPageable<PowerStationDTO> pairPageable = powerStationService.findAll(pageRequest);
        return new PageImpl<>(pairPageable.getElements(), pageRequest, pairPageable.getTotalElements());
    }

    @GetMapping("/{id}")
    public PowerStationDTO getPowerStation(@PathVariable("id") Long id) {
        return powerStationService.findById(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Long createPowerStation(@Valid @RequestBody PowerStationDTO powerStationDTO) {
        return powerStationService.save(powerStationDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletePowerStation(@PathVariable Long id) {
        powerStationService.delete(id);
    }

    @PatchMapping("/{id}")
    public PowerStationDTO updatePowerStation(@PathVariable Long id, @Valid @RequestBody PowerStationDTO powerStationDTO) {
        return powerStationService.update(id, powerStationDTO);
    }

    @GetMapping("/{id}/{taskType}")
    public Long getNumberOfEvents(@PathVariable Long id, @PathVariable String taskType) {
        return powerStationService.countEventsByIdPowerStation(id, taskType);
    }

    @GetMapping("/")
    public Map<Long, BigDecimal> getDateAndPower(@RequestParam(value = "date") String date) {
        return powerStationService.getDateAndPower(date);
    }

    @GetMapping("/addAll")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addAll() {
        powerStationService.addAll();
    }

}
