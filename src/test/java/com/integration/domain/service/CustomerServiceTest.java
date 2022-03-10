package com.integration.domain.service;

import static com.integration.builder.CustomerBuilder.aCustomer;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import com.integration.domain.exception.InvalidAgeException;
import com.integration.domain.exception.InvalidNameException;
import com.integration.domain.model.Customer;
import com.integration.domain.repository.CustomerRepository;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
//@MockitoSettings(strictness = Strictness.LENIENT)
public class CustomerServiceTest {

	private CustomerService service;
	
	@Mock
	private TestService testService;
	
	@Mock
	private CustomerRepository customerRepository;
	
	@BeforeEach
	private void setUp() {
		MockitoAnnotations.openMocks(this);
		this.service = new CustomerService(customerRepository, testService);	
	}
	
	@Test
	public void shouldSuccedWhenGivenValidAge() {
		//Mockito.doNothing().when(testService).test();
		Customer customer = aCustomer().build();
		
		assertDoesNotThrow(() -> ReflectionTestUtils.invokeMethod(service, "validateMinAge", customer.getAge()));
	}
	
	@Test
	public void shouldSuccedWhenGivenValidName() {
		Customer customer = aCustomer().build();
		
		assertDoesNotThrow(() -> ReflectionTestUtils.invokeMethod(service, "validateNameLenght", customer.getName()));
	}
	
	@Test
	public void shouldFailWhenGivenInvalidAge() {
		// cenário
		Customer customer = aCustomer().withInvalidAge().build();
		
		// ação
		InvalidAgeException thrown = assertThrows(
				InvalidAgeException.class,
				() -> ReflectionTestUtils.invokeMethod(service, "validateMinAge", customer.getAge()),
				"Should have not been allowed to saved a underage customer"
		    );

		//validação
	    assertTrue(thrown.getMessage().equals("Age must be over 17"));
	}
	
	@Test
	public void shouldFailWhenGivenInvalidName() {
		Customer customer = aCustomer().withInvalidName().build();
		
		InvalidNameException thrown = assertThrows(
				InvalidNameException.class,
				() -> ReflectionTestUtils.invokeMethod(service, "validateNameLenght", customer.getName()),
				"Should have not been allowed to saved a customer with name under 10 characters"
		    );

	    assertTrue(thrown.getMessage().equals("Name must have at least 10 characters"));
	}
	
}
