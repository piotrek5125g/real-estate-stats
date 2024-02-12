package pl.pni.realestatestats.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "regions")
public class Region {
    public Region(){

    }

    public Region(String regionId, String regionName) {
        this.regionId = regionId;
        this.regionName = regionName;
    }

    @Id
    private String regionId;
    @Column
    private String regionName;

    @OneToMany(mappedBy="region")
    private Set<House> houses;

}
