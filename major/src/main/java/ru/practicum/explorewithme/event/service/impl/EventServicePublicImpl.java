package ru.practicum.explorewithme.event.service.impl;


import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.client.EndpointHit;
import ru.practicum.explorewithme.client.StatWebClient;
import ru.practicum.explorewithme.client.ViewStats;
import ru.practicum.explorewithme.enums.Sorted;
import ru.practicum.explorewithme.enums.State;
import ru.practicum.explorewithme.event.EventMapper;
import ru.practicum.explorewithme.event.EventRepository;
import ru.practicum.explorewithme.event.QEvent;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.event.service.EventServicePublic;
import ru.practicum.explorewithme.exceptions.EventNotFoundException;
import ru.practicum.explorewithme.utility.FromSizeRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.utility.MyConstants.SERVICE;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventServicePublicImpl implements EventServicePublic {

    private final EventRepository eventRepository;

    private final StatWebClient client;

    @Override
    public List<EventShortDto> findAllByParamForPublic(String text,
                                                       List<Long> categories,
                                                       Boolean paid,
                                                       LocalDateTime rangeStart,
                                                       LocalDateTime rangeEnd,
                                                       Boolean onlyAvailable,
                                                       Integer from,
                                                       Integer size,
                                                       Sorted sorted,
                                                       HttpServletRequest request) {
        createStat(request);
        Pageable pageable;
        if (sorted != null)
            pageable = FromSizeRequest.of(from, size, Sort.by("eventDate").ascending());
        else
            pageable = FromSizeRequest.of(from, size);
        final QEvent event = QEvent.event;
        final BooleanBuilder where = new BooleanBuilder();

        where.and(event.state.eq(State.PUBLISHED));
        if (text != null)
            where.and(event.annotation.likeIgnoreCase(text).or(event.description.likeIgnoreCase(text)));
        if (categories != null)
            where.and(event.category.id.in(categories));
        if (paid != null)
            where.and(event.paid.eq(paid));
        if (rangeStart != null && rangeEnd != null)
            where.and(event.eventDate.between(rangeStart, rangeEnd));
        else
            where.and(event.eventDate.after(LocalDateTime.now()));
        if (onlyAvailable != null)
            where.and(event.participantLimit.subtract(event.confirmedRequests).gt(0));


        if (sorted == null)
            return eventRepository.findAll(where, pageable).stream()
                    .map(temp -> EventMapper.toEventShortDto(temp, getViews(request, false)))
                    .collect(Collectors.toList());
        else if (sorted.equals(Sorted.EVENT_DATE)) {
            return eventRepository.findAll(where, pageable).stream()
                    .map(EventMapper::toEventShortDto)
                    .collect(Collectors.toList());
        } else {
            return eventRepository.findAll(where, pageable).stream()
                    .map(temp -> EventMapper.toEventShortDto(temp, getViews(request, false)))
                    .sorted(Comparator.comparing(EventShortDto::getViews))
                    .collect(Collectors.toList());
        }
    }

    private long getViews(HttpServletRequest request, boolean unique) {
        List<ViewStats> statistics = client.getStats(LocalDateTime.now().minusYears(10), LocalDateTime.now(), Set.of(request.getRequestURI()), unique);
        long views = 0;
        for (ViewStats view : statistics)
            if (request.getRequestURI().equals(view.getUri()) && SERVICE.equals(view.getApp()))
                views = view.getHits();
        return views;
    }

    private void createStat(HttpServletRequest request) {
        client.save(EndpointHit.builder()
                .ip(request.getRemoteAddr())
                .uri(request.getRequestURI())
                .app(SERVICE)
                .timestamp(LocalDateTime.now())
                .build());
    }

    @Override
    public EventFullDto findAllByIdForPublic(Long eventId, HttpServletRequest request) {
        createStat(request);
        if (!eventRepository.existsByIdAndState(eventId, State.PUBLISHED))
            throw new IllegalArgumentException("Событие должно быть опубликовано.");
        return EventMapper.toEventFullDto(
                eventRepository.findById(eventId)
                        .orElseThrow(() -> new EventNotFoundException("События с id = " + eventId + " не найдено.")),
                getViews(request, false)
        );
    }
}
