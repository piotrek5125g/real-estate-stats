package pl.pni.realestatestats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.pni.realestatestats.model.House;
import pl.pni.realestatestats.model.Region;

import java.time.LocalDateTime;
import java.util.List;

public interface HouseRepository extends JpaRepository<House, String>, JpaSpecificationExecutor<House> {

    @Query("SELECT h.houseId FROM House h WHERE h.region= :regionId")
    List<String> findHouseIdsByRegion(Region regionId);

    House findByHouseId(String houseId);

}
