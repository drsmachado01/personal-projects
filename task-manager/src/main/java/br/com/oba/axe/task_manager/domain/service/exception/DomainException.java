package br.com.oba.axe.task_manager.domain.service.exception;

public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
