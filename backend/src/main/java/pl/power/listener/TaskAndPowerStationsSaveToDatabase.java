package pl.power.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pl.power.aspect.LogController;
import pl.power.model.PowerStationDTO;
import pl.power.model.TaskDTO;

@Component
public class TaskAndPowerStationsSaveToDatabase {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @LogController
    @RabbitListener(queues = "app")
    public void getTasks(String json) {
        TaskDTO taskDTO = jsonToJava(json, TaskDTO.class);
        //todo save to database
    }

    @LogController
    @RabbitListener(queues = "app2")
    public void getPowerStations(String json) {
        PowerStationDTO powerStationDTO = jsonToJava(json, PowerStationDTO.class);
        //todo save to database
    }

    private <D> D jsonToJava(String json, Class<D> cls) {
        D result = null;
        try {
            result = objectMapper.readValue(json, cls);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

}
