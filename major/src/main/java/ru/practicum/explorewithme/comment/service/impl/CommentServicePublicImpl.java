package ru.practicum.explorewithme.comment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.comment.CommentMapper;
import ru.practicum.explorewithme.comment.CommentRepository;
import ru.practicum.explorewithme.comment.dto.CommentDto;
import ru.practicum.explorewithme.comment.service.CommentServicePublic;
import ru.practicum.explorewithme.event.EventRepository;
import ru.practicum.explorewithme.exceptions.CommentNotFoundException;
import ru.practicum.explorewithme.exceptions.CustomValidationException;
import ru.practicum.explorewithme.exceptions.EventNotFoundException;
import ru.practicum.explorewithme.utility.FromSizeRequest;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.enums.Order.ASC;
import static ru.practicum.explorewithme.enums.Order.DESC;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServicePublicImpl implements CommentServicePublic {

    private final CommentRepository commentRepository;

    private final EventRepository eventRepository;

    @Override
    public CommentDto findById(Long commentId) {
        return CommentMapper.toCommentDto(commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Комментария с id=" + commentId + " не найдено.")));
    }

    @Override
    public List<CommentDto> findAll(Long eventId, Integer from, Integer size, String sortOrder) {
        if (eventRepository.existsById(eventId))
            throw new EventNotFoundException("События с id=" + eventId + " не существует.");
        Pageable pageable;
        if (sortOrder.equalsIgnoreCase(ASC.getOrder()))
            pageable = FromSizeRequest.of(from, size, Sort.by("written").ascending());
        else if (sortOrder.equalsIgnoreCase(DESC.getOrder()))
            pageable = FromSizeRequest.of(from, size, Sort.by("written").descending());
        else
            throw new CustomValidationException("Значение сортировки заданно не верно.");

        return commentRepository.findAllByEvent_Id(eventId, pageable).stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }
}
