package ru.practicum.explorewithme.rating;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingKey> {

    @Override
    <S extends Rating> S save(S rating);

    @Override
    boolean existsById(RatingKey ratingKey);

    boolean existsByIdAndLikeDislike(RatingKey ratingKey, Boolean likeDislike);

    @Override
    void deleteById(RatingKey ratingKey);

    long countByIdAndLikeDislikeIsTrue(RatingKey ratingKey);

    long countByIdAndLikeDislikeIsFalse(RatingKey ratingKey);

    @Override
    Page<Rating> findAll(Pageable pageable);

}
