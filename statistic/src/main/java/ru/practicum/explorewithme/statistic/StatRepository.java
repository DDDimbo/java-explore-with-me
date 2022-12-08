package ru.practicum.explorewithme.statistic;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StatRepository extends JpaRepository<Stat, Long> {

    @Override
    <S extends Stat> S save(S stat);


}
