package ru.practicum.explorewithme.event.service;

import ru.practicum.explorewithme.enums.Sorted;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventServicePublic {

    List<EventShortDto> findAllByParamForPublic(String text,
                                                List<Long> categories,
                                                Boolean paid,
                                                LocalDateTime rangeStart,
                                                LocalDateTime rangeEnd,
                                                Boolean onlyAvailable,
                                                Integer from,
                                                Integer size,
                                                Sorted sort,
                                                HttpServletRequest request);

    EventFullDto findAllByIdForPublic(Long eventId, HttpServletRequest request);
}
