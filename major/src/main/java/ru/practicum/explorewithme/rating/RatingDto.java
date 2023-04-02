package ru.practicum.explorewithme.rating;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class RatingDto {

//    private Long userId;

    private Long commentId;

    private Boolean likeDislike;
}
