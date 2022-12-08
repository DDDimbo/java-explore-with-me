package ru.practicum.explorewithme.compilation.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.compilation.CompilationMapper;
import ru.practicum.explorewithme.compilation.CompilationRepository;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.service.CompilationServicePublic;
import ru.practicum.explorewithme.exceptions.CompilationNotFoundException;
import ru.practicum.explorewithme.utility.FromSizeRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompilationServicePublicImpl implements CompilationServicePublic {

    private final CompilationRepository compilationRepository;

    @Override
    public List<CompilationDto> findAllByParams(Boolean pinned, Integer from, Integer size) {
        Pageable pageable = FromSizeRequest.of(from, size);
        return compilationRepository.findAllByPinned(pinned, pageable).stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CompilationDto> findAll(Integer from, Integer size) {
        Pageable pageable = FromSizeRequest.of(from, size);
        return compilationRepository.findAll(pageable).stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto findById(Long compilationId) {
        return CompilationMapper.toCompilationDto(compilationRepository.findById(compilationId)
                .orElseThrow(() -> new CompilationNotFoundException("Подборки с id=" + compilationId + " не найдено")));
    }
}
