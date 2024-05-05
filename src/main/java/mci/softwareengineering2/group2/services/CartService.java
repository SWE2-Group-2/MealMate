package mci.softwareengineering2.group2.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import mci.softwareengineering2.group2.data.Cart;
import mci.softwareengineering2.group2.datarepository.CartRepository;

@Service
public class CartService {

    private final CartRepository repository;

    public CartService(CartRepository repository) {
        this.repository = repository;
    }

    public Optional<Cart> get(Long id) {
        return repository.findById(id);
    }

    public Cart update(Cart entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Cart> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Cart> list(Pageable pageable, Specification<Cart> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }
}
