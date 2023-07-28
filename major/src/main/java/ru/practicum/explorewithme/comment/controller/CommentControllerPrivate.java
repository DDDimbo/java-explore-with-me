package ru.practicum.explorewithme.comment.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.comment.dto.CommentCreateDto;
import ru.practicum.explorewithme.comment.dto.CommentDto;
import ru.practicum.explorewithme.comment.dto.CommentUpdateDto;
import ru.practicum.explorewithme.comment.service.CommentServicePrivate;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/users/{userId}/comments")
public class CommentControllerPrivate {

    private final CommentServicePrivate commentServicePrivate;

    // нужно ли добавить инфу про евент в запрос?
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto create(@PathVariable @Positive Long userId, @RequestBody CommentCreateDto newComment) {
        log.info("Create comment by user({}) to event {}", userId, newComment.getEventId());
        return commentServicePrivate.create(userId, newComment);
    }

    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto change(@PathVariable @Positive Long userId,
                             @PathVariable @Positive Long commentId,
                             @RequestBody CommentUpdateDto commentUpdateDto) {
        log.info("Change comment({}) by user({})", commentId, userId);
        return commentServicePrivate.change(userId, commentId, commentUpdateDto);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable @Positive Long userId,
                       @PathVariable @Positive Long commentId) {
        log.info("Delete comment({}) by user({})", commentId, userId);
        commentServicePrivate.delete(userId, commentId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> findAllForWriter(@PathVariable @Positive Long userId,
                                             @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                             @RequestParam(defaultValue = "10") @PositiveOrZero Integer size,
                                             @RequestParam(name = "order", defaultValue = "desc") String sortOrder) {
        log.info("Find all comments which was written by user({})", userId);
        return commentServicePrivate.findAllForWriter(userId, from, size, sortOrder);
    }

    @PatchMapping("/{commentId}/like")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto likeComment(@PathVariable @Positive Long userId,
                                        @PathVariable @Positive Long commentId) {
        log.info("Patch method: like comment. userId={}; commentId={}", userId, commentId);
        return commentServicePrivate.likeComment(userId, commentId);
    }

    @PatchMapping("/{commentId}/dislike")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto dislikeComment(@PathVariable @Positive Long userId,
                                           @PathVariable @Positive Long commentId) {
        log.info("Patch method: dislike comment.");
        return commentServicePrivate.dislikeComment(userId, commentId);
    }
}
