package pl.power.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import pl.power.constant.TaskType;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@ToString(callSuper = true, exclude = {"powerStation"})
public class Task implements Serializable, EntityInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_power_station")
    @JsonBackReference
    private PowerStation powerStation;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "task_type")
    @GenericField
    private TaskType taskType;

    @Column(name = "power_loss")
    @GenericField
    private BigDecimal powerLoss;

    @Column(name = "start_date")
    @GenericField
    private Timestamp startDate;

    @Column(name = "end_date")
    @GenericField
    private Timestamp endDate;
}
