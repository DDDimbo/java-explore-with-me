package ru.practicum.explorewithme.event.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.category.Category;
import ru.practicum.explorewithme.category.CategoryRepository;
import ru.practicum.explorewithme.enums.State;
import ru.practicum.explorewithme.enums.Status;
import ru.practicum.explorewithme.event.Event;
import ru.practicum.explorewithme.event.EventMapper;
import ru.practicum.explorewithme.event.EventRepository;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.NewEventDto;
import ru.practicum.explorewithme.event.dto.UpdateEventRequest;
import ru.practicum.explorewithme.event.service.EventServicePrivate;
import ru.practicum.explorewithme.exceptions.*;
import ru.practicum.explorewithme.request.ParticipationRequestDto;
import ru.practicum.explorewithme.request.RequestMapper;
import ru.practicum.explorewithme.request.RequestRepository;
import ru.practicum.explorewithme.user.User;
import ru.practicum.explorewithme.user.UserRepository;
import ru.practicum.explorewithme.utility.FromSizeRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventServicePrivateImpl implements EventServicePrivate {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final RequestRepository requestRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public EventFullDto create(Long initiatorId, NewEventDto eventDto) {
        User initiator = userRepository.findById(initiatorId)
                .orElseThrow(() -> new UserNotFoundException("Пользователя с id = " + initiatorId + " не найдено"));
        if (eventDto.getEventDate().minusHours(2).isBefore(LocalDateTime.now()))
            throw new RangeTimeException("Создать новое событие можно не позднее, чем за 2 часа до его начала");
        Category category = categoryRepository.findById(eventDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(
                        "Категории с таким с id = " + eventDto.getCategoryId() + "не найдено")
                );
        Event result = eventRepository.save(EventMapper.toEventCreate(initiator, category, eventDto));
        return EventMapper.toEventFullDto(result);
    }

    @Transactional
    @Override
    public EventFullDto change(Long initiatorId, UpdateEventRequest updateEvent) {
        Event event = eventRepository.findById(updateEvent.getEventId())
                .orElseThrow(() -> new EventNotFoundException("События с id = " + updateEvent.getEventId() + " не найдено."));

        // Валидация
        if (!event.getInitiator().getId().equals(initiatorId))
            throw new UserAccessException("Пользователь не является создателем события");
        if (event.getState().equals(State.PUBLISHED))
            throw new IncorrectStateException("Нельзя обновить уже опубликованное событие.");
        if (event.getEventDate().minusHours(2).isBefore(LocalDateTime.now()))
            throw new RangeTimeException("Создать новое событие можно не позднее, чем за 2 часа до нового события");

        // Обновление
        if (event.getState().equals(State.CANCELED))
            event.setState(State.PENDING);
        if (updateEvent.getAnnotation() != null)
            event.setAnnotation(updateEvent.getAnnotation());
        if (updateEvent.getDescription() != null)
            event.setDescription(updateEvent.getDescription());
        if (updateEvent.getTitle() != null)
            event.setTitle(updateEvent.getTitle());
        if (updateEvent.getPaid() != null)
            event.setPaid(updateEvent.getPaid());
        if (updateEvent.getCategoryId() != null) {
            Long categoryId = updateEvent.getCategoryId();
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new CategoryNotFoundException("Категории с таким с id = " + categoryId + "не найдено."));
            event.setCategory(category);
        }
        if (updateEvent.getEventDate() != null)
            event.setEventDate(updateEvent.getEventDate());
        if (updateEvent.getParticipantLimit() != null)
            event.setParticipantLimit(updateEvent.getParticipantLimit());

        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Transactional
    @Override
    public EventFullDto cancel(Long initiatorId, Long eventId) {
        if (!eventRepository.existsByIdAndInitiator_Id(eventId, initiatorId))
            throw new IllegalArgumentException("Передан некорректный путь запроса");
        if (!eventRepository.existsByIdAndState(eventId, State.PENDING))
            throw new UserAccessException("Можно отменить только событие находящееся в модерации.");
        eventRepository.setCancelByInitiator(eventId, State.CANCELED);
        return EventMapper.toEventFullDto(eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("События с id=" + eventId + " не существует.")));
    }

    @Override
    public List<EventFullDto> findAllByUser(Long initiatorId, Integer from, Integer size) {
        if (!userRepository.existsById(initiatorId))
            throw new IllegalArgumentException("Передан некорректный путь запроса");
        Pageable pageable = FromSizeRequest.of(from, size);
        return eventRepository.findAllByInitiator_Id(initiatorId, pageable).stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto findAllInfoAboutEvent(Long initiatorId, Long eventId) {
        if (!eventRepository.existsByIdAndInitiator_Id(eventId, initiatorId))
            throw new IllegalArgumentException("Передан некорректный путь запроса");
        return EventMapper.toEventFullDto(eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("События с id=" + eventId + " не найдено.")));
    }

    @Transactional
    @Override
    public ParticipationRequestDto confirm(Long initiatorId, Long requestId, Long eventId) {
        // Выгрузка данных и валидация
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("События с id=" + eventId + " не найдено."));
        if (!event.getInitiator().getId().equals(initiatorId))
            throw new UserAccessException("Пользователь c id=" + initiatorId + " не является создателем события");
        if (!requestRepository.existsByIdAndEventId(requestId, eventId))
            throw new IllegalArgumentException("Передан некорректный путь запроса");
        // Действия с запросом
        long limit = event.getParticipantLimit();
        if (!event.getRequestModeration() || limit == 0L)
            throw new IllegalArgumentException("Подтверждение заявки не требуется");
        if (limit == requestRepository.countByEventIdAndStatus(eventId, Status.CONFIRMED))
            throw new ParticipantLimitEndException("У события достигнут лимит запросов на участие");
        // Обновление данных
        requestRepository.setStatusForRequest(requestId, Status.CONFIRMED);
        if (limit == requestRepository.countByEventIdAndStatus(eventId, Status.CONFIRMED))
            requestRepository.setCancelAfterLimitEnd(eventId, Status.PENDING, Status.REJECTED);
        //Обновление confirmedRequests
        event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        eventRepository.setNewConfirmedRequests(event.getId(), event.getConfirmedRequests());

        return RequestMapper.toParticipationRequestDto(requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException("Запрос с id=" + requestId + " не найден.")));
    }

    @Transactional
    @Override
    public ParticipationRequestDto reject(Long initiatorId, Long requestId, Long eventId) {
        // Выгрузка данных и валидация
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("События с id=" + eventId + " не найдено."));
        if (!event.getInitiator().getId().equals(initiatorId))
            throw new UserAccessException("Пользователь c id=" + initiatorId + " не является создателем события");
        if (!requestRepository.existsByIdAndEventId(requestId, eventId))
            throw new IllegalArgumentException("Передан некорректный путь запроса");
        // Обновление данных
        requestRepository.setStatusForRequest(requestId, Status.REJECTED);

        return RequestMapper.toParticipationRequestDto(requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestNotFoundException("Запрос с id=" + requestId + " не найден.")));
    }

    @Override
    public List<ParticipationRequestDto> findAllInfoAboutPartInEvent(Long initiatorId, Long eventId) {
        if (!eventRepository.existsByIdAndInitiator_Id(eventId, initiatorId))
            throw new IllegalArgumentException("Передан некорректный путь запроса");
        return requestRepository.findAllByEventId(eventId).stream()
                .map(RequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }
}
