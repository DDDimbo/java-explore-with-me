package ru.practicum.explorewithme.utility;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MyConstants {

    public static final String SERVICE = "ewm-main-service";
}
