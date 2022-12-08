package ru.practicum.explorewithme.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MyConstants {

    public static final String SERVICE = "ewm-main-service";

    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

}
