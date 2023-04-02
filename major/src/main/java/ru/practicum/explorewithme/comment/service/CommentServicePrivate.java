package ru.practicum.explorewithme.comment.service;

import ru.practicum.explorewithme.comment.dto.CommentCreateDto;
import ru.practicum.explorewithme.comment.dto.CommentDto;

import java.util.List;

public interface CommentServicePrivate {

    CommentDto create(Long userId, CommentCreateDto newComment);

    CommentDto change(Long userId, Long commentId, CommentDto commentDto);

    void delete(Long userId, Long commentId);

    List<CommentDto> findAllForWriter(Long userId, Integer from, Integer size, String sortOrder);

    CommentDto likeComment(Long userId, Long commentId);

    CommentDto dislikeComment(Long userId, Long commentId);

}
