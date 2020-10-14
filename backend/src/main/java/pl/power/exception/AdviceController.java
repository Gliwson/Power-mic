package pl.power.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class AdviceController {

    @ResponseBody
    @ExceptionHandler(IdIsNullException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String idIsNullAdvice(IdIsNullException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String taskNotFoundAdvice(TaskNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(PowerStationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String powerStationsNotFoundAdvice(PowerStationNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(StartDateIsAfterDateEndException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public String startDateIsAfterEndDate(StartDateIsAfterDateEndException ex) {
        return ex.getMessage();
    }

}
