package mci.softwareengineering2.group2.datarepository;

import org.springframework.data.jpa.domain.Specification;
import mci.softwareengineering2.group2.data.Category;

public class CategorySpecifications {
    public static Specification<Category> isName(String name) {
        return (root, query, criteriaBuilder) ->
            criteriaBuilder.equal(root.get("name"), name);
    }
}
