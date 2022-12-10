package ru.practicum.explorewithme.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.markerinterface.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.time.LocalDateTime;

@ToString
@Getter
@Builder
public class CommentDto {

    @Null(groups = Update.class)
    private Long id;

    @NotBlank
    private String text;

    @Null(groups = Update.class)
    private Long writerId;

    @Null(groups = Update.class)
    private EventShortDto event;

    @Null(groups = Update.class)
    private Boolean visited;

    @Null(groups = Update.class)
    private LocalDateTime written;
}
