package com.example.spring_boot_jpa_junittest.repository;

import com.example.spring_boot_jpa_junittest.entity.Customer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  List<Customer> findByLastName(String lastName);
}
