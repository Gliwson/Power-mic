package pl.power.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.power.model.PowerStationDTO;
import pl.power.services.PowerStationService;
import pl.power.services.serviceImpl.PairPageable;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/powerstations", produces = MediaType.APPLICATION_JSON_VALUE)
public class PowerStationController {

    private final PowerStationService powerStationService;

    public PowerStationController(PowerStationService powerStationService) {
        this.powerStationService = powerStationService;
    }

    @RolesAllowed({"ROLE_ADMIN"})
    @GetMapping
    public Page<PowerStationDTO> getPowerStations(@RequestParam(name = "page", defaultValue = "0") int page,
                                                  @RequestParam(name = "size", defaultValue = "20") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        PairPageable<PowerStationDTO> pairPageable = powerStationService.findAll(pageRequest);
        return new PageImpl<>(pairPageable.getElements(), pageRequest, pairPageable.getTotalElements());
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/{id}")
    public PowerStationDTO getPowerStation(@PathVariable("id") Long id) {
        return powerStationService.findById(id);
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Long createPowerStation(@Valid @RequestBody PowerStationDTO powerStationDTO) {
        return powerStationService.save(powerStationDTO);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deletePowerStation(@PathVariable Long id) {
        powerStationService.delete(id);
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @PatchMapping("/{id}")
    public PowerStationDTO updatePowerStation(@PathVariable Long id, @Valid @RequestBody PowerStationDTO powerStationDTO) {
        return powerStationService.update(id, powerStationDTO);
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/{id}/{taskType}")
    public Long getNumberOfEvents(@PathVariable Long id, @PathVariable String taskType) {
        return powerStationService.countEventsByIdPowerStation(id, taskType);
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/")
    public Map<Long, BigDecimal> getDateAndPower(@RequestParam(value = "date") String date) {
        return powerStationService.getDateAndPower(date);
    }

    //    @Secured("ROLE_ADMIN")
    @GetMapping("/addAll")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void addAll() {
        powerStationService.addAll();
    }

}
