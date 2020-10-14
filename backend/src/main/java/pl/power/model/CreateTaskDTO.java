package pl.power.model;

import lombok.Data;
import pl.power.constant.TaskType;
import pl.power.validation.StartDateAndEndDate;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@StartDateAndEndDate
public class CreateTaskDTO implements DTOInterface {

    @NotNull
    private Long id;

    @NotNull
    private TaskType taskType;

    @NotNull(message = "The power loss field is empty")
    private BigDecimal powerLoss;

    @NotNull
    private Timestamp startDate;

    @NotNull
    private Timestamp endDate;
}
