package pl.power.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;


@Getter
@Setter
public class PowerStationRemoteDto {

    private Integer id;

    private List<EventDTO> events;

    private String name;

    private BigDecimal power;

}
