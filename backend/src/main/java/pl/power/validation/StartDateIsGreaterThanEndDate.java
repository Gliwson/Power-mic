package pl.power.validation;

import pl.power.exception.*;
import pl.power.model.CreateTaskDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StartDateIsGreaterThanEndDate implements ConstraintValidator<StartDateAndEndDate, CreateTaskDTO> {

    @Override
    public void initialize(StartDateAndEndDate constraint) {
    }

    public boolean isValid(CreateTaskDTO taskDTO, ConstraintValidatorContext context) {
        if (taskDTO.getStartDate() == null || taskDTO.getEndDate() == null) {
            return false;
        }
        if (!taskDTO.getStartDate().toLocalDateTime().isBefore(taskDTO.getEndDate().toLocalDateTime())) {
            throw new StartDateIsAfterDateEndException();
        }
        return true;
    }
}
