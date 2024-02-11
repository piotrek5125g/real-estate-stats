package pl.pni.realestatestats.model.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class HouseExternal {

    private String id;
    private String type;
    private double price;
   // private String description;
    private double area;
    private int rooms;

}
