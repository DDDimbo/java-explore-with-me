package ru.practicum.explorewithme.statistic;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.statistic.dto.EndpointHit;
import ru.practicum.explorewithme.statistic.dto.ViewStats;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StatMapper {

    public static Stat toStat(EndpointHit endpointHit) {
        return Stat.builder()
                .app(endpointHit.getApp())
                .uri(endpointHit.getUri())
                .ip(endpointHit.getIp())
                .timestamp(endpointHit.getTimestamp())
                .build();
    }

    public static ViewStats toViewStats(Stat stat, Long hits) {
        return new ViewStats(stat.getApp(), stat.getUri(), hits);
    }
}
