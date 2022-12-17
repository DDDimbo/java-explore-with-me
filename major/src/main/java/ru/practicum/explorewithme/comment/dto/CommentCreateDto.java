package ru.practicum.explorewithme.comment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@ToString
@Getter
@Builder
public class CommentCreateDto {

    @NotBlank
    private String text;

    @NotNull
    @Positive
    private Long eventId;

}
