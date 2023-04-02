package ru.practicum.explorewithme.comment.repository;

import ru.practicum.explorewithme.comment.dto.CommentDto;

import java.util.List;

public interface CommentRepositoryCustom {

    List<CommentDto> findAllCommentsWithFullInfoByUserId(Long userId);
}
