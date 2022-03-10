package com.integration.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Integer age;
	
	@Column(nullable = false, unique = true)
	private String cnpj;
	
	public Customer() { }

	public Customer(String name, Integer age, String cnpj) {
		this.name = name;
		this.age = age;
		this.cnpj = cnpj;
	}
	
}
