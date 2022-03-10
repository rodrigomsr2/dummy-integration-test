package com.integration.domain.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.integration.domain.model.Customer;

import static com.integration.builder.CustomerBuilder.aCustomer;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //para caso o banco de teste não for em memória
@ActiveProfiles("test")
public class CustomerRepositoryTest {
	
	public static final String DEFAULT_CNPJ = "95816517000160";

	@Autowired
	private CustomerRepository repository;
	
	@AfterEach
	public void cleanDB() {
		this.repository.deleteAll();
	}
	
	@Test
	void shouldLoadSpecifiedCustomerByCnpj() {
		Customer expected = aCustomer().withCnpj(DEFAULT_CNPJ).build();
		
		this.repository.save(expected);
		Customer actual = this.repository.findByCnpj(expected.getCnpj()).get();
		
		assertNotNull(actual);
		assertTrue(expected.getCnpj().equals(actual.getCnpj()));
	}
	
}
