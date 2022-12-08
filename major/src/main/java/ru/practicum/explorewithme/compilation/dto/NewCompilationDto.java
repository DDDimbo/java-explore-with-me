package ru.practicum.explorewithme.compilation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;


@Getter
@Builder
@ToString
public class NewCompilationDto {

    @NotNull
    private Boolean pinned;

    @NotBlank
    private String title;

    @NotNull
    @JsonProperty("events")
    private Set<Long> eventIds;

}
