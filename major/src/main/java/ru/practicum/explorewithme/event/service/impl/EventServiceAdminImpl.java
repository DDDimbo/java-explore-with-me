package ru.practicum.explorewithme.event.service.impl;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.category.Category;
import ru.practicum.explorewithme.category.CategoryRepository;
import ru.practicum.explorewithme.enums.State;
import ru.practicum.explorewithme.event.Event;
import ru.practicum.explorewithme.event.EventMapper;
import ru.practicum.explorewithme.event.QEvent;
import ru.practicum.explorewithme.event.EventRepository;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.NewEventDto;
import ru.practicum.explorewithme.event.service.EventServiceAdmin;
import ru.practicum.explorewithme.exceptions.CategoryNotFoundException;
import ru.practicum.explorewithme.exceptions.EventNotFoundException;
import ru.practicum.explorewithme.exceptions.IncorrectStateException;
import ru.practicum.explorewithme.exceptions.RangeTimeException;
import ru.practicum.explorewithme.utility.FromSizeRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EventServiceAdminImpl implements EventServiceAdmin {

    private final EventRepository eventRepository;

    private final CategoryRepository categoryRepository;


    @Transactional(readOnly = true)
    @Override
    public List<EventFullDto> findAllByParam(List<Long> users,
                                             List<State> states,
                                             List<Long> categories,
                                             LocalDateTime rangeStart,
                                             LocalDateTime rangeEnd,
                                             Integer from,
                                             Integer size) {
        Pageable pageable = FromSizeRequest.of(from, size);
        final QEvent event = QEvent.event;
        BooleanBuilder where = new BooleanBuilder();
        if (users != null)
            where.and(event.initiator.id.in(users));
        if (states != null)
            where.and((event.state.in(states)));
        if (categories != null)
            where.and(event.category.id.in(categories));
        if (rangeStart != null)
            where.and(event.eventDate.after(rangeStart));
        if (rangeEnd != null)
            where.and(event.eventDate.before(rangeEnd));

        return eventRepository.findAll(where, pageable).stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto update(Long eventId, NewEventDto updateEvent) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("События с таким с id = " + eventId + " не найдено."));

        if (updateEvent.getAnnotation() != null)
            event.setAnnotation(updateEvent.getAnnotation());
        if (updateEvent.getDescription() != null)
            event.setDescription(updateEvent.getDescription());
        if (updateEvent.getEventDate() != null)
            event.setEventDate(updateEvent.getEventDate());
        if (updateEvent.getCategoryId() != null) {
            Long categoryId = updateEvent.getCategoryId();
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new CategoryNotFoundException("Категории с таким с id = " + categoryId + "не найдено."));
            event.setCategory(category);
        }
        if (updateEvent.getPaid() != null)
            event.setPaid(updateEvent.getPaid());
        if (updateEvent.getParticipantLimit() != null)
            event.setParticipantLimit(updateEvent.getParticipantLimit());
        if (updateEvent.getRequestModeration() != null)
            event.setRequestModeration(updateEvent.getRequestModeration());
        if (updateEvent.getTitle() != null)
            event.setTitle(updateEvent.getTitle());
        if (updateEvent.getLocation() != null) {
            event.setLocationLat(updateEvent.getLocation().getLocationLat());
            event.setLocationLat(updateEvent.getLocation().getLocationLon());
        }
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto publish(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("События с таким с id = " + eventId + " не найдено."));
        if (event.getEventDate().minusHours(1).isBefore(LocalDateTime.now()))
            throw new RangeTimeException(
                    "Нельзя публиковать событие с id=" + eventId + ", если осталось меньше часа до начала."
            );
        if (!event.getState().equals(State.PENDING))
            throw new IncorrectStateException("Для подтверждения публикации статус должен быть \"В ожидании\".");

        event.setState(State.PUBLISHED);
        event.setPublishedOn(LocalDateTime.now());
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public EventFullDto reject(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("События с таким с id = " + eventId + " не найдено."));

        event.setState(State.CANCELED);
        return EventMapper.toEventFullDto(eventRepository.save(event));
    }
}
