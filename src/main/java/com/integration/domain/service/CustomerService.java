package com.integration.domain.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.stereotype.Service;

import com.integration.domain.exception.InvalidAgeException;
import com.integration.domain.exception.CustomerNotFoundException;
import com.integration.domain.exception.InvalidNameException;
import com.integration.domain.model.Customer;
import com.integration.domain.repository.CustomerRepository;

@Service
public class CustomerService {

	private CustomerRepository customerRepository;
	
	private TestService testService;
	
	public CustomerService(CustomerRepository customerRepository, TestService testService) {
		this.customerRepository = customerRepository;
		this.testService = testService;
	}
	
	public List<Customer> findAll() {
		return IterableUtils.toList(this.customerRepository.findAll());
	}
	
	public Optional<Customer> find(Long id) {
		Integer i = testService.test(1, LocalDateTime.now(), LocalDateTime.now());
		return this.customerRepository.findById(id);
	}
	
	public Customer findByIdOrFail(Long id) {
		Customer c = this.find(id).orElseThrow(CustomerNotFoundException::new);
		return c;
	}
	
	public Customer findByCnpjOrFail(String cnpj) {
		return this.customerRepository.findByCnpj(cnpj).orElseThrow(CustomerNotFoundException::new);
	}
	
	public Customer save(Customer customer) {
		validateBeforeSave(customer);
		return this.customerRepository.save(customer);
	}
	
	private void validateBeforeSave(Customer customer) {
		validateNameLenght(customer.getName());
		validateMinAge(customer.getAge());
	}
	
	private void validateNameLenght(String name) {
		if( name.length() < 10 ) throw new InvalidNameException();
	}
	
	private void validateMinAge(Integer age) {
		Integer test = testService.test(1, LocalDateTime.now(), LocalDateTime.now());
		if( age < 18 || test < 2 ) {
			throw new InvalidAgeException();
		};
	}
	
}
