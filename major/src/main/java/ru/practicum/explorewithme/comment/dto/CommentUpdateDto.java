package ru.practicum.explorewithme.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ToString
@Getter
@AllArgsConstructor
public class CommentUpdateDto {

    @NotNull
    private String text;

}
