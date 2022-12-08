package ru.practicum.explorewithme.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.enums.State;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.NewEventDto;
import ru.practicum.explorewithme.event.service.EventServiceAdmin;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/events")
public class EventControllerAdmin {

    private final EventServiceAdmin eventServiceAdmin;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> findAllByParam(
            @RequestParam(value = "users", required = false) List<Long> users,
            @RequestParam(value = "states", required = false) List<State> states,
            @RequestParam(value = "categories", required = false) List<Long> categories,
            @RequestParam(value = "rangeStart", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(value = "rangeEnd", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.info("Find all by parameters for admin layer");
        return eventServiceAdmin.findAllByParam(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PutMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto update(@PathVariable(value = "eventId") Long eventId,
                               @RequestBody NewEventDto updateEvent) {
        log.info("Update(put) event with id={} for admin layer", eventId);
        return eventServiceAdmin.update(eventId, updateEvent);
    }

    @PatchMapping("/{eventId}/publish")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto publish(@PathVariable(value = "eventId") Long eventId) {
        log.info("Published event with id={}", eventId);
        return eventServiceAdmin.publish(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto reject(@PathVariable(value = "eventId") Long eventId) {
        log.info("Rejected event with id={}", eventId);
        return eventServiceAdmin.reject(eventId);
    }
}
