package mci.softwareengineering2.group2.services;

import java.util.Optional;
import mci.softwareengineering2.group2.data.Address;
import mci.softwareengineering2.group2.datarepository.AddressRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository repository;

    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }

    public Optional<Address> get(Long id) {
        return repository.findById(id);
    }

    public Address update(Address entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Address> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Address> list(Pageable pageable, Specification<Address> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }

}
