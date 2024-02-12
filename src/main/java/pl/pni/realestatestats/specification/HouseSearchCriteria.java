package pl.pni.realestatestats.specification;

import lombok.Getter;
import lombok.Setter;
import pl.pni.realestatestats.constants.SearchOperation;

@Getter
@Setter
public class HouseSearchCriteria {
    private String key;
    private Object value;
    private Object value2;
    private SearchOperation operation;

    public HouseSearchCriteria() {

    }

    public HouseSearchCriteria(String key, Object value, Object value2, SearchOperation operation) {
        this.key = key;
        this.value = value;
        this.value2 = value2;
        this.operation = operation;
    }

    public HouseSearchCriteria(String key, Object value, SearchOperation operation) {
        this.key = key;
        this.value = value;
        this.operation = operation;
    }
}
