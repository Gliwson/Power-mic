package pl.power.controller;

import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pl.power.aspect.LogController;
import pl.power.model.PowerStationDTO;
import pl.power.services.PowerStationService;
import pl.power.services.impl.PairPageable;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = "/api/powerstations", produces = MediaType.APPLICATION_JSON_VALUE)
public class PowerStationController {

    private static final Logger log = getLogger(PowerStationController.class);

    private final PowerStationService powerStationService;

    public PowerStationController(PowerStationService powerStationService) {
        this.powerStationService = powerStationService;
    }

    //    @RolesAllowed({"ROLE_USER","ROLE_ADMIN"})
    @LogController
    @GetMapping
    public Page<PowerStationDTO> findAll(@RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "size", defaultValue = "20") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        PairPageable<PowerStationDTO> pairPageable = powerStationService.findAll(pageRequest);
        log.info("method: getPowerStations send");

        return new PageImpl<>(pairPageable.getElements(), pageRequest, pairPageable.getTotalElements());
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @LogController
    @GetMapping("/{id}")
    public PowerStationDTO findById(@PathVariable("id") Long id) {
        return powerStationService.findById(id);
    }

    @Secured("ROLE_ADMIN")
    @LogController
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Long create(@Valid @RequestBody PowerStationDTO powerStationDTO) {
        return powerStationService.save(powerStationDTO);
    }

    @Secured("ROLE_ADMIN")
    @LogController
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        powerStationService.delete(id);
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @LogController
    @PatchMapping("/{id}")
    public PowerStationDTO update(@PathVariable Long id, @Valid @RequestBody PowerStationDTO powerStationDTO) {
        return powerStationService.update(id, powerStationDTO);
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @LogController
    @GetMapping("/{id}/{taskType}")
    public Long getNumberOfEvents(@PathVariable Long id, @PathVariable String taskType) {
        return powerStationService.countEventsByIdPowerStation(id, taskType);
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @LogController
    @GetMapping("/")
    public Map<Long, BigDecimal> getIdAndPowerForTheGivenDay(@RequestParam(value = "date") String date) {
        return powerStationService.getIdAndPowerForTheGivenDay(date);
    }

    @RolesAllowed({"ROLE_USER", "ROLE_ADMIN"})
    @LogController
    @GetMapping("/addAll")
    @ResponseStatus(value = HttpStatus.CREATED)
    public void importTasks() {
        powerStationService.addAll();
    }

}
