package ru.practicum.explorewithme.statistic.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.statistic.QStat;
import ru.practicum.explorewithme.statistic.Stat;
import ru.practicum.explorewithme.statistic.StatMapper;
import ru.practicum.explorewithme.statistic.StatRepository;
import ru.practicum.explorewithme.statistic.dto.EndpointHit;
import ru.practicum.explorewithme.statistic.dto.ViewStats;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatServiceImpl implements StatService {

    private final StatRepository statsRepository;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void createStat(EndpointHit endpointHit) {
        statsRepository.save(StatMapper.toStat(endpointHit));
    }

    @Override
    public List<ViewStats> findAllByParams(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        System.out.println(start + "   " + end);
        final JPAQuery<Stat> query = new JPAQuery<>(em);
        final QStat stat = QStat.stat;
        final BooleanBuilder where = new BooleanBuilder();
//        where.and(stat.timestamp.between(decodeLocalDateTimeValue(start), decodeLocalDateTimeValue(end)));
        where.and(stat.timestamp.between(start, end));
        if (uris != null)
            where.and(stat.uri.in(uris));

        NumberPath<Long> hits = Expressions.numberPath(Long.class, "hits");
        NumberExpression<Long> views = unique ? stat.ip.countDistinct().as(hits) : stat.ip.count().as(hits);

        return query.select(Projections.constructor(ViewStats.class, stat.app, stat.uri, views))
                .from(stat)
                .where(where)
                .groupBy(stat.app, stat.uri)
                .fetch();
    }

    private LocalDateTime decodeLocalDateTimeValue(String time) {
        return  LocalDateTime.parse(URLDecoder.decode(time, StandardCharsets.UTF_8), formatter);
    }
}
