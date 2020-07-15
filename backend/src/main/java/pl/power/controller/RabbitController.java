package pl.power.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.power.aspect.LogController;
import pl.power.services.impl.DefaultPowerStationService;
import pl.power.services.impl.DefaultTaskService;

@RestController
@RequestMapping(value = "/api/rabbit", produces = MediaType.APPLICATION_JSON_VALUE)
public class RabbitController {

    private final DefaultTaskService taskService;
    private final DefaultPowerStationService powerStationService;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RabbitController(DefaultTaskService taskService, DefaultPowerStationService powerStationService, RabbitTemplate rabbitTemplate) {
        this.taskService = taskService;
        this.powerStationService = powerStationService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @LogController
    @GetMapping("/tasks")
    public ResponseEntity<String> getTasksToRabbitMQ() {
        taskService.findAll().forEach(taskDTO -> {
            String sendString = javaToJson(taskDTO);
            rabbitSend(sendString);
        });
        return new ResponseEntity<>("Mailed", HttpStatus.OK);
    }

    @LogController
    @GetMapping("/powerStations")
    public ResponseEntity<String> getPowerStationToRabbitMQ() {
        powerStationService.findAll().forEach(powerStationDTO -> {
            String sendString = javaToJson(powerStationDTO);
            rabbitSend(sendString);
        });
        return new ResponseEntity<>("Mailed", HttpStatus.OK);
    }

    private void rabbitSend(String sendString) {
        rabbitTemplate.convertAndSend("test-mateusz-exchange", "", sendString);
    }

    private <D> String javaToJson(D dto) {
        String sendString = null;
        try {
            sendString = objectMapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return sendString;
    }


}
