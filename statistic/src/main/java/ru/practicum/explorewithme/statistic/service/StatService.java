package ru.practicum.explorewithme.statistic.service;

import ru.practicum.explorewithme.statistic.dto.EndpointHit;
import ru.practicum.explorewithme.statistic.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {

    void createStat(EndpointHit endpointHit);

    List<ViewStats> findAllByParams(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
