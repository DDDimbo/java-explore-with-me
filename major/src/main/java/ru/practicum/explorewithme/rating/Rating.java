package ru.practicum.explorewithme.rating;

import lombok.*;
import lombok.extern.jackson.Jacksonized;
import ru.practicum.explorewithme.comment.Comment;
import ru.practicum.explorewithme.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Builder
@AllArgsConstructor
@EqualsAndHashCode
@Jacksonized
@ToString
@Setter
@Getter
@Entity
@Table(name = "ratings")
public class Rating {

    @EmbeddedId
    private RatingKey id;

    @NotNull
    @ManyToOne
    @MapsId("commentIdKey")
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @NotNull
    @ManyToOne
    @MapsId("userIdKey")
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Column(name = "like_dislike")
    private Boolean likeDislike;

    public Rating() {

    }

}
