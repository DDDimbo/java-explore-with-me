package ru.practicum.explorewithme.exceptions;

public class DublicateRequestException extends RuntimeException {

    public DublicateRequestException(String message) {
        super(message);
    }
}
