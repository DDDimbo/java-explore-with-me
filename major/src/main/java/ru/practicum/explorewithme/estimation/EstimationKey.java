package ru.practicum.explorewithme.estimation;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class EstimationKey implements Serializable {


    private Long event;

    private Long user;
}
