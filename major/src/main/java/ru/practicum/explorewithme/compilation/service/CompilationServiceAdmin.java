package ru.practicum.explorewithme.compilation.service;


import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.NewCompilationDto;

public interface CompilationServiceAdmin {

    CompilationDto create(NewCompilationDto newCompilationDto);

    void addEventInCompilation(Long eventId, Long compilationId);

    void pinned(Long compilationId);

    void deleteEventFromCompilation(Long compilationId, Long eventId);

    void deleteById(Long id);

    void unpinned(Long id);
}
