package mci.softwareengineering2.group2.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import mci.softwareengineering2.group2.data.Meal;
import mci.softwareengineering2.group2.data.Order;
import mci.softwareengineering2.group2.datarepository.MealRepository;
import mci.softwareengineering2.group2.datarepository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository repository;

    @Autowired
    private MealRepository mealRepository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Optional<Order> get(Long id) {
        return repository.findById(id);
    }

    public Order update(Order entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Order> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Order> list(Pageable pageable, Specification<Order> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

    public Page<Meal> listMeals(Pageable pageable) {
        return mealRepository.findAll(pageable);
    }
}
