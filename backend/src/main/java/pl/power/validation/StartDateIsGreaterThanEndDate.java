package pl.power.validation;

import pl.power.model.CreateTaskDTO;
import pl.power.services.exception.TaskNotFoundException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StartDateIsGreaterThanEndDate implements ConstraintValidator<StartDateAndEndDate, CreateTaskDTO> {

    public void initialize(StartDateAndEndDate constraint) {
    }

    public boolean isValid(CreateTaskDTO taskDTO, ConstraintValidatorContext context) {
        if (taskDTO.getStartDate() == null || taskDTO.getEndDate() == null) {
            return false;
        }
        if (!taskDTO.getStartDate().toLocalDateTime().isBefore(taskDTO.getEndDate().toLocalDateTime())) {
            // TODO: 2020-06-24 add Exception with advice
            throw new TaskNotFoundException(100L);
        }
        return true;
    }
}
