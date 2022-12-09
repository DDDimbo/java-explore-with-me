package ru.practicum.explorewithme.user.service;

import ru.practicum.explorewithme.user.dto.UserDto;

import java.util.List;

public interface UserServiceAdmin {

    UserDto create(UserDto userDto);

    List<UserDto> findAllByParam(List<Long> ids, Integer from, Integer size);

    List<UserDto> findAll(Integer from, Integer size);

    void deleteById(Long userId);
}
