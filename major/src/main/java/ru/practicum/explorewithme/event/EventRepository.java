package ru.practicum.explorewithme.event;

import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.enums.State;
import ru.practicum.explorewithme.event.dto.EventConfirmedRequestDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {

    @Override
    <S extends Event> S save(S event);

    @Override
    void deleteById(Long id);

    @Override
    Optional<Event> findById(Long id);

    @Override
    boolean existsById(Long id);

    boolean existsByInitiator_Id(Long initiatorId);

    boolean existsByIdAndInitiator_Id(Long eventId, Long initiatorId);

    boolean existsByCategory_Id(Long categoryId);

    boolean existsByIdAndState(Long eventId, State state);

    @Modifying
    @Query("update Event e set e.state = ?2 where e.id = ?1")
    void setCancelByInitiator(Long eventId, State cancel);

    List<Event> findAllByInitiator_Id(Long initiatorId, Pageable pageable);

    Set<Event> findAllByIdIn(Set<Long> events);

    @Query("select e " +
            "from Event e " +
            "left outer join Request r on r.eventId=e.id " +
            "where r.id = ?1 and r.requesterId = ?2")
    Optional<Event> findEventByRequestInfo(Long requestId, Long requesterId);


    @Modifying
    @Query("update Event e set e.confirmedRequests = ?2 where e.id = ?1")
    void setNewConfirmedRequests(Long eventId, Long confirmedRequests);

    @Query("select new ru.practicum.explorewithme.event.dto.EventConfirmedRequestDto(e.id, e.participantLimit, count(r.eventId)) " +
            "from Event e " +
            "left outer join Request r on e.id=r.eventId " +
            "where r.status = ?1 " +
            "group by e.id, e.participantLimit " +
            "having e.participantLimit > count(r.eventId) or e.participantLimit = 0")
    List<EventConfirmedRequestDto> findAllByConfirmedRequest(State published);

    @Override
    Page<Event> findAll(Predicate predicate, Pageable pageable);
}
