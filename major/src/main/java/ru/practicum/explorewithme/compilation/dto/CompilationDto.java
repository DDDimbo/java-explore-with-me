package ru.practicum.explorewithme.compilation.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.markerinterface.Create;
import ru.practicum.explorewithme.markerinterface.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.util.Set;

@Builder
@Getter
@ToString
public class CompilationDto {

    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    @Positive(groups = Update.class)
    private Long id;

    @NotNull
    private Boolean pinned;

    @NotBlank
    private String title;

    private Set<EventShortDto> events;

}
