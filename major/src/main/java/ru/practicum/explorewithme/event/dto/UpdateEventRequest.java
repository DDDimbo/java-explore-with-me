package ru.practicum.explorewithme.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.explorewithme.category.Category;
import ru.practicum.explorewithme.markerinterface.Create;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@ToString
@Getter
@Builder
@Jacksonized
public class UpdateEventRequest {

    @NotBlank
    private String annotation;

    @Positive
    @JsonProperty("category")
    private Long categoryId;

    @NotBlank
    private String description;

    @NotNull
    @FutureOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @Positive
    private Long eventId;

    @NotNull
    private Boolean paid;

    @PositiveOrZero
    private Long participantLimit;

    @NotBlank
    private String title;

}
