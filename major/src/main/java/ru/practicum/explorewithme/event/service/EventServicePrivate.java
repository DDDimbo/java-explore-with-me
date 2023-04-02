package ru.practicum.explorewithme.event.service;


import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.NewEventDto;
import ru.practicum.explorewithme.event.dto.UpdateEventRequest;
import ru.practicum.explorewithme.request.ParticipationRequestDto;

import java.util.List;

public interface EventServicePrivate {


    EventFullDto create(Long userId, NewEventDto eventDto);

    EventFullDto change(Long userId, UpdateEventRequest updateEvent);

    EventFullDto cancel(Long userId, Long eventId);

    ParticipationRequestDto confirm(Long userId, Long requestId, Long eventId);

    ParticipationRequestDto reject(Long userId, Long requestId, Long eventId);

    List<EventFullDto> findAllByUser(Long userId, Integer from, Integer size);

    EventFullDto findAllInfoAboutEvent(Long userId, Long eventId);

    List<ParticipationRequestDto> findAllInfoAboutPartInEvent(Long userId, Long eventId);

}
