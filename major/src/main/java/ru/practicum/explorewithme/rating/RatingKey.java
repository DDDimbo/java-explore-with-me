package ru.practicum.explorewithme.rating;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RatingKey implements Serializable {

//    @Column(name = "comment_id")
    private Long commentIdKey;

//    @Column(name = "user_id")
    private Long userIdKey;

}
