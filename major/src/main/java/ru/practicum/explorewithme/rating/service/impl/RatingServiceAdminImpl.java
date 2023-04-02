package ru.practicum.explorewithme.rating.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.rating.Rating;
import ru.practicum.explorewithme.rating.RatingRepository;
import ru.practicum.explorewithme.rating.service.RatingServiceAdmin;
import ru.practicum.explorewithme.utility.FromSizeRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RatingServiceAdminImpl implements RatingServiceAdmin {

    public final RatingRepository ratingRepository;

    @Override
    public List<Rating> findAllCommentRatings(Integer from, Integer size) {
        Pageable pageable = FromSizeRequest.of(from, size);
        return ratingRepository.findAll(pageable).stream().collect(Collectors.toList());
    }
}
