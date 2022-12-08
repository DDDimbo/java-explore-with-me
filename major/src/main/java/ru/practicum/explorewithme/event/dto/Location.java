package ru.practicum.explorewithme.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@ToString
public class Location {

    @NotNull
    @JsonProperty("lat")
    private Float locationLat;

    @NotNull
    @JsonProperty("lon")
    private Float locationLon;

}
