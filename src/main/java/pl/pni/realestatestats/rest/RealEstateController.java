package pl.pni.realestatestats.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.pni.realestatestats.model.dto.AnswerDto;
import pl.pni.realestatestats.service.HouseSearchService;

import java.util.Optional;

@RestController
@RequestMapping("/api/real-estates-stats")
public class RealEstateController {

    private HouseSearchService houseSearchService;

    public RealEstateController(HouseSearchService houseSearchService) {
        this.houseSearchService = houseSearchService;
    }

    @GetMapping("/{regionId}")
    public AnswerDto getAvgPrice(@PathVariable String regionId,
                              @RequestParam Optional<String> size,
                              @RequestParam Optional<Integer> rooms,
                              @RequestParam Optional<String> types,
                              @RequestParam Optional<String> dateSince,
                              @RequestParam Optional<String> dateUntil) {

        Double avgValue = houseSearchService
                .findAvgPrice(regionId, size, rooms, types, dateSince, dateUntil);

        return new AnswerDto(avgValue);
    }
}
