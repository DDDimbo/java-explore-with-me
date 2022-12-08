package ru.practicum.explorewithme.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.enums.State;
import ru.practicum.explorewithme.enums.Status;
import ru.practicum.explorewithme.event.Event;
import ru.practicum.explorewithme.event.EventRepository;
import ru.practicum.explorewithme.exceptions.*;
import ru.practicum.explorewithme.request.ParticipationRequestDto;
import ru.practicum.explorewithme.request.Request;
import ru.practicum.explorewithme.request.RequestMapper;
import ru.practicum.explorewithme.request.RequestRepository;
import ru.practicum.explorewithme.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RequestServicePrivateImpl implements RequestServicePrivate {

    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;


    @Override
    public ParticipationRequestDto create(Long requesterId, Long eventId) {
        if (!userRepository.existsById(requesterId))
            throw new UserNotFoundException("Пользователя с " + requesterId + " не существует.");
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("События с id = " + eventId + " не найдено."));
        if (requestRepository.existsByRequesterIdAndEventId(requesterId, eventId))
            throw new DublicateRequestException("Пользователь уже создал запрос для этого события.");
        if (event.getInitiator().getId().equals(requesterId))
            throw new IllegalArgumentException("Пользователь не может создать запрос на свое событие.");
        if (!event.getState().equals(State.PUBLISHED))
            throw new IncorrectStateException("Нельзя участвовать в неопубликованном событии.");
        long limit = event.getParticipantLimit();
        if (limit != 0 &&
                limit == requestRepository.countByEventIdAndStatus(eventId, Status.CONFIRMED))
            throw new ParticipantLimitEndException("У события достигнут лимит запросов на участие");
        Request result;
        if (event.getRequestModeration() || limit == 0)
            result = requestRepository.save(Request.builder()
                    .eventId(eventId)
                    .requesterId(requesterId)
                    .status(Status.PENDING)
                    .created(LocalDateTime.now())
                    .build());
        else {
            result = requestRepository.save(Request.builder()
                    .eventId(eventId)
                    .requesterId(requesterId)
                    .status(Status.REJECTED)
                    .created(LocalDateTime.now())
                    .build());
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            eventRepository.setNewConfirmedRequests(eventId, event.getConfirmedRequests());
        }

        return RequestMapper.toParticipationRequestDto(result);
    }

    @Override
    public ParticipationRequestDto cancel(Long requesterId, Long requestId) {
        if (!requestRepository.existsByIdAndRequesterId(requestId, requesterId))
            throw new IllegalArgumentException("Передан некорректный путь запроса");
        requestRepository.setStatusForRequest(requestId, Status.CANCELED);
        Event event = eventRepository.findEventByRequestInfo(requestId, requesterId)
                .orElseThrow(() -> new EventNotFoundException("События с указанным id не найдено."));
        //Обновление confirmedRequests
        Long oldCount = event.getConfirmedRequests();
        eventRepository.setNewConfirmedRequests(event.getId(), oldCount - 1);

        return RequestMapper.toParticipationRequestDto(requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException("Запрос с id=" + requestId + " не найден.")));
    }

    @Transactional(readOnly = true)
    @Override
    public List<ParticipationRequestDto> findAllInfoAboutOwnerRequests(Long requesterId) {
        return requestRepository.findAllByRequesterId(requesterId).stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }
}
