package pl.pni.realestatestats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.pni.realestatestats.model.House;
import pl.pni.realestatestats.model.Region;

import java.time.LocalDateTime;
import java.util.List;

public interface HouseRepository extends JpaRepository<House, Long>, JpaSpecificationExecutor<House> {

    @Query("SELECT h.houseId FROM House h WHERE h.region= :regionId")
    List<String> findHouseIdsByRegion(Region regionId);

    House findByHouseId(String houseId);

//    @Query("SELECT AVG(h.price) FROM House h WHERE h.size= :size and h.region= :regionId " +
//            "and h.rooms= :rooms and h.type IN(:types) and h.addedDate BETWEEN :dateFrom AND :dateTo")
//    Double findAvgPrice(@Param("regionId") Region regionId, @Param("size") String size,
//                        @Param("rooms") int rooms, @Param("types") List<String> types,
//                        @Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateUntil);
}
