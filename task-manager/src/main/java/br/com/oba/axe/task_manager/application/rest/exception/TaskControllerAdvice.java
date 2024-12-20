package br.com.oba.axe.task_manager.application.rest.exception;

import br.com.oba.axe.task_manager.aspect.annotation.Logifier;
import br.com.oba.axe.task_manager.domain.service.exception.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TaskControllerAdvice {
    @ExceptionHandler(value = { DomainException.class })
    @Logifier
    public ResponseEntity<APIErrorMessage> handleDomainException(DomainException exception) {
        return new ResponseEntity<>(new APIErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
