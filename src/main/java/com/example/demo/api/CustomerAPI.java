package com.example.demo.api;


import com.example.demo.entity.Customer;
import com.example.demo.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin("*")
public class CustomerAPI {
    @Autowired
    CustomerService customerService;

@PostMapping
public ResponseEntity<Customer> create(@Valid @RequestBody Customer customer) {
   Customer newCustomer =customerService.createNewCustomer(customer);
   return ResponseEntity.ok(newCustomer);
}
@GetMapping
    public ResponseEntity get() {
    List<Customer> customerList = customerService.getAllCustomers();
    return ResponseEntity.ok(customerList);
}
@PutMapping("{id}")
    public ResponseEntity<Customer> updateCustomer (@Valid @RequestBody Customer customer, @PathVariable long id) {
    Customer newCustomer =customerService.updateCustomer(customer, id);
    return ResponseEntity.ok(newCustomer);
}
@DeleteMapping("{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable long id) {
    Customer customer = customerService.deleteCustomer(id);
    return ResponseEntity.ok(customer);
}

}
