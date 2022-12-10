package ru.practicum.explorewithme.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.comment.dto.CommentDto;
import ru.practicum.explorewithme.comment.service.CommentServicePublic;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentControllerPublic {

    private final CommentServicePublic commentServicePublic;

    @GetMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto findById(@PathVariable @Positive Long commentId) {
        log.info("Find comment by id={} for public layer", commentId);
        return commentServicePublic.findById(commentId);
    }

    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> findAll(@PathVariable @Positive Long eventId,
                                    @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                    @RequestParam(defaultValue = "10") @PositiveOrZero Integer size,
                                    @RequestParam(name = "order", defaultValue = "asc") String sortOrder) {
        log.info("Find all for event with id={}", eventId);
        return commentServicePublic.findAll(eventId, from, size, sortOrder);
    }
}
