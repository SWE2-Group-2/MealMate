package mci.softwareengineering2.group2.datarepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import mci.softwareengineering2.group2.data.Order;

public interface OrderRepository extends
        JpaRepository<Order, Long>,
        JpaSpecificationExecutor<Order> {

}
