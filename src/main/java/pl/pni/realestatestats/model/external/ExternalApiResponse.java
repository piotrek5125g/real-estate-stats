package pl.pni.realestatestats.model.external;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExternalApiResponse {
    public int totalPages;

    public List<HouseExternal> data;
}
