package com.integration.builder;

import com.integration.domain.model.Customer;
import com.integration.util.RandomCnpjGenerator;

public class CustomerBuilder {
	
	private Customer customer;
	
	private CustomerBuilder() { }
	
	public static CustomerBuilder aCustomer() {
		CustomerBuilder builder = new CustomerBuilder();
		builder.customer = new Customer();
		
		builder.customer.setAge(22);
		builder.customer.setCnpj(RandomCnpjGenerator.generate());
		builder.customer.setName("testerson tester");
		
		return builder;
	}
	
	public CustomerBuilder withAge(Integer age) {
		this.customer.setAge(age);
		return this;
	}
	
	public CustomerBuilder withInvalidAge() {
		this.customer.setAge(17);
		return this;
	}
	
	public CustomerBuilder withInvalidName() {
		this.customer.setName("a");
		return this;
	}
	
	public CustomerBuilder withCnpj(String cnpj) {
		this.customer.setCnpj(cnpj);
		return this;
	}
	
	public Customer build() {
		return this.customer;
	}
	
}
