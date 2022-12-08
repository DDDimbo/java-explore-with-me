package ru.practicum.explorewithme.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.explorewithme.category.CategoryDto;
import ru.practicum.explorewithme.enums.State;
import ru.practicum.explorewithme.markerinterface.Create;
import ru.practicum.explorewithme.user.dto.UserShortDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@ToString
@Getter
@Builder
public class EventFullDto {


    @Positive
    private Long id;

    @NotNull
    private String annotation;

    @NotNull
    private CategoryDto category;

    @PositiveOrZero
    private Long confirmedRequests;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

    @NotNull
    private String description;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    @NotNull
    private UserShortDto initiator;

    @NotNull
    private Boolean paid;

    @PositiveOrZero
    private Long participantLimit;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;

    @NotNull
    private State state;

    @NotNull
    private Boolean requestModeration;

    @NotNull
    private String title;

    @NotNull(groups = Create.class)
    private Location location;

    @PositiveOrZero
    private Long views;

}
