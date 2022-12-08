package ru.practicum.explorewithme.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.enums.Status;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    @Override
    <S extends Request> S save(S entity);

    boolean existsByRequesterIdAndEventId(Long requesterId, Long eventId);

    boolean existsByIdAndRequesterId(Long requestId, Long requesterId);

    boolean existsByIdAndEventId(Long requestId, Long eventId);

    long countByEventIdAndStatus(Long eventId, Status status);

    @Override
    Optional<Request> findById(Long id);

    List<Request> findAllByRequesterId(Long userId);

    List<Request> findAllByEventId(Long eventId);

    @Modifying
    @Query("update Request r set r.status = ?2 where r.id = ?1")
    void setStatusForRequest(Long requestId, Status cancel);

    @Modifying
    @Query("update Request r set r.status = ?3 where r.eventId = ?1 and r.status = ?2")
    void setCancelAfterLimitEnd(Long eventId, Status pending, Status cancel);
}
