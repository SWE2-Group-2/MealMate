package mci.softwareengineering2.group2.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import mci.softwareengineering2.group2.data.Meal;
import mci.softwareengineering2.group2.data.Category;
import mci.softwareengineering2.group2.datarepository.MealRepository;

@Service
public class MealService {

private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
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
}
