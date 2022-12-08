package ru.practicum.explorewithme.statistic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.statistic.dto.EndpointHit;
import ru.practicum.explorewithme.statistic.dto.ViewStats;
import ru.practicum.explorewithme.statistic.service.StatService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatController {

    private final StatService statsService;


    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.OK)
    public void createStat(@RequestBody @Valid EndpointHit endpointHit) {
        log.info("Save statistics from {} and with uri={}, ip={}",
                endpointHit.getApp(),
                endpointHit.getUri(),
                endpointHit.getIp()
        );
        statsService.createStat(endpointHit);
    }

    @GetMapping("/stats")
    @ResponseStatus(HttpStatus.OK)
    public List<ViewStats> findAllByParams(@RequestParam
                                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                           LocalDateTime start,
                                           @RequestParam
                                           @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                           LocalDateTime end,
                                           @RequestParam(value = "uris", required = false) List<String> uris,
                                           @RequestParam(value = "unique", defaultValue = "false") Boolean unique,
                                           HttpServletRequest request) {
        log.info("Find all statistics by params: start={}, end={}, ip={}, uri={}",
                start, end, request.getRemoteAddr(), request.getRequestURI());
        return statsService.findAllByParams(start, end, uris, unique);
    }
}
