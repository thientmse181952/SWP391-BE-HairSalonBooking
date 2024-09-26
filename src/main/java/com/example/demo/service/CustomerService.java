package com.example.demo.service;

import com.example.demo.entity.Customer;
import com.example.demo.exception.DuplicateEntity;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    public Customer createNewCustomer(Customer customer) {
        try{
            Customer newCustomer = customerRepository.save(customer);
            return newCustomer;
        }catch(Exception e){
                  throw new DuplicateEntity("Duplicate Customer found");
        }

    }
   public List<Customer> getAllCustomers() {
        List<Customer> customers = customerRepository.findCustomersByIsDeletedFalse();
        return customers;
    }

public Customer updateCustomer(Customer customer, long customerId) {

    Customer oldCustomer = customerRepository.findCustomerById(customerId);
    if (oldCustomer == null) {
        throw new NotFoundException("Customer not found");
    }
    oldCustomer.setName(customer.getName());
    oldCustomer.setGender(customer.getGender());
    return customerRepository.save(oldCustomer);
}
  public Customer deleteCustomer(long customerId){
      Customer oldCustomer = customerRepository.findCustomerById(customerId);
      if (oldCustomer == null) {
          throw new NotFoundException("Customer not found");
      }
      oldCustomer.setDeleted(true);
      return customerRepository.save(oldCustomer);
  }

}
