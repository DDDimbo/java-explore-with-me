package ru.practicum.explorewithme.compilation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.compilation.dto.CompilationDto;
import ru.practicum.explorewithme.compilation.dto.NewCompilationDto;
import ru.practicum.explorewithme.event.Event;
import ru.practicum.explorewithme.event.EventMapper;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CompilationMapper {

    public static Compilation toCompilationFromNew(NewCompilationDto newCompilationDto, Set<Event> eventSet) {
        boolean pin = false;
        if (newCompilationDto.getPinned() != null)
            pin = newCompilationDto.getPinned();
        return Compilation.builder()
                .title(newCompilationDto.getTitle())
                .pinned(pin)
                .events(eventSet)
                .build();
    }

    public static CompilationDto toCompilationDto(Compilation compilation) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.getPinned())
                .events(compilation.getEvents().stream()
                        .map(EventMapper::toEventShortDto)
                        .collect(Collectors.toSet()))
                .build();
    }
}
