package ru.practicum.explorewithme.request.service;

import ru.practicum.explorewithme.request.ParticipationRequestDto;

import java.util.List;

public interface RequestServicePrivate {

    ParticipationRequestDto create(Long userId, Long eventId);

    ParticipationRequestDto cancel(Long userId, Long requestId);

    List<ParticipationRequestDto> findAllInfoAboutOwnerRequests(Long userId);

}
