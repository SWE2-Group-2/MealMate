package mci.softwareengineering2.group2.datarepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import mci.softwareengineering2.group2.data.Meal;

public interface MealRepository extends JpaRepository<Meal, Long>, JpaSpecificationExecutor<Meal> {

    Meal findMealByName(String username);

}