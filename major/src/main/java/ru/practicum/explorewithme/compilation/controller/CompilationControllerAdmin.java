package ru.practicum.explorewithme.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.NewCompilationDto;
import ru.practicum.explorewithme.compilation.service.CompilationServiceAdmin;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@Slf4j
@RequestMapping("/admin/compilations")
@RequiredArgsConstructor
public class CompilationControllerAdmin {

    private final CompilationServiceAdmin compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto create(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Create compilation with title - {} by admin", newCompilationDto.getTitle());
        return compilationService.create(newCompilationDto);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public void addEventInCompilation(@PathVariable(name = "compId") @Positive Long compilationId,
                                      @PathVariable @Positive Long eventId) {
        log.info("Add event({}) in compilation({}) for admin layer", eventId, compilationId);
        compilationService.addEventInCompilation(eventId, compilationId);
    }

    @PatchMapping("/{compId}/pin")
    @ResponseStatus(HttpStatus.OK)
    public void pin(@PathVariable(name = "compId") @Positive Long compilationId) {
        log.info("Pin compilation with id={} for admin layer", compilationId);
        compilationService.pinned(compilationId);
    }

    @DeleteMapping("/{compId}/pin")
    @ResponseStatus(HttpStatus.OK)
    public void unpinned(@PathVariable(value = "compId") @Positive Long compilationId) {
        log.info("Delete (unpinned) compilation with id {}  for admin layer", compilationId);
        compilationService.unpinned(compilationId);
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable(value = "compId") @Positive Long id) {
        log.info("Delete compilation with id {}  for admin layer", id);
        compilationService.deleteById(id);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEventFromCompilation(@PathVariable(name = "compId") @Positive Long compilationId,
                                           @PathVariable @Positive Long eventId) {
        log.info("Delete event({}) from compilation({}) for admin layer", eventId, compilationId);
        compilationService.deleteEventFromCompilation(compilationId, eventId);
    }
}
