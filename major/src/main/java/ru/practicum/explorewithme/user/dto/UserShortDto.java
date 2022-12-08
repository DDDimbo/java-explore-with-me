package ru.practicum.explorewithme.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Builder
@Getter
@ToString
public class UserShortDto {

    @Positive
    private Long id;

    @NotBlank
    private String name;

}
