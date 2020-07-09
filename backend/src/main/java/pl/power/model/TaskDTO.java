package pl.power.model;

import lombok.Data;
import pl.power.constant.TaskType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
public class TaskDTO implements DTOInterface , Serializable {

    private Long id;

    @NotBlank
    private String namePowerStation;

    @NotNull
    private TaskType taskType;

    @NotNull
    private BigDecimal powerLoss;

    @NotNull
    private Timestamp startDate;

    @NotNull
    private Timestamp endDate;
}
