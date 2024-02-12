package pl.pni.realestatestats.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.pni.realestatestats.model.external.HouseExternal;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@WireMockTest(httpPort = 8090)
public class ExternalApiServiceTest {

    @Autowired
    ExternalApiService externalApiService;

    @Test
    public void should_return_total_pages() throws JsonProcessingException {
        stubFor(get(urlEqualTo("/api/real-estates/SL_PN?page=1"))
                .willReturn(aResponse().withBodyFile("realestate.json")));

        int totalPages = externalApiService.fetchTotalPages("SL_PN");
        assertEquals(87, totalPages);
    }

    @Test
    public void should_return_houses_for_region() throws JsonProcessingException {
        stubFor(get(urlEqualTo("/api/real-estates/LUBL_INNE?page=8"))
                .willReturn(aResponse().withBodyFile("realestate.json")));

        List<HouseExternal> houses = externalApiService.fetchHousesForRegionAndPage("LUBL_INNE",8);

        assertEquals(5, houses.size());
    }

    @Test
    public void should_throw_on_connection_error() {
        stubFor(get(urlEqualTo("/api/real-estates/LUBSK?page=1"))
                .willReturn(aResponse().withStatus(500)));

        assertThrows(ServiceException.class, () -> {
            externalApiService.fetchTotalPages("LUBSK");
        });
    }
}
