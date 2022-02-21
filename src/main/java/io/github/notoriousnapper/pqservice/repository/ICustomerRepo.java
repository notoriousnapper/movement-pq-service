package io.github.notoriousnapper.pqservice.repository;

import io.github.notoriousnapper.pqservice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomerRepo extends JpaRepository<Customer, Long> {

}
