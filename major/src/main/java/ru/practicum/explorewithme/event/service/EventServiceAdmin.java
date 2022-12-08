package ru.practicum.explorewithme.event.service;

import ru.practicum.explorewithme.enums.State;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.NewEventDto;

import java.time.LocalDateTime;
import java.util.List;

public interface EventServiceAdmin {

    List<EventFullDto> findAllByParam(List<Long> users,
                                      List<State> states,
                                      List<Long> categories,
                                      LocalDateTime rangeStart,
                                      LocalDateTime rangeEnd,
                                      Integer from,
                                      Integer size);
    EventFullDto update(Long eventId, NewEventDto updateEvent);

    EventFullDto publish(Long eventId);

    EventFullDto reject(Long eventId);
}
