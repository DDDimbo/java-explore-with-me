package ru.practicum.explorewithme.event.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.NewEventDto;
import ru.practicum.explorewithme.event.dto.UpdateEventRequest;
import ru.practicum.explorewithme.event.service.EventServicePrivate;
import ru.practicum.explorewithme.markerinterface.Create;
import ru.practicum.explorewithme.request.ParticipationRequestDto;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/events")
public class EventControllerPrivate {

    private final EventServicePrivate eventServicePrivate;


    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto create(@PathVariable Long userId,
                               @RequestBody @Validated(Create.class) NewEventDto eventDto) {
        log.info("Create event by user with id={}", userId);
        return eventServicePrivate.create(userId, eventDto);
    }


    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto change(@PathVariable @Positive Long userId,
                               @RequestBody UpdateEventRequest updateEvent) {
        log.info("Changed event by user with id={}", userId);
        return eventServicePrivate.change(userId, updateEvent);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/confirm")
    @ResponseStatus(HttpStatus.OK)
    public ParticipationRequestDto confirm(@PathVariable @Positive Long userId,
                                           @PathVariable(value = "reqId") @Positive Long requestId,
                                           @PathVariable @Positive Long eventId) {
        log.info("Confirm request with id={}, where eventId={}, userId={}", requestId, eventId, userId);
        return eventServicePrivate.confirm(userId, requestId, eventId);
    }

    @PatchMapping("/{eventId}/requests/{reqId}/reject")
    @ResponseStatus(HttpStatus.OK)
    public ParticipationRequestDto reject(@PathVariable @Positive Long userId,
                                          @PathVariable(value = "reqId") @Positive Long requestId,
                                          @PathVariable @Positive Long eventId) {
        log.info("Reject request with id={}, where eventId={}, userId={}", requestId, eventId, userId);
        return eventServicePrivate.reject(userId, requestId, eventId);
    }


    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto cancel(@PathVariable @Positive Long userId,
                               @PathVariable @Positive Long eventId) {
        log.info("Cancel event with id={} by user with id={}", eventId, userId);
        return eventServicePrivate.cancel(userId, eventId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> findAllByUser(@PathVariable @Positive Long userId,
                                            @RequestParam(name = "from", defaultValue = "0") Integer from,
                                            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        log.info("Find all events by user with id={}", userId);
        return eventServicePrivate.findAllByUser(userId, from, size);
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto findAllInfoAboutEvent(@PathVariable @Positive Long userId,
                                              @PathVariable @Positive Long eventId) {
        log.info("Find all info about event(id={}), where initiatorId={}", eventId, userId);
        return eventServicePrivate.findAllInfoAboutEvent(userId, eventId);
    }

    @GetMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> findAllInfoAboutPartInEvent(@PathVariable @Positive Long userId,
                                                                     @PathVariable @Positive Long eventId) {
        log.info("Find all info about participant in event(id={}), where initiatorId={}", eventId, userId);
        return eventServicePrivate.findAllInfoAboutPartInEvent(userId, eventId);
    }
}
