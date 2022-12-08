package ru.practicum.explorewithme.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.practicum.explorewithme.enums.State;
import ru.practicum.explorewithme.enums.Status;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class ParticipationRequestDto {

    @Positive
    private Long id;

    @PastOrPresent
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    @Positive
    @JsonProperty("event")
    private Long eventId;

    @Positive
    @JsonProperty("requester")
    private Long requesterId;

    @NotNull
    private Status status;

}
