package ru.practicum.explorewithme.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentUpdateDto {

    @NotBlank
    private String text;

}
