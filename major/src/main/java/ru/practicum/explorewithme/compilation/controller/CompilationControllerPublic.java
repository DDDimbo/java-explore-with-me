package ru.practicum.explorewithme.compilation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.service.CompilationServicePublic;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class CompilationControllerPublic {

    private final CompilationServicePublic compilationServicePublic;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CompilationDto> findAllByParams(@RequestParam(required = false) Boolean pinned,
                                                @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                @RequestParam(defaultValue = "10") @PositiveOrZero Integer size) {
        log.info("Find all compilations by params for public layer");
        if (pinned == null)
            return compilationServicePublic.findAll(from, size);
        return compilationServicePublic.findAllByParams(pinned, from, size);
    }

    @GetMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto findById(@PathVariable(name = "compId") @Positive Long compilationId) {
        log.info("Find compilation by id={} for public layer", compilationId);
        return compilationServicePublic.findById(compilationId);
    }
}
