package ru.practicum.explorewithme.event.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class EventConfirmedRequestDto {

    private Long id;

    private Long participantLimit;

    private Long confirmedRequests;

}
