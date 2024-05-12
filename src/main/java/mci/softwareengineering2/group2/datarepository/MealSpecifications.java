package mci.softwareengineering2.group2.datarepository;

import org.springframework.data.jpa.domain.Specification;
import mci.softwareengineering2.group2.data.Meal;

public class MealSpecifications {
    public static Specification<Meal> isDeleted(Boolean deleted) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("deleted"), deleted);
    }
    
    public static Specification<Meal> isName(String name) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("name"), name);
    }
}
