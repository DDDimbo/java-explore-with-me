package ru.practicum.explorewithme.client;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder
@ToString
@Getter
public class EndpointHit {

    private Long id;

    private String app;

    private String uri;

    private String ip;

    private LocalDateTime timestamp;

}
