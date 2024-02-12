package pl.pni.realestatestats.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "houses")
public class House {

    //@Id
    //private String houseId;
   // @EmbeddedId
   // private HouseEmbeddedKey houseEmbeddedKey;

    @Id
    private String houseId;
    @Column
    private int rooms;
    @Column
    private double price;
    @Column
    private String size;
    @Column
    private String type;
    @Column
    private Date addedDate;

    @ManyToOne
    @JoinColumn(name="region_id", referencedColumnName = "regionId", nullable=false)
    private Region region;

}
