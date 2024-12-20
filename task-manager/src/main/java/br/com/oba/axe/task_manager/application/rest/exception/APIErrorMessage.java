package br.com.oba.axe.task_manager.application.rest.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
public class APIErrorMessage {

    private HttpStatus status;
    private List<String> errors;

    public APIErrorMessage(HttpStatus status, List<String> errors) {
        super();
        this.status = status;
        this.errors = errors;
    }

    public APIErrorMessage(HttpStatus status, String error) {
        super();
        this.status = status;
        errors = List.of(error);
    }

}
