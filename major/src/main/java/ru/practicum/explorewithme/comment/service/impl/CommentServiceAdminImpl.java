package ru.practicum.explorewithme.comment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.comment.CommentMapper;
import ru.practicum.explorewithme.comment.dto.CommentDto;
import ru.practicum.explorewithme.comment.repository.CommentRepository;
import ru.practicum.explorewithme.comment.service.CommentServiceAdmin;
import ru.practicum.explorewithme.exceptions.CommentNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceAdminImpl implements CommentServiceAdmin {

    private final CommentRepository commentRepository;

    @Override
    public CommentDto findById(Long commentId) {
        return CommentMapper.toCommentDto(commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Комментария с id=" + commentId + " не найдено.")));
    }
}
