package ru.practicum.explorewithme.compilation.service;

import ru.practicum.explorewithme.compilation.dto.CompilationDto;

import java.util.List;

public interface CompilationServicePublic {

    List<CompilationDto> findAllByParams(Boolean pinned, Integer from, Integer size);

    List<CompilationDto> findAll(Integer from, Integer size);

    CompilationDto findById(Long compilationId);
}
