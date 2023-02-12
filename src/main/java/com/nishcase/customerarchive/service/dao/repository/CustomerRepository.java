package com.nishcase.customerarchive.service.dao.repository;

import com.nishcase.customerarchive.service.dao.entity.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
  Optional<Customer> findByEmail(String email);
  Boolean existsCustomerByEmailAndIdNot(String email, Long id);
  Boolean existsCustomerByEmail(String email);

}
