package ru.practicum.explorewithme.comment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

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
}
