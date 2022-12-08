package ru.practicum.explorewithme.exceptions;


public class CustomValidationException extends RuntimeException {

    public CustomValidationException(String message) {
        super(message);
    }
}

