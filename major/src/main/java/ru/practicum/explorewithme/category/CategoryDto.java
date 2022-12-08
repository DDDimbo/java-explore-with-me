package ru.practicum.explorewithme.category;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.practicum.explorewithme.markerinterface.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@ToString
@Getter
@Builder
public class CategoryDto {

    @Positive(groups = Update.class)
    private Long id;

    @NotBlank
    private String name;
}
