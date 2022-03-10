package com.integration.api.dto.input;

import com.integration.domain.model.Customer;

import lombok.Data;

@Data
public class CustomerDTO {

	private String name;
	
	private Integer age;
	
	private String cnpj;
	
	public Customer toEntity() {
		return new Customer(name, age, cnpj);
	}
	
}
