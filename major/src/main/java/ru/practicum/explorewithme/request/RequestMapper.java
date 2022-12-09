package ru.practicum.explorewithme.request;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RequestMapper {

    public static ParticipationRequestDto toParticipationRequestDto(Request request) {
        return ParticipationRequestDto.builder()
                .id(request.getId())
                .eventId(request.getEventId())
                .requesterId(request.getRequesterId())
                .created(request.getCreated())
                .status(request.getStatus())
                .build();
    }
}
