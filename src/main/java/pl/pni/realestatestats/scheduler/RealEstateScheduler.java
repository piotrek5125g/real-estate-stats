package pl.pni.realestatestats.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.pni.realestatestats.service.RealEstateDBUpdateService;

@Component
public class RealEstateScheduler {

    private RealEstateDBUpdateService realEstateDBUpdateService;

    public RealEstateScheduler(RealEstateDBUpdateService realEstateDBUpdateService) {
        this.realEstateDBUpdateService = realEstateDBUpdateService;
    }

    @Scheduled(cron = "0 0 21 * * *")
    public void performRealEstateDBUpdate() {
        realEstateDBUpdateService.performRealEstateDBUpdateProcedure();
    }
}
