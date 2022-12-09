package ru.practicum.explorewithme.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static ru.practicum.explorewithme.utility.MyConstants.formatter;

@Service
@RequiredArgsConstructor
public class StatWebClient {

    private final WebClient client;

    @Autowired
    public StatWebClient(@Value("${explorewithme-statistic.url}") String serverUrl) {
        final DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(serverUrl);
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        client = WebClient
                .builder()
                .uriBuilderFactory(factory)
                .defaultHeader("Content-Type", "application/json")
                .baseUrl(serverUrl)
                .build();
    }

    public void save(EndpointHit endpointHit) {
        client.post()
                .uri("/hit")
                .body(Mono.just(endpointHit), EndpointHit.class)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        error -> Mono.error(new RuntimeException("API not found")))
                .onStatus(HttpStatus::is5xxServerError,
                        error -> Mono.error(new RuntimeException("Server is not responding")))
                .bodyToMono(EndpointHit.class)
                .block();
    }

    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, Set<String> uris, boolean unique) {
        return client.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/stats")
                        .queryParam("start", encodeLocalDateTimeValue(start))
                        .queryParam("end", encodeLocalDateTimeValue(end))
                        .queryParam("uris", uris)
                        .queryParam("unique", unique)
                        .build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        error -> Mono.error(new RuntimeException("API not found")))
                .onStatus(HttpStatus::is5xxServerError,
                        error -> Mono.error(new RuntimeException("Server is not responding")))
                .bodyToMono(new ParameterizedTypeReference<List<ViewStats>>() {
                })
                .block();
    }

    private String encodeLocalDateTimeValue(LocalDateTime time) {
        return URLEncoder.encode(time.format(formatter), StandardCharsets.UTF_8);
    }

}
