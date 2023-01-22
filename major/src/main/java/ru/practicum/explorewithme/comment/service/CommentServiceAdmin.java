package ru.practicum.explorewithme.comment.service;

import ru.practicum.explorewithme.comment.dto.CommentDto;

public interface CommentServiceAdmin {


    CommentDto findById(Long commentId);
}
