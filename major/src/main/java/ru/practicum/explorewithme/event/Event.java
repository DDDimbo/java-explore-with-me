package ru.practicum.explorewithme.event;

import lombok.*;
import ru.practicum.explorewithme.category.Category;
import ru.practicum.explorewithme.compilation.Compilation;
import ru.practicum.explorewithme.enums.State;
import ru.practicum.explorewithme.request.Request;
import ru.practicum.explorewithme.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String annotation;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @NotNull
    @Column(name = "created_on")
    private LocalDateTime createdOn;

    @NotNull
    private String description;

    @NotNull
    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;

    @NotNull
    private Boolean paid;

    @NotNull
    @Column(name = "participant_limit")
    private Long participantLimit = 0L;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private State state;

    @NotNull
    @Column(name = "request_moderation")
    private Boolean requestModeration;

    @NotNull
    private String title;

    @NotNull
    @Column(name = "location_lat")
    private Float locationLat;

    @NotNull
    @Column(name = "location_lon")
    private Float locationLon;

    @PositiveOrZero
    private Long confirmedRequests = 0L;

    public Event(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
