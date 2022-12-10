package ru.practicum.explorewithme.comment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.comment.Comment;
import ru.practicum.explorewithme.comment.CommentMapper;
import ru.practicum.explorewithme.comment.CommentRepository;
import ru.practicum.explorewithme.comment.dto.CommentCreateDto;
import ru.practicum.explorewithme.comment.dto.CommentDto;
import ru.practicum.explorewithme.comment.service.CommentServicePrivate;
import ru.practicum.explorewithme.enums.State;
import ru.practicum.explorewithme.enums.Status;
import ru.practicum.explorewithme.event.Event;
import ru.practicum.explorewithme.event.EventRepository;
import ru.practicum.explorewithme.exceptions.*;
import ru.practicum.explorewithme.request.RequestRepository;
import ru.practicum.explorewithme.user.UserRepository;
import ru.practicum.explorewithme.utility.FromSizeRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.explorewithme.enums.Order.ASC;
import static ru.practicum.explorewithme.enums.Order.DESC;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServicePrivateImpl implements CommentServicePrivate {

    private final CommentRepository commentRepository;

    private final EventRepository eventRepository;

    private final UserRepository userRepository;

    private final RequestRepository requestRepository;

    @Override
    public CommentDto create(Long userId, CommentCreateDto newComment) {
        if (userRepository.existsById(userId))
            throw new UserNotFoundException("Пользователь с id=" + userId + " не зарегистрирован.");
        Event event = eventRepository.findById(newComment.getEventId())
                .orElseThrow(() -> new EventNotFoundException("События с id=" + newComment.getEventId() + " не найдено."));
        if (event.getInitiator().getId().equals(userId))
            throw new UserAccessException("Пользователь с id=" + userId + "не может оставить комментарий на свое событие.");
        if (!event.getState().equals(State.PUBLISHED))
            throw new IncorrectStateException("Комментарий можно оставить только к опубликованному событию.");
        Comment result = commentRepository.save(CommentMapper.toCreateComment(newComment, event, userId,
                requestRepository.existsByRequesterIdAndEventIdAndStatus(userId, event.getId(), Status.CONFIRMED))
        );
        return CommentMapper.toCommentDto(result);
    }

    @Override
    public CommentDto change(Long userId, Long commentId, CommentDto commentDto) {
        if (userRepository.existsById(userId))
            throw new UserNotFoundException("Пользователь с id=" + userId + " не зарегистрирован.");
        if (commentRepository.existsByIdAndWriterId(commentId, userId))
            throw new IllegalArgumentException(
                    "Комментария с параметрами id=" + commentId + " и userId=" + userId + " не существует."
            );
        if (commentRepository.timeCheck(commentId, LocalDateTime.now().minusHours(1)))
            throw new RangeTimeException("Отредактировать комментарий можно не позднее, чем через час после публикации.");
        commentRepository.setNewText(commentDto.getText(), commentId);
        commentRepository.setNewText(commentDto.getText(), commentId);
        return CommentMapper.toCommentDto(commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException("Комментария с id=" + commentId + " не найдено.")));
    }

    @Override
    public void delete(Long userId, Long commentId) {
        if (userRepository.existsById(userId))
            throw new UserNotFoundException("Пользователь с id=" + userId + " не зарегистрирован.");
        if (commentRepository.existsByIdAndWriterId(commentId, userId))
            throw new IllegalArgumentException(
                    "Комментария с параметрами id=" + commentId + " и userId=" + userId + " не существует."
            );
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentDto> findAllForWriter(Long userId, Integer from, Integer size, String sortOrder) {
        if (userRepository.existsById(userId))
            throw new UserNotFoundException("Пользователь с id=" + userId + " не зарегистрирован.");
        Pageable pageable;
        if (sortOrder.equalsIgnoreCase(ASC.getOrder()))
            pageable = FromSizeRequest.of(from, size, Sort.by("written").ascending());
        else if (sortOrder.equalsIgnoreCase(DESC.getOrder()))
            pageable = FromSizeRequest.of(from, size, Sort.by("written").descending());
        else
            throw new CustomValidationException("Значение сортировки заданно не верно.");

        return commentRepository.findAllById(userId, pageable).stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }
}
