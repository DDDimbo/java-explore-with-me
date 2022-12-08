package ru.practicum.explorewithme.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Override
    <S extends User> S save(S user);

    @Override
    void deleteById(Long id);

    @Override
    Optional<User> findById(Long id);

    @Override
    Page<User> findAll(Pageable pageable);

    @Override
    boolean existsById(Long id);

    @Query("select u " +
            "from User u " +
            "where u.id in ?1 " +
            "order by u.id")
    List<User> findALlByParam(List<Long> ids, Pageable pageable);
}
