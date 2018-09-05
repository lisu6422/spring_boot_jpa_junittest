package com.example.spring_boot_jpa_junittest.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.spring_boot_jpa_junittest.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class CustomerRepositoryTest {

  @Autowired
  private TestEntityManager testEntityManager;

  @Autowired
  private CustomerRepository repository;

  @Test
  public void should_find_no_customers_if_repository_is_empty() {
    Iterable<Customer> customers = repository.findAll();

    assertThat(customers).isEmpty();
  }

  @Test
  public void should_store_a_customer() {
    Customer customer = repository.save(new Customer("Jack", "Smith"));

    assertThat(customer).hasFieldOrPropertyWithValue("firstName", "Jack");
    assertThat(customer).hasFieldOrPropertyWithValue("lastName", "Smith");
  }

  @Test
  public void should_delete_all_customer() {
    testEntityManager.persist(new Customer("Jack", "Smith"));
    testEntityManager.persist(new Customer("Adam", "Johnson"));

    repository.deleteAll();

    assertThat(repository.findAll()).isEmpty();
  }

  @Test
  public void should_find_all_customers() {
    Customer customer1 = new Customer("Jack", "Smith");
    testEntityManager.persist(customer1);

    Customer customer2 = new Customer("Adam", "Johnson");
    testEntityManager.persist(customer2);

    Customer customer3 = new Customer("Peter", "Smith");
    testEntityManager.persist(customer3);

    Iterable<Customer> customers = repository.findAll();

    assertThat(customers).hasSize(3).contains(customer1, customer2, customer3);
  }

  @Test
  public void should_find_customer_by_id() {
    Customer customer1 = new Customer("Jack", "Smith");
    testEntityManager.persist(customer1);

    Customer customer2 = new Customer("Adam", "Johnson");
    testEntityManager.persist(customer2);

    Customer foundCustomer = repository.findById(customer2.getId()).orElse(null);
    assertThat(foundCustomer).isEqualTo(customer2);
  }
}
