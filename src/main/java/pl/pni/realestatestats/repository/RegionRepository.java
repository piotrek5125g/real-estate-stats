package pl.pni.realestatestats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.pni.realestatestats.model.Region;

public interface RegionRepository extends JpaRepository<Region,String> {
}
