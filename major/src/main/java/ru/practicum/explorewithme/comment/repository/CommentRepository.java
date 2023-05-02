package ru.practicum.explorewithme.comment.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.comment.Comment;
import ru.practicum.explorewithme.comment.dto.CommentDtoView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

    @Override
    <S extends Comment> S save(S comment);

    @Override
    Optional<Comment> findById(Long id);

    List<Comment> findAllById(Long id, Pageable pageable);

    List<Comment> findAllByEvent_Id(Long eventId, Pageable pageable);

    @Override
    boolean existsById(Long id);

    boolean existsByIdAndWriterId(Long id, Long userId);

    @Override
    void deleteById(Long id);

    @Modifying
    @Query("update Comment c set c.text = ?1 where c.id = ?2")
    void setNewText(String newText, Long id);

    @Query("select count(c) > 0 " +
            "from Comment c " +
            "where c.id = ?1 and c.written > ?2")
    boolean timeCheck(Long commentId, LocalDateTime nowMinusHour);


    @Query("select c.id as id, " +
            "c.text as text, " +
            "c.writerId as writerId, " +
            "c.visited as visited, " +
            "c.written as written, " +
            "sum(case when r.likeDislike = true then 1 else 0 end) as likes, " +
            "sum(case when r.likeDislike = false then 1 else 0 end) as dislikes, " +
            "e as event " +
            "from Comment c " +
            "inner join Event e on c.event.id = e.id " +
            "left join Rating r on c.id = r.comment.id " +
            "where c.id = :id " +
            "group by c.id, c.text, c.writerId, c.visited, c.written, e ")
    CommentDtoView findCommentsWithFullInfoById(@Param("id") Long commentId);

    @Query("select c.id as id, " +
            "c.text as text, " +
            "c.writerId as writerId, " +
            "c.visited as visited, " +
            "c.written as written, " +
            "sum(case when r.likeDislike = true then 1 else 0 end) as likes, " +
            "sum(case when r.likeDislike = false then 1 else 0 end) as dislikes, " +
            "e as event " +
            "from Comment c " +
            "inner join Event e on c.event.id = e.id " +
            "left join Rating r on c.id = r.comment.id " +
            "where c.writerId = :user_id " +
            "group by c.id, c.text, c.writerId, c.visited, c.written, e " +
            "order by c.written desc")
    List<CommentDtoView> findAllCommentsWithFullInfoByUserIdJPQL(@Param("user_id") Long useId, Pageable pageable);


//    @Query(value = "select c.id, " +
//            "c.text, " +
//            "c.writer_id as writerId, " +
//            "c.event_id as eventId, " +
//            "c.visited, " +
//            "c.written, " +
//            "count(r.like_dislike) filter (where r.like_dislike = true)  as likes, " +
//            "count(r.like_dislike) filter (where r.like_dislike = false) as dislikes " +
//            "from comments c " +
//            "left outer join ratings r on c.id = r.comment_id " +
//            "where c.writer_id = :id " +
//            "group by c.id, c.text, c.writer_id, c.event_id, c.visited, c.written", nativeQuery = true)
//    List<CommentDtoView> findAllCommentsWithFullInfoByUserIdNative(@Param("id") Long useId);
}
