package pl.pni.realestatestats.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.pni.realestatestats.model.dto.AnswerDto;
import pl.pni.realestatestats.service.HouseSearchService;

@RestController
@RequestMapping("/api/real-estates-stats")
public class RealEstateController {

    private HouseSearchService houseSearchService;

    public RealEstateController(HouseSearchService houseSearchService) {
        this.houseSearchService = houseSearchService;
    }

    @GetMapping("/{regionId}")
    public AnswerDto getStats(@PathVariable String regionId,
                              @RequestParam String size,
                              @RequestParam int rooms,
                              @RequestParam String types,
                              @RequestParam String dateSince,
                              @RequestParam String dateUntil) {

        Double avgValue = houseSearchService
                .findAvgPrice(regionId, size, rooms, types, dateSince, dateUntil);

        return new AnswerDto(avgValue);
    }
}
