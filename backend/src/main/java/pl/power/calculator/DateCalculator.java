package pl.power.calculator;

import pl.power.domain.entity.PowerStation;
import pl.power.domain.entity.Task;
import pl.power.exception.MaxOrMinIsNullException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateCalculator {

    private final LocalDate dateTime;

    public DateCalculator(String date) {
        this.dateTime = LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
    }

    public PairCalculator<Long, BigDecimal> subtractPowerLossFromPower(PowerStation powerStation) {
        BigDecimal allDayPower = getPowerWithAllDay(powerStation);
        Long powerStationId = powerStation.getId();
        PairCalculator<Long, BigDecimal> decimalPair = new PairCalculator<>(powerStationId, allDayPower);

        for (Task task : powerStation.getTasks()) {

            LocalDateTime start = task.getStartDate().toLocalDateTime();
            LocalDateTime end = task.getEndDate().toLocalDateTime();
            LocalDate startDate = start.toLocalDate();
            LocalDate endDate = end.toLocalDate();

            BigDecimal sumOfTheHoursOfTheWholeDay = filterDateAndReturnSumOfTheHoursOfTheWholeDay(start, end, startDate, endDate);

            BigDecimal result = totalPowerAsCanProducePowerStation(decimalPair).subtract(producedPowerDuringTheDay(task, sumOfTheHoursOfTheWholeDay));
            result = scaleUpToTwoAfterComma(result);
            decimalPair = new PairCalculator<>(powerStationId, result);
        }
        return decimalPair;
    }

    private BigDecimal filterDateAndReturnSumOfTheHoursOfTheWholeDay(LocalDateTime start, LocalDateTime end, LocalDate startDate, LocalDate endDate) {
        BigDecimal sumOfTheHoursOfTheWholeDay = new BigDecimal(0);
        if (checkIfTheGivenTimeIsBetweenTheStartDateAndEndDate(startDate, endDate)) {

            LocalTime min = ifTheDateStartsOnADifferentDayReturnTimeAtTheBeginningOfTheDayAndIfOnThisDayReturnTheTimeWhenItStarts(start, startDate);
            LocalTime max = ifTheDateEndsOnADifferentDayReturnTimeAtTheEndOfTheDayAndIfOnThisDayReturnTheTimeWhenItEnds(end, endDate);

            int seconds = subtractMaxFromMin(max, min);
            sumOfTheHoursOfTheWholeDay = convertSecondsToHours(seconds);
        }
        return sumOfTheHoursOfTheWholeDay;
    }

    private BigDecimal totalPowerAsCanProducePowerStation(PairCalculator<Long, BigDecimal> decimalPair) {
        return decimalPair.getValue();
    }

    private BigDecimal producedPowerDuringTheDay(Task task, BigDecimal sumOfTheHoursOfTheWholeDay) {
        return task.getPowerLoss().multiply(sumOfTheHoursOfTheWholeDay);
    }

    private boolean checkIfTheGivenTimeIsBetweenTheStartDateAndEndDate(LocalDate startDate, LocalDate endDate) {
        return startDate.compareTo(dateTime) <= 0 && 0 <= endDate.compareTo(dateTime);
    }

    private LocalTime ifTheDateEndsOnADifferentDayReturnTimeAtTheEndOfTheDayAndIfOnThisDayReturnTheTimeWhenItEnds(LocalDateTime end, LocalDate endDate) {
        LocalTime max = null;
        if (endDate.isAfter(dateTime)) {
            max = LocalTime.MAX;
        }
        if (endDate.isEqual(dateTime)) {
            max = LocalTime.of(end.getHour(), end.getMinute());
        }
        return max;
    }

    private LocalTime ifTheDateStartsOnADifferentDayReturnTimeAtTheBeginningOfTheDayAndIfOnThisDayReturnTheTimeWhenItStarts(LocalDateTime start, LocalDate startDate) {
        LocalTime min = null;
        if (startDate.isBefore(dateTime)) {
            min = LocalTime.MIN;
        }
        if (startDate.isEqual(dateTime)) {
            min = LocalTime.of(start.getHour(), start.getMinute());
        }
        return min;
    }

    private BigDecimal convertSecondsToHours(int seconds) {
        BigDecimal hour = new BigDecimal("3600");
        BigDecimal secondsInBigDecimal = new BigDecimal(seconds);
        return secondsInBigDecimal.divide(hour, 2, RoundingMode.HALF_UP);
    }

    private int subtractMaxFromMin(LocalTime max, LocalTime min) {
        if (max == null || min == null) {
            throw new MaxOrMinIsNullException("max=" + max + " " + "min=" + min);
        }
        return (max.toSecondOfDay() - min.toSecondOfDay());
    }

    private BigDecimal getPowerWithAllDay(PowerStation powerStation) {
        return powerStation.getPower().multiply(new BigDecimal(24));
    }

    private BigDecimal scaleUpToTwoAfterComma(BigDecimal result) {
        result = result.setScale(2, RoundingMode.HALF_UP);
        return result;
    }
}
