package ru.practicum.explorewithme.rating.service;

import ru.practicum.explorewithme.rating.Rating;

import java.util.List;

public interface RatingServiceAdmin {

    List<Rating> findAllCommentRatings(Integer from, Integer size);
}
