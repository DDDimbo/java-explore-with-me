package ru.practicum.explorewithme.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.enums.Sorted;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.event.service.EventServicePublic;


import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/events")
public class EventControllerPublic {

    private final EventServicePublic eventServicePublic;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> findAllByParamForPublic(@RequestParam(required = false) String text,
                                                       @RequestParam(required = false) List<Long> categories,
                                                       @RequestParam(required = false) Boolean paid,
                                                       @RequestParam(required = false)
                                                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                       LocalDateTime rangeStart,
                                                       @RequestParam(required = false)
                                                       @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                       LocalDateTime rangeEnd,
                                                       @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                                       @RequestParam(defaultValue = "0") Integer from,
                                                       @RequestParam(defaultValue = "10") Integer size,
                                                       @RequestParam(required = false) Sorted sort,
                                                       HttpServletRequest request) {
        log.info("Get events info for public layer. Client ip: {}, endpoint path: {}",
                request.getRemoteAddr(),
                request.getRequestURI());

        return eventServicePublic.findAllByParamForPublic(
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, from, size, sort, request
        );
    }


    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto findAllByIdForPublic(@PathVariable @Positive Long eventId, HttpServletRequest request) {
        log.info("Get event info by eventId={} for public layer. Client ip: {}, endpoint path: {}",
                eventId,
                request.getRemoteAddr(),
                request.getRequestURI());
        return eventServicePublic.findAllByIdForPublic(eventId, request);
    }
}
