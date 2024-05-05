package mci.softwareengineering2.group2.datarepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import mci.softwareengineering2.group2.data.Cart;

public interface CartRepository  extends
            JpaRepository<Cart, Long>,
            JpaSpecificationExecutor<Cart>{

}
