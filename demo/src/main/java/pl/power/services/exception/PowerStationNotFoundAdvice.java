package pl.power.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PowerStationNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(PowerStationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String powerStationsNotFoundAdvice(PowerStationNotFoundException ex) {
        return ex.getMessage();
    }

}
