package ru.practicum.explorewithme.comment.utility;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.exceptions.CustomValidationException;
import ru.practicum.explorewithme.utility.FromSizeRequest;

import static ru.practicum.explorewithme.enums.Order.*;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentSort {

    public static Pageable sortByTimeAndRating(Integer from, Integer size, String sortOrder) {
        if (sortOrder.equalsIgnoreCase(TIME_ASC.getOrder()))
            return FromSizeRequest.of(from, size, Sort.by("written").ascending());
        else if (sortOrder.equalsIgnoreCase(TIME_DESC.getOrder()))
            return FromSizeRequest.of(from, size, Sort.by("written").descending());
        else if (sortOrder.equalsIgnoreCase(LIKES_ASC.getOrder()))
            return FromSizeRequest.of(from, size, Sort.by("likes").ascending());
        else if (sortOrder.equalsIgnoreCase(LIKES_DESC.getOrder()))
            return FromSizeRequest.of(from, size, Sort.by("likes").descending());
        else if (sortOrder.equalsIgnoreCase(DISLIKES_ASC.getOrder()))
            return FromSizeRequest.of(from, size, Sort.by("dislikes").ascending());
        else if (sortOrder.equalsIgnoreCase(DISLIKES_DESC.getOrder()))
            return FromSizeRequest.of(from, size, Sort.by("dislikes").descending());
        else
            throw new CustomValidationException("Значение сортировки заданно не верно.");
    }
}
