package ru.practicum.explorewithme.exceptions.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.explorewithme.error.ApiError;
import ru.practicum.explorewithme.exceptions.*;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {


    @ExceptionHandler({ConstraintViolationException.class, NullPointerException.class,
            HttpMessageNotReadableException.class, CustomValidationException.class, AlreadyExistsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(final RuntimeException e) {
        log.error("Ошибка валидации (400) - {}", e.getMessage(), e);
        return ApiError.builder()
                .errors(e.getStackTrace())
                .message(e.getMessage())
                .reason("The request was incorrect.")
                .status(HttpStatus.BAD_REQUEST.name())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler({RangeTimeException.class, ParticipantLimitEndException.class, DublicateRequestException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiError handleForbiddenException(final RuntimeException e) {
        log.error("Не выполнены условия для совершения операции (403) - {} ", e.getMessage(), e);
        return ApiError.builder()
                .errors(e.getStackTrace())
                .message(e.getMessage())
                .reason("For the requested operation the conditions are not met.")
                .status(HttpStatus.FORBIDDEN.name())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler({UserNotFoundException.class, CategoryNotFoundException.class,
            EventNotFoundException.class, CompilationNotFoundException.class, RequestNotFoundException.class,
            IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final RuntimeException e) {
        log.error("Объект не найден (404) - {}", e.getMessage(), e);
        return ApiError.builder()
                .message(e.getMessage())
                .reason("The required object was not found.")
                .status(HttpStatus.NOT_FOUND.name())
                .timestamp(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictException(final RuntimeException e) {
        log.error("Конфликт данных (409) - {}", e.getMessage(), e);
        return ApiError.builder()
                .errors(e.getStackTrace())
                .message(e.getCause().getMessage())
                .reason("Integrity constraint has been violated")
                .status(HttpStatus.CONFLICT.name())
                .timestamp(LocalDateTime.now())
                .build();
    }


    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleServerException(final RuntimeException e) {
        log.error("Внутренняя ошибка сервера (500) - {} ", e.getMessage(), e);
        return ApiError.builder()
                .errors(e.getStackTrace())
                .message(e.getMessage())
                .reason("Error occurred.")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
