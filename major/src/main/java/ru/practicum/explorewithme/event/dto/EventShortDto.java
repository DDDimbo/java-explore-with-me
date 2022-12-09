package ru.practicum.explorewithme.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.practicum.explorewithme.category.CategoryDto;
import ru.practicum.explorewithme.user.dto.UserShortDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@ToString
@Getter
@Builder
public class EventShortDto {

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
    private LocalDateTime eventDate;

    @NotNull
    private UserShortDto initiator;

    @NotNull
    private Boolean paid;

    @NotNull
    private String title;

    @PositiveOrZero
    private Long views;

}