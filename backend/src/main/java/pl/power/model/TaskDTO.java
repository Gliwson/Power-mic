package pl.power.model;

import lombok.Getter;
import lombok.Setter;
import pl.power.constant.TaskType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
public class TaskDTO implements DTOInterface , Serializable {

    private static final long serialVersionUID = 1L;

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

    @Override
    public String toString() {
        return "TaskDTO{" +
                "id=" + id +
                ", namePowerStation='" + namePowerStation + '\'' +
                ", taskType=" + taskType +
                ", powerLoss=" + powerLoss +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
