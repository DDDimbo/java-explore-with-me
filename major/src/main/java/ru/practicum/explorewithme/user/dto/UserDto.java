package ru.practicum.explorewithme.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.practicum.explorewithme.markerinterface.Create;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Getter
@ToString
public class UserDto {

    private Long id;

    @NotBlank(groups = Create.class)
    private String name;

    @Email(groups = Create.class)
    @NotNull(groups = Create.class)
    private String email;

}
