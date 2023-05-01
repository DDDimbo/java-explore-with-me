package ru.practicum.explorewithme.comment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.markerinterface.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime written;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @PositiveOrZero
    private Long likes;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @PositiveOrZero
    private Long dislikes;

}
