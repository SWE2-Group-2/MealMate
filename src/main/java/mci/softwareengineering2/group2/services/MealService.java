package mci.softwareengineering2.group2.services;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import mci.softwareengineering2.group2.data.Meal;
import mci.softwareengineering2.group2.data.Category;
import mci.softwareengineering2.group2.datarepository.CategoryRepository;
import mci.softwareengineering2.group2.datarepository.MealRepository;

@Service
@Transactional
public class MealService {

    @Autowired
    private final MealRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }
    
    public Meal updateMealCategories(Long mealId, List<Long> categoryIds) {
        Meal meal = repository.findById(mealId).orElse(null);
        if (meal != null) {
            // Delete all existing Category associations where the meal is associated
            List<Category> existingCategories = meal.getCategory();
            for (Category category : existingCategories) {
                category.getMeals().remove(meal);
            }
            // Fetch Category entities based on provided IDs
            List<Category> categories = categoryRepository.findAllById(categoryIds);
            // Update Meal's category association
            meal.setCategory(categories);
            for (Category category : categories) {
                // Update Category's meal association
                if (!category.getMeals().contains(meal)) {
                   category.getMeals().add(meal);
                }
            }
            // Save the updated Category entities to synchronize changes
            categoryRepository.saveAll(categories);
            // Save the updated Meal entity to synchronize changes
            return repository.save(meal);
        }
        return null;
    }

    // Fetch Meal's associated categories
    public List<Category> getMealCategories(Long mealId) {
        Meal meal = repository.findById(mealId).orElse(null);
        if (meal != null) {
            return meal.getCategory();
        }
        return null;
    }

    public List<String> getMealCategoryNames(Long mealId) {
        Meal meal = repository.findById(mealId).orElse(null);
        if (meal != null) {
            List<Category> categories = meal.getCategory();
            List<String> categoryNames = new ArrayList<>();
            for (Category category : categories) {
                categoryNames.add(category.getName());
            }
            return categoryNames;
        }
        return null;
    }

    public Optional<Meal> get(Long id) {
        return repository.findById(id);
    }

    public Meal update(Meal entity) {
        return repository.save(entity);
    }

    public Meal create(Meal entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        
        repository.deleteById(id);
    }

    public Page<Meal> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Meal> list(Pageable pageable, Specification<Meal> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

    // Category association
    public Optional<Category> getCategory(Long id) {
        return categoryRepository.findById(id);
    }

    public Category update(Category entity) {
        return categoryRepository.save(entity);
    }

    public Category create(Category entity) {
        return categoryRepository.save(entity);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public Page<Category> listCategory(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Page<Category> listCategory(Pageable pageable, Specification<Category> filter) {
        return categoryRepository.findAll(filter, pageable);
    }

    public List<Category> listAll() {
        return categoryRepository.findAll();
    }

    public int countCategory() {
        return (int) categoryRepository.count();
    }
}
