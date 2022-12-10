package ru.practicum.explorewithme.comment.service;

import ru.practicum.explorewithme.comment.dto.CommentDto;

import java.util.List;

public interface CommentServicePublic {

    CommentDto findById(Long commentId);

    List<CommentDto> findAll(Long eventId, Integer from, Integer size, String sortOrder);
}
