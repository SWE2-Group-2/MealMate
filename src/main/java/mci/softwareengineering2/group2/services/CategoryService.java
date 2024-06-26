package mci.softwareengineering2.group2.services;

import java.util.Optional;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import mci.softwareengineering2.group2.data.Category;
import mci.softwareengineering2.group2.datarepository.CategoryRepository;

@Service
public class CategoryService {

private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Optional<Category> get(Long id) {
        return repository.findById(id);
    }

    public Category update(Category entity) {
        return repository.save(entity);
    }

    public Category create(Category entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Category> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Category> list(Pageable pageable, Specification<Category> filter) {
        return repository.findAll(filter, pageable);
    }

    public List<Category> listAll() {
        return repository.findAll();
    }

    public int count() {
        return (int) repository.count();
    }
}
