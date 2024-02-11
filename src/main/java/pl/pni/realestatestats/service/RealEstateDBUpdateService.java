package pl.pni.realestatestats.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.pni.realestatestats.model.House;
import pl.pni.realestatestats.model.Region;
import pl.pni.realestatestats.model.external.HouseExternal;
import pl.pni.realestatestats.repository.HouseRepository;
import pl.pni.realestatestats.repository.RegionRepository;
import pl.pni.realestatestats.util.SizeUtil;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RealEstateDBUpdateService {

    private RegionRepository regionRepository;
    private HouseRepository houseRepository;
    private ExternalApiService externalApiService;
    private Date currentDate;

    public RealEstateDBUpdateService(RegionRepository regionRepository, HouseRepository houseRepository,
                                     ExternalApiService externalApiService) {
        this.regionRepository = regionRepository;
        this.houseRepository = houseRepository;
        this.externalApiService = externalApiService;
    }

    public void performRealEstateDBUpdateProcedure() {
        currentDate = new Date();

        List<Region> regions = regionRepository.findAll();

        for (Region region : regions) {
            //We need this list to determine if the house is not already in DB
            List<String> regionHousesInDb = houseRepository.findHouseIdsByRegion(region);

            int numOfPages = 0;

            try {
                numOfPages = externalApiService.fetchTotalPages(region.getRegionId());
            } catch (JsonProcessingException jpe) {
                log.error("Json processing error when fetching totalPages for the region {}", region.getRegionId(), jpe);
                continue;
            }

            int addedHouses = 0;

            for (int i = 1; i <= numOfPages; i++) {
                try {
                    List<HouseExternal> housesFromApi = externalApiService
                            .fetchHousesForRegionAndPage(region.getRegionId(), i);

                    //filter out the list - we only need houses which are not already in DB
                    List<House> housesNotInDb = getHousesNotInDb(regionHousesInDb, housesFromApi, region);

                    if (!housesNotInDb.isEmpty()) {
                        houseRepository.saveAll(housesNotInDb);
                        addedHouses += housesNotInDb.size();
                    }
                } catch (JsonProcessingException jpe) {
                    log.error("Json processing error on page {} for the region {}", i, region.getRegionId(), jpe);
                }
            }
            log.info("Saved {} new houses in region: {}", addedHouses, region.getRegionId());
        }
    }

    private List<House> getHousesNotInDb(List<String>  regionHousesInDb, List<HouseExternal> housesFromApi, Region region) {
        return housesFromApi.stream()
                .filter(house -> !regionHousesInDb.contains(house.getId()))
                .map(external -> new House(external.getId(), external.getRooms(), external.getPrice(),
                        SizeUtil.convertArea(external.getArea()), external.getType(), currentDate, region))
                .collect(Collectors.toList());
    }

}
