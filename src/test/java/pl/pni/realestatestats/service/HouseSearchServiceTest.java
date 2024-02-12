package pl.pni.realestatestats.service;


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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class HouseSearchServiceTest {

    @Autowired
    private HouseSearchService houseSearchService;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private HouseRepository houseRepository;

    @Test
    public void should_find_avg_price() throws ParseException {
        Region region = new Region("SL_PN","Slaskie polnoc");
        regionRepository.save(region);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse("2023-01-01");

        houseRepository.save(new House("test-1", 3, 1000, "M", "flat",date, region));
        houseRepository.save(new House("test-2", 3, 2000, "M", "detached_house",date, region));
        houseRepository.save(new House("test-3", 1, 3000, "S", "flat",date, region));

        Double avgPrice = houseSearchService.findAvgPrice(region.getRegionId(), Optional.of("M"), Optional.of(3),
                Optional.of("flat,detached_house"), Optional.of("20100101"), Optional.of("20250101"));

        assertEquals(1500, avgPrice);

        avgPrice = houseSearchService.findAvgPrice(region.getRegionId(), Optional.empty(), Optional.empty(),
                Optional.of("flat,detached_house"), Optional.of("20100101"), Optional.of("20250101"));

        assertEquals(2000, avgPrice);
    }
}
