package pl.power.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import pl.power.constant.TaskType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

@Getter
@Setter
@ToString
@Document(indexName = "taskDocument")
public class TaskDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Field(type = Text)
    private String namePowerStation;

    @Field(type = Text)
    private TaskType taskType;

    @Field(type = Text)
    private BigDecimal powerLoss;

    @Field(type = FieldType.Date, store = true, format = DateFormat.basic_date_time)
    private LocalDateTime startDate;

    @Field(type = FieldType.Date, store = true, format = DateFormat.basic_date_time)
    private LocalDateTime endDate;
}
