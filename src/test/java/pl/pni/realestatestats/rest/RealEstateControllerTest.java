package pl.pni.realestatestats.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.pni.realestatestats.model.House;
import pl.pni.realestatestats.model.Region;
import pl.pni.realestatestats.repository.HouseRepository;
import pl.pni.realestatestats.repository.RegionRepository;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RealEstateControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private HouseRepository houseRepository;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void shouldReturnAvgPrice() throws Exception {
        Region region = new Region("SL_PN","Slaskie polnoc");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = formatter.parse("2023-01-01");

        regionRepository.save(region);
        houseRepository.save(new House("test-1", 3, 1000, "M", "flat",date, region));
        houseRepository.save(new House("test-2", 3, 2000, "M", "detached_house",date, region));
        houseRepository.save(new House("test-3", 1, 3000, "S", "flat",date, region));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/real-estates-stats/SL_PN"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.avgValue").value(2000));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/real-estates-stats/SL_PN?size=S"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.avgValue").value(3000));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/real-estates-stats/SL_PN?size=M&types=flat,terraced_house&dateSince=20100101&dateUntil=20250101"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.avgValue").value(1000));

    }
}
