package pl.pni.realestatestats.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.pni.realestatestats.model.external.ExternalApiResponse;
import pl.pni.realestatestats.model.external.HouseExternal;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;

@Service
public class ExternalApiService {

    private WebClient webClient;
    private ObjectMapper mapper = new ObjectMapper();
    private EmailSenderService emailSenderService;

    @Value("${external.api.max.retry}")
    private int maxRetry;

    @Value("${external.api.base.url}")
    private String baseUrl;


    public ExternalApiService(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;

        webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    private String getResponseAsString(String regionId, int pageNum) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/real-estates/{region}")
                        .queryParam("page", pageNum)
                        .build(regionId))
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.backoff(maxRetry, Duration.ofSeconds(2))
                        .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
                            emailSenderService.sendErrorEmailToAdministrator("External Api connection error",
                                    "The application could not connect to real estate API after max retry attempts");
                            throw new ServiceException("Failed to connect to API after max retries");
                        }))
                .block();
    }

    public List<HouseExternal> fetchHousesForRegionAndPage(String regionId, int pageNum) throws JsonProcessingException {
        String response = getResponseAsString(regionId, pageNum);
        ExternalApiResponse ers = mapper.readValue(response, ExternalApiResponse.class);

        return ers.getData();
    }

    public int fetchTotalPages(String regionId) throws JsonProcessingException {
        String response = getResponseAsString(regionId, 1);
        ExternalApiResponse ers = mapper.readValue(response, ExternalApiResponse.class);

        return ers.getTotalPages();
    }
}
