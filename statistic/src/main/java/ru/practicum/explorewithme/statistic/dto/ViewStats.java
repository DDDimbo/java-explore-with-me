package ru.practicum.explorewithme.statistic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class ViewStats {

    private String app;

    private String uri;

    private Long hits;

    public ViewStats() {
    }
}
