package pl.pni.realestatestats.service;

import org.springframework.stereotype.Service;
import pl.pni.realestatestats.model.Region;
import pl.pni.realestatestats.repository.HouseRepository;
import pl.pni.realestatestats.repository.RegionRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HouseSearchService {
    private HouseRepository houseRepository;
    private RegionRepository regionRepository;

    public HouseSearchService(HouseRepository houseRepository, RegionRepository regionRepository) {
        this.houseRepository = houseRepository;
        this.regionRepository = regionRepository;
    }

    public Double findAvgPrice(String regionId, String size, int rooms,
                               String types, String dateSince, String dateUntil) {
        Region region = regionRepository.findById(regionId).orElseThrow();
        List<String> typesAsList = List.of(types.split(","));

        LocalDateTime localDateTimeFrom = parseString(dateSince,0,0);
        LocalDateTime localDateTimeUntil = parseString(dateUntil,23,59);

        return houseRepository.findAvgPrice(region, size, rooms,typesAsList,
                localDateTimeFrom, localDateTimeUntil);
    }

    private LocalDateTime parseString(String input, int hour, int minute) {
        int year = Integer.parseInt(input.substring(0,4));
        int month = Integer.parseInt(input.substring(4,6));
        int day = Integer.parseInt(input.substring(6,8));

        return LocalDateTime.of(year, month, day,hour,minute);
    }
}
