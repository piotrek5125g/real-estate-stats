package pl.pni.realestatestats.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import pl.pni.realestatestats.constants.SearchOperation;
import pl.pni.realestatestats.model.House;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HouseSpecification implements Specification<House> {

    private List<HouseSearchCriteria> searchCriteriaList = new ArrayList<>();

    public void add(HouseSearchCriteria criteria) {
        searchCriteriaList.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<House> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        for (HouseSearchCriteria criteria : searchCriteriaList) {
            if (criteria.getOperation().equals(SearchOperation.EQUAL)) {
                predicates.add(
                        builder.equal(
                                root.get(criteria.getKey()),
                                criteria.getValue())
                );
            } else if (criteria.getOperation().equals(SearchOperation.IN)) {
                predicates.add(
                        builder.in(
                                root.get(criteria.getKey())
                        ).value(criteria.getValue())
                );
            } else if (criteria.getOperation().equals(SearchOperation.BETWEEN)) {
                predicates.add(
                        builder.between(
                                root.get(criteria.getKey()),
                                (LocalDateTime)criteria.getValue(),
                                (LocalDateTime)criteria.getValue2())
                );
            } else if (criteria.getOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
                predicates.add(
                        builder.lessThanOrEqualTo(
                                root.get(criteria.getKey()),
                                (LocalDateTime) criteria.getValue()
                ));
            } else if (criteria.getOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
                predicates.add(
                        builder.greaterThanOrEqualTo(
                                root.get(criteria.getKey()),
                                (LocalDateTime) criteria.getValue()
                        )
                );
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
