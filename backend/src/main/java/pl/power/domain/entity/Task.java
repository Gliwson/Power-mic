package pl.power.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import pl.power.constant.TaskType;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@ToString(callSuper = true, exclude = {"powerStation"})
@Document(indexName = "taskq")
public class Task implements Serializable, EntityInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_power_station")
    @Field(type = FieldType.Object)
    @JsonBackReference
    private PowerStation powerStation;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "task_type")
    @Field(type = Text)
    private TaskType taskType;

    @Column(name = "power_loss")
    @Field(type = Text)
    private BigDecimal powerLoss;

    @Column(name = "start_date")
    @Field(type = FieldType.Date, store = true, format = DateFormat.basic_date_time)
    private Timestamp startDate;

    @Column(name = "end_date")
    @Field(type = FieldType.Date, store = true, format = DateFormat.basic_date_time)
    private Timestamp endDate;
}
