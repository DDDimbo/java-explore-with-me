package ru.practicum.explorewithme.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.explorewithme.user.User;
import ru.practicum.explorewithme.user.UserMapper;
import ru.practicum.explorewithme.user.UserRepository;
import ru.practicum.explorewithme.user.dto.UserDto;
import ru.practicum.explorewithme.utility.FromSizeRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceAdminImpl implements UserServiceAdmin {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        User result = userRepository.save(UserMapper.toUser(userDto));
        return UserMapper.toUserDto(result);
    }

    @Override
    public List<UserDto> findAll(Integer from, Integer size) {
        Pageable pageable = FromSizeRequest.of(from, size);
        return userRepository.findAll(pageable).stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> findAllByParam(List<Long> ids, Integer from, Integer size) {
        Pageable pageable = FromSizeRequest.of(from, size);
        return userRepository.findALlByParam(ids, pageable).stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }
}
