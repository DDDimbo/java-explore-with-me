package ru.practicum.explorewithme.compilation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.event.Event;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    @Override
    <S extends Compilation> S save(S compilation);

    @Override
    boolean existsById(Long id);

    boolean existsByIdAndPinned(Long id, Boolean pinned);

    @Override
    void deleteById(Long id);

    @Query("select c.events " +
            "from Compilation c " +
            "where c.id = ?1")
    Optional<Set<Event>> findEventsById(Long id);

    @Modifying
    @Query("update Compilation c set c.pinned = ?1 where c.id = ?2")
    void setPinned(Boolean pinned, Long compilationId);

    @Override
    List<Compilation> findAll();

    List<Compilation> findAllByPinned(Boolean pinned, Pageable pageable);

}