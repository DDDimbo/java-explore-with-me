package ru.practicum.explorewithme.comment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.comment.CommentMapper;
import ru.practicum.explorewithme.comment.dto.CommentDto;
import ru.practicum.explorewithme.comment.repository.CommentRepository;
import ru.practicum.explorewithme.comment.service.CommentServicePublic;
import ru.practicum.explorewithme.comment.utility.CommentSort;
import ru.practicum.explorewithme.event.EventRepository;
import ru.practicum.explorewithme.exceptions.EventNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServicePublicImpl implements CommentServicePublic {

    private final CommentRepository commentRepository;

    private final EventRepository eventRepository;

    @Override
    public List<CommentDto> findAll(Long eventId, Integer from, Integer size, String sortOrder) {
        if (!eventRepository.existsById(eventId))
            throw new EventNotFoundException("События с id=" + eventId + " не существует.");

        final Pageable pageable = CommentSort.sortByTimeAndRating(from, size, sortOrder);

        return CommentMapper.toCommentDto(commentRepository.findAllCommentsByEvent(eventId, pageable));
    }
}
