package pl.power.calculator


import pl.power.constant.TaskType
import pl.power.domain.entity.PowerStation
import pl.power.domain.entity.Task
import spock.lang.Specification
import spock.lang.Unroll

import java.sql.Timestamp

@Unroll
class DateCalculatorSpock extends Specification {

    private PowerStation powerStation

    void setup() {
        Task task = new Task()
        task.setId(1L)
        task.setPowerLoss(new BigDecimal(100))
        task.setTaskType(TaskType.AWARIA)
        task.setStartDate(Timestamp.valueOf("2018-01-20 10:00:00"))
        task.setEndDate(Timestamp.valueOf("2018-01-20 11:00:00"))
        Task task1 = new Task()
        task1.setId(2L)
        task1.setPowerLoss(new BigDecimal(100))
        task1.setTaskType(TaskType.AWARIA)
        task1.setStartDate(Timestamp.valueOf("2020-01-01 00:00:00"))
        task1.setEndDate(Timestamp.valueOf("2020-2-01 00:00:00"))
        Task task2 = new Task()
        task2.setId(3L)
        task2.setPowerLoss(new BigDecimal(100))
        task2.setTaskType(TaskType.REMONT)
        task2.setStartDate(Timestamp.valueOf("2019-12-31 23:00:00"))
        task2.setEndDate(Timestamp.valueOf("2020-01-01 00:00:00"))
        Task task3 = new Task()
        task3.setId(4L)
        task3.setPowerLoss(new BigDecimal(100))
        task3.setTaskType(TaskType.AWARIA)
        task3.setStartDate(Timestamp.valueOf("2020-01-25 00:00:00"))
        task3.setEndDate(Timestamp.valueOf("2020-2-01 00:00:00"))

        powerStation = new PowerStation()
        powerStation.setId(1L)
        powerStation.setName("Lublin")
        powerStation.setPower(new BigDecimal(200))
        powerStation.add(task)
        powerStation.add(task1)
        powerStation.add(task2)
        powerStation.add(task3)
    }

    def "should return id power stations"() {
        DateCalculator dateCalculator = new DateCalculator("2020-01-25")

        PairCalculator<Long, BigDecimal> result = dateCalculator.subtractPowerLossFromPower(powerStation)

        expect:
        result.getKey() == 1
    }

    def "should return the values will be the power for the given day"() {
        expect:
        dateCalculator.subtractPowerLossFromPower(powerStation).getValue() == value

        where:
        dateCalculator                   || value
        new DateCalculator("2020-06-26") || 4800
        new DateCalculator("2019-12-31") || 4700
        new DateCalculator("2018-01-20") || 4700
    }

    def "should return the keys will the ID of the power station"() {
        expect:
        dateCalculator.subtractPowerLossFromPower(powerStation).getKey() == key

        where:
        dateCalculator                   || key
        new DateCalculator("2020-06-26") || 1
        new DateCalculator("2019-12-31") || 1
        new DateCalculator("2018-01-20") || 1
    }

}
