package ru.practicum.explorewithme.rating.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.rating.Rating;
import ru.practicum.explorewithme.rating.service.RatingServiceAdmin;

import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/rating")
public class RatingControllerAdmin {

    public final RatingServiceAdmin ratingServiceAdmin;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Rating> findAllCommentRatings(
            @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(name = "size", defaultValue = "10") @PositiveOrZero Integer size) {
        log.info("Find all likes and dislikes from comments");
        return ratingServiceAdmin.findAllCommentRatings(from, size);
    }

}
