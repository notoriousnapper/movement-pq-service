package io.github.notoriousnapper.pqservice.controllers;

import java.util.List;
import java.util.Optional;
import io.github.notoriousnapper.pqservice.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.github.notoriousnapper.pqservice.repository.ICustomerRepo;

@RestController
public class CustomerController {

  @Autowired
  ICustomerRepo customerRepo;

  @PostMapping("/customers")
  public ResponseEntity<Customer> save(@RequestBody Customer customer) {
    try {
      return new ResponseEntity<>(customerRepo.save(customer), HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/customers")
  public ResponseEntity<List<Customer>> getAllCustomers() {
    try {
      List<Customer> list = customerRepo.findAll();
      if (list.isEmpty() || list.size() == 0) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(list, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/customers/{id}")
  public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
    Optional<Customer> customer = customerRepo.findById(id);

    if (customer.isPresent()) {
      return new ResponseEntity<Customer>(customer.get(), HttpStatus.OK);
    }
    return new ResponseEntity<Customer>(HttpStatus.NOT_FOUND);
  }

  @PutMapping("/customers/{id}")
  public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) {
    try {
      return new ResponseEntity<>(customerRepo.save(customer), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/customers/{id}")
  public ResponseEntity<Customer> updateCustomer(@PathVariable Long id) {
    try {
      Optional<Customer> customer = customerRepo.findById(id);
      if (customer.isPresent()) {
        customerRepo.delete(customer.get());
        return new ResponseEntity<>(HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
