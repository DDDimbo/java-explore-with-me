package ru.practicum.explorewithme.category;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Override
    <S extends Category> S save(S category);

    @Override
    void deleteById(Long id);

    @Override
    Optional<Category> findById(Long id);

    @Override
    Page<Category> findAll(Pageable pageable);

}
