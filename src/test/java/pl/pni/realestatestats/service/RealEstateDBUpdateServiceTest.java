package pl.pni.realestatestats.service;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import pl.pni.realestatestats.model.House;
import pl.pni.realestatestats.model.Region;
import pl.pni.realestatestats.repository.HouseRepository;
import pl.pni.realestatestats.repository.RegionRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        houseRepository.deleteAll();
        regionRepository.deleteAll();

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

    @Test
    public void should_only_add_new_houses_to_db() throws ParseException {
        RealEstateDBUpdateService service = new RealEstateDBUpdateService(regionRepository,
                houseRepository, externalApiService);
        houseRepository.deleteAll();
        regionRepository.deleteAll();

        Region region = new Region("ZPOM","\"Zachodniopomorskie\"");
        regionRepository.save(region);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date pastDate = formatter.parse("2010-01-01");

        houseRepository.save(new House("test-21", 3, 1000, "M", "flat", pastDate, region));

        stubFor(get(urlEqualTo("/api/real-estates/ZPOM?page=1"))
                .willReturn(aResponse().withBodyFile("realestate_zpom.json")));

        service.performRealEstateDBUpdateProcedure();

        assertEquals(pastDate, houseRepository.findByHouseId("test-21").getAddedDate());
    }
}
