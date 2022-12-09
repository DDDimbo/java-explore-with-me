package ru.practicum.explorewithme.event;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.category.Category;
import ru.practicum.explorewithme.category.CategoryDto;
import ru.practicum.explorewithme.category.CategoryMapper;
import ru.practicum.explorewithme.enums.State;
import ru.practicum.explorewithme.event.dto.EventFullDto;
import ru.practicum.explorewithme.event.dto.EventShortDto;
import ru.practicum.explorewithme.event.dto.Location;
import ru.practicum.explorewithme.event.dto.NewEventDto;
import ru.practicum.explorewithme.user.User;
import ru.practicum.explorewithme.user.UserMapper;
import ru.practicum.explorewithme.user.dto.UserShortDto;

import java.time.LocalDateTime;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EventMapper {

    public static Event toEventCreate(User initiator, Category category, NewEventDto eventDto) {
        return Event.builder()
                .annotation(eventDto.getAnnotation())
                .category(category)
                .createdOn(LocalDateTime.now())
                .description(eventDto.getDescription())
                .eventDate(eventDto.getEventDate())
                .initiator(initiator)
                .paid(eventDto.getPaid())
                .confirmedRequests(0L)
                .participantLimit(eventDto.getParticipantLimit())
                .state(State.PENDING)
                .requestModeration(eventDto.getRequestModeration())
                .title(eventDto.getTitle())
                .locationLat(eventDto.getLocation().getLocationLat())
                .locationLon(eventDto.getLocation().getLocationLon())
                .build();
    }

    public static EventFullDto toEventFullDto(Event event, Long views) {
        Location location = new Location(event.getLocationLat(), event.getLocationLon());
        CategoryDto categoryDto = CategoryMapper.toCategoryDto(event.getCategory());
        UserShortDto userShortDto = UserMapper.toUserShortDto(event.getInitiator());
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(categoryDto)
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .initiator(userShortDto)
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .state(event.getState())
                .requestModeration(event.getRequestModeration())
                .title(event.getTitle())
                .location(location)
                .views(views)
                .build();
    }

    public static EventFullDto toEventFullDto(Event event) {
        Location location = new Location(event.getLocationLat(), event.getLocationLon());
        CategoryDto categoryDto = CategoryMapper.toCategoryDto(event.getCategory());
        UserShortDto userShortDto = UserMapper.toUserShortDto(event.getInitiator());
        return EventFullDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(categoryDto)
                .confirmedRequests(event.getConfirmedRequests())
                .createdOn(event.getCreatedOn())
                .description(event.getDescription())
                .eventDate(event.getEventDate())
                .initiator(userShortDto)
                .paid(event.getPaid())
                .participantLimit(event.getParticipantLimit())
                .publishedOn(event.getPublishedOn())
                .state(event.getState())
                .requestModeration(event.getRequestModeration())
                .title(event.getTitle())
                .location(location)
                .views(0L)
                .build();
    }

    public static EventShortDto toEventShortDto(Event event, Long views) {
        CategoryDto categoryDto = CategoryMapper.toCategoryDto(event.getCategory());
        UserShortDto userShortDto = UserMapper.toUserShortDto(event.getInitiator());
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(categoryDto)
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .initiator(userShortDto)
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(views)
                .build();
    }

    public static EventShortDto toEventShortDto(Event event) {
        CategoryDto categoryDto = CategoryMapper.toCategoryDto(event.getCategory());
        UserShortDto userShortDto = UserMapper.toUserShortDto(event.getInitiator());
        return EventShortDto.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .category(categoryDto)
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate())
                .initiator(userShortDto)
                .paid(event.getPaid())
                .title(event.getTitle())
                .views(0L)
                .build();
    }

}
