package pl.pni.realestatestats.service;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import pl.pni.realestatestats.model.Region;
import pl.pni.realestatestats.repository.HouseRepository;
import pl.pni.realestatestats.repository.RegionRepository;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@WireMockTest(httpPort = 8090)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RealEstateDBUpdateServiceTest {

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private ExternalApiService externalApiService;

    @Test
    public void should_add_houses_to_db() {
        RealEstateDBUpdateService service = new RealEstateDBUpdateService(regionRepository,
                houseRepository, externalApiService);

        stubFor(get(urlEqualTo("/api/real-estates/SL_PN?page=1"))
                .willReturn(aResponse().withBodyFile("realestate_sl1.json")));
        stubFor(get(urlEqualTo("/api/real-estates/SL_PN?page=2"))
                .willReturn(aResponse().withBodyFile("realestate_sl2.json")));
        stubFor(get(urlEqualTo("/api/real-estates/SL_PN?page=3"))
                .willReturn(aResponse().withBodyFile("realestate_sl3.json")));

        stubFor(get(urlEqualTo("/api/real-estates/LUBL_INNE?page=1"))
                .willReturn(aResponse().withBodyFile("realestate_empty.json")));

        regionRepository.save(new Region("SL_PN","Slaskie polnoc"));
        regionRepository.save(new Region("LUBL_INNE","Lubelskie inne"));

        service.performRealEstateDBUpdateProcedure();

        assertEquals(7, houseRepository.findAll().size());
    }
}
