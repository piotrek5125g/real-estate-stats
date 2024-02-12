package pl.pni.realestatestats.service;

import org.springframework.stereotype.Service;
import pl.pni.realestatestats.constants.SearchOperation;
import pl.pni.realestatestats.model.House;
import pl.pni.realestatestats.model.Region;
import pl.pni.realestatestats.repository.HouseRepository;
import pl.pni.realestatestats.repository.RegionRepository;
import pl.pni.realestatestats.specification.HouseSearchCriteria;
import pl.pni.realestatestats.specification.HouseSpecification;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HouseSearchService {
    private HouseRepository houseRepository;
    private RegionRepository regionRepository;

    public HouseSearchService(HouseRepository houseRepository, RegionRepository regionRepository) {
        this.houseRepository = houseRepository;
        this.regionRepository = regionRepository;
    }

    public Double findAvgPrice(String regionId, Optional<String> size, Optional<Integer> rooms,
                               Optional<String> types, Optional<String> dateSince, Optional<String> dateUntil) {

        HouseSpecification specification = new HouseSpecification();

        Region region = regionRepository.findById(regionId).orElseThrow();
        specification.add(new HouseSearchCriteria("region", region, SearchOperation.EQUAL));

        if (size.isPresent()) {
            specification.add(new HouseSearchCriteria("size",size.get(), SearchOperation.EQUAL));
        }

        if (rooms.isPresent()) {
            specification.add(new HouseSearchCriteria("rooms",rooms.get(), SearchOperation.EQUAL));
        }

        if (types.isPresent()) {
            List<String> typesAsList = List.of(types.get().split(","));
            specification.add(new HouseSearchCriteria("type", typesAsList, SearchOperation.IN));
        }

        if (dateSince.isPresent() && dateUntil.isPresent()) {
            LocalDateTime from = parseString(dateSince.get(),0,0);
            LocalDateTime to = parseString(dateUntil.get(),23,59);
            specification.add(new HouseSearchCriteria("addedDate",from,to, SearchOperation.BETWEEN));
        } else if (dateSince.isPresent() && dateUntil.isEmpty()) {
            LocalDateTime from = parseString(dateSince.get(),0,0);
            specification.add(new HouseSearchCriteria("addedDate",from, SearchOperation.GREATER_THAN_EQUAL));
        } else if (dateSince.isEmpty() && dateUntil.isPresent()) {
            LocalDateTime to = parseString(dateUntil.get(),23,59);
            specification.add(new HouseSearchCriteria("addedDate",to, SearchOperation.LESS_THAN_EQUAL));
        }

        List<House> houses = houseRepository.findAll(specification);

        BigDecimal sum = new BigDecimal(0);
        for (House house : houses) {
            sum = sum.add(BigDecimal.valueOf(house.getPrice()));
        }

        if (houses.size() > 0) {
            return sum.divide(BigDecimal.valueOf(houses.size()), RoundingMode.HALF_UP).doubleValue();
        } else {
            return null;
        }
    }

    private LocalDateTime parseString(String input, int hour, int minute) {
        int year = Integer.parseInt(input.substring(0,4));
        int month = Integer.parseInt(input.substring(4,6));
        int day = Integer.parseInt(input.substring(6,8));

        return LocalDateTime.of(year, month, day,hour,minute);
    }
}
