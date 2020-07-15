package pl.power.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class EventDTO {

    private Integer id;

    private String eventType;

    private Timestamp startDate;

    private Timestamp endDate;
}
