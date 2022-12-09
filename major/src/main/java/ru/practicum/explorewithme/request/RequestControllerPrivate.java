package ru.practicum.explorewithme.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.request.service.RequestServicePrivate;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class RequestControllerPrivate {

    private final RequestServicePrivate requestServicePrivate;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ParticipationRequestDto create(@PathVariable @Positive Long userId,
                                          @RequestParam @PositiveOrZero Long eventId) {
        log.info("Create request for event({}) by user with id={} for private layer", eventId, userId);
        return requestServicePrivate.create(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ParticipationRequestDto cancel(@PathVariable @Positive Long userId,
                                          @PathVariable @Positive Long requestId) {
        log.info("User with id={} cancel his request({}) for private layer", userId, requestId);
        return requestServicePrivate.cancel(userId, requestId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> findAllInfoAboutOwnerRequests(@PathVariable @Positive Long userId) {
        log.info("Get all info for user({}) about his requests for private layer", userId);
        return requestServicePrivate.findAllInfoAboutOwnerRequests(userId);
    }
}
