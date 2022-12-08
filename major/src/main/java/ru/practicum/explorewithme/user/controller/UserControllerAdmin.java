package ru.practicum.explorewithme.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.markerinterface.Create;
import ru.practicum.explorewithme.user.dto.UserDto;
import ru.practicum.explorewithme.user.service.UserServiceAdmin;

import java.util.Collection;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(path = "/admin/users")
public class UserControllerAdmin {

    private final UserServiceAdmin userService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDto create(@Validated(Create.class) @RequestBody UserDto userDto) {
        log.info("Create user with email {}.", userDto.getEmail());
        return userService.create(userDto);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<UserDto> findAllByParam(
            @RequestParam(value = "ids", required = false) List<Long> ids,
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (ids == null) {
            log.info("Find all users from {} and with size {}", from, size);
            return userService.findAll(from, size);
        }
        log.info("Find all users in ids, from {} and with size {}", from, size);
        return userService.findAllByParam(ids, from, size);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable Long userId) {
        log.info("Delete user with id {}.", userId);
        userService.deleteById(userId);
    }
}
