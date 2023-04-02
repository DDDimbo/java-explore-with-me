package ru.practicum.explorewithme.comment.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import ru.practicum.explorewithme.comment.Comment;
import ru.practicum.explorewithme.comment.QComment;
import ru.practicum.explorewithme.comment.dto.CommentDto;
import ru.practicum.explorewithme.comment.repository.CommentRepositoryCustom;
import ru.practicum.explorewithme.rating.QRating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<CommentDto> findAllCommentsWithFullInfoByUserId(Long userId) {
        final JPAQuery<Comment> query = new JPAQuery<>(em);
        final QComment comment = QComment.comment;
        final QRating rating = QRating.rating;
        final BooleanBuilder where = new BooleanBuilder();
        where.and(comment.writerId.eq(userId));

        NumberPath<Long> likes = Expressions.numberPath(Long.class, "likes");
        NumberPath<Long> dislikes = Expressions.numberPath(Long.class, "dislikes");

        NumberExpression<Long> likesExpression = rating.likeDislike.eq(true).count();
        NumberExpression<Long> dislikesExpression = rating.likeDislike.eq(false).count();

        return query.select(Projections.constructor(
                        CommentDto.class,
                        comment.id,
                        comment.text,
                        comment.writerId,
                        comment.event,
                        comment.visited,
                        comment.written,
                        likesExpression,
                        dislikesExpression))
                .from(comment)
                .leftJoin(rating).on(comment.id.eq(rating.commentId))
                .where(where)
                .groupBy(comment.id, comment.text, comment.writerId, comment.event, comment.visited, comment.written)
                .fetch();
    }
}
