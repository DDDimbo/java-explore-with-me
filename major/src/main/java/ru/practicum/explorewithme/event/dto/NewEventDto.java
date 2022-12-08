package ru.practicum.explorewithme.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.explorewithme.category.Category;
import ru.practicum.explorewithme.markerinterface.Create;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@Getter
@Builder
@Jacksonized
public class NewEventDto {

    @NotBlank(groups = Create.class)
    private String annotation;

    @JsonProperty("category")
    @Positive(groups = Create.class)
    private Long categoryId;

    @NotBlank(groups = Create.class)
    private String description;

    @NotNull(groups = Create.class)
    @FutureOrPresent(groups = Create.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotNull(groups = Create.class)
    private Boolean paid;

    @PositiveOrZero(groups = Create.class)
    private Long participantLimit;

    @NotNull(groups = Create.class)
    private Boolean requestModeration;

    @NotBlank(groups = Create.class)
    private String title;

    @NotNull(groups = Create.class)
    private Location location;
}
