package ru.practicum.explorewithme.comment.dto;

import ru.practicum.explorewithme.event.Event;

import java.time.LocalDateTime;

public interface CommentDtoView {

    Long getId();

    String getText();

    Long getWriterId();

    Event getEvent();

    Boolean getVisited();

    LocalDateTime getWritten();

    Long getLikes();

    Long getDislikes();
}
