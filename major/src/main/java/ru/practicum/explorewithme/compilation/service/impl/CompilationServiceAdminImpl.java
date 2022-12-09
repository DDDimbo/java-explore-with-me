package ru.practicum.explorewithme.compilation.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.compilation.Compilation;
import ru.practicum.explorewithme.compilation.CompilationMapper;
import ru.practicum.explorewithme.compilation.CompilationRepository;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.NewCompilationDto;
import ru.practicum.explorewithme.compilation.service.CompilationServiceAdmin;
import ru.practicum.explorewithme.enums.State;
import ru.practicum.explorewithme.event.Event;
import ru.practicum.explorewithme.event.EventRepository;
import ru.practicum.explorewithme.exceptions.AlreadyExistsException;
import ru.practicum.explorewithme.exceptions.CompilationNotFoundException;
import ru.practicum.explorewithme.exceptions.CustomValidationException;
import ru.practicum.explorewithme.exceptions.EventNotFoundException;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class CompilationServiceAdminImpl implements CompilationServiceAdmin {

    private final CompilationRepository compilationRepository;

    private final EventRepository eventRepository;


    @Override
    public CompilationDto create(NewCompilationDto newCompilationDto) {
        Set<Event> events = eventRepository.findAllByIdIn(newCompilationDto.getEventIds());
        Compilation result = compilationRepository.save(CompilationMapper.toCompilationFromNew(newCompilationDto, events));
        return CompilationMapper.toCompilationDto(result);
    }

    @Override
    public void addEventInCompilation(Long eventId, Long compilationId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("События с id=" + eventId + " не найдено."));
        if (!event.getState().equals(State.PUBLISHED))
            throw new IllegalArgumentException("Переданное событие(" + eventId + ") не подтверждено");
        Set<Event> events = compilationRepository.findEventsById(compilationId)
                .orElseThrow(() -> new CompilationNotFoundException("Подборка с id=" + compilationId + " не найдена"));
        events.add(event);

        Compilation result = compilationRepository.findById(compilationId)
                .orElseThrow(() ->  new CompilationNotFoundException("Подборка с id=" + compilationId + " не найдена"));
        result.setEvents(events);
        compilationRepository.save(result);
    }

    @Override
    public void deleteById(Long compilationId) {
        compilationRepository.deleteById(compilationId);
    }

    @Override
    public void pinned(Long compilationId) {
        if (!compilationRepository.existsById(compilationId))
            throw new CompilationNotFoundException("Подборка с id=" + compilationId + " не найдена");
        if (compilationRepository.existsByIdAndPinned(compilationId, true))
            throw new AlreadyExistsException("Данная подборка(" + compilationId + ") уже закреплена");
        compilationRepository.setPinned(true, compilationId);
    }

    @Override
    public void unpinned(Long compilationId) {
        if (!compilationRepository.existsById(compilationId))
            throw new CompilationNotFoundException("Подборка с id=" + compilationId + " не найдена");
        if (compilationRepository.existsByIdAndPinned(compilationId, false))
            throw new AlreadyExistsException("Данная подборка(" + compilationId + ") уже откреплена");
        compilationRepository.setPinned(false, compilationId);
    }


    @Override
    public void deleteEventFromCompilation(Long compilationId, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("События с id=" + eventId + " не найдено."));
        if (!event.getState().equals(State.PUBLISHED))
            throw new CustomValidationException("Переданное событие(" + eventId + ") не подтверждено");
        Set<Event> events = compilationRepository.findEventsById(compilationId)
                .orElseThrow(() -> new CompilationNotFoundException("Подборка с id=" + compilationId + " не найдена"));
        events.remove(event);

        Compilation result = compilationRepository.findById(compilationId)
                .orElseThrow(() ->  new CompilationNotFoundException("Подборка с id=" + compilationId + " не найдена"));
        result.setEvents(events);
        compilationRepository.save(result);
    }
}
