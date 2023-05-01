package ru.practicum.explorewithme.estimation;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.event.Event;
import ru.practicum.explorewithme.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(EstimationKey.class)
@Table(name = "estimations")
public class Estimation {

    @Id
    @NotNull
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Id
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private Integer estimation;
}
