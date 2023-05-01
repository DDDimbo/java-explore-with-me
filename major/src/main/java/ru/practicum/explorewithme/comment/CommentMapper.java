package ru.practicum.explorewithme.comment;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.comment.dto.CommentCreateDto;
import ru.practicum.explorewithme.comment.dto.CommentDto;
import ru.practicum.explorewithme.comment.dto.CommentDtoView;
import ru.practicum.explorewithme.event.Event;
import ru.practicum.explorewithme.event.EventMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommentMapper {

    public static Comment toCreateComment(CommentCreateDto newComment, Event event, Long userId, Boolean isVisit) {
        return Comment.builder()
                .text(newComment.getText())
                .event(event)
                .writerId(userId)
                .visited(isVisit)
                .written(LocalDateTime.now())
                .build();
    }

    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .event(EventMapper.toEventShortDto(comment.getEvent()))
                .writerId(comment.getWriterId())
                .visited(comment.getVisited())
                .written(comment.getWritten())
                .build();
    }

    public static CommentDto toCommentDto(Comment comment, Long likes, Long dislikes) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .event(EventMapper.toEventShortDto(comment.getEvent()))
                .writerId(comment.getWriterId())
                .visited(comment.getVisited())
                .written(comment.getWritten())
                .likes(likes)
                .dislikes(dislikes)
                .build();
    }

    public static CommentDto toCommentDto(CommentDtoView commentDtoView) {
        return CommentDto.builder()
                .id(commentDtoView.getId())
                .text(commentDtoView.getText())
//                .event(EventMapper.toEventShortDto(commentDtoView.getEvent()))
                .writerId(commentDtoView.getWriterId())
                .visited(commentDtoView.getVisited())
                .written(commentDtoView.getWritten())
                .likes(commentDtoView.getLikes())
                .dislikes(commentDtoView.getDislikes())
                .build();
    }
//
//
//    public static CommentDto toCommentDto(CommentDtoView commentDtoView) {
//        return CommentDto.builder()
//                .id(commentDtoView.getId())
//                .text(commentDtoView.getText())
//                .event(EventMapper.toEventShortDto(commentDtoView.getEventId()))
//                .writerId(commentDtoView.getWriterId())
//                .visited(commentDtoView.getVisited())
//                .written(commentDtoView.getWritten())
//                .likes(commentDtoView.getLikes())
//                .dislikes(commentDtoView.getDislikes())
//                .build();
//    }

    public static List<CommentDto> toCommentDto(List<CommentDtoView> comments) {
        final List<CommentDto> result = new ArrayList<>();
        for (CommentDtoView comment : comments)
            result.add(toCommentDto(comment));
        return result;
    }
}
