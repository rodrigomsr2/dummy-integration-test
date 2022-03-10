package com.integration.api.controller;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.anyInt;

import java.time.LocalDateTime;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.integration.builder.CustomerBuilder;
import com.integration.domain.model.Customer;
import com.integration.domain.repository.CustomerRepository;
import com.integration.domain.service.CustomerService;
import com.integration.domain.service.TestService;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CustomerControllerMockRepoTest {
	
	@Autowired
    private MockMvc mvc;
	
	@InjectMocks
	private CustomerService customerService;
	
	@Mock
	private CustomerRepository customerRepository;
	
	@MockBean
	private TestService testService;
	
	@LocalServerPort
	private int port;
	
	@SuppressWarnings("unused")
	private static ObjectMapper mapper;
	
	private static Integer lastId = 1;
	
	@BeforeAll
	public static void setUpOnce() {
		lastId = 1;
		mapper = new ObjectMapper();
	}
	
	@BeforeEach
	public void setUp() {
		RestAssured.port = port;
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		MockitoAnnotations.openMocks(this);
        RestAssuredMockMvc.standaloneSetup(MockMvcBuilders.standaloneSetup(customerService));
		RestAssured.basePath = "/customers";
	}
	
	@Test
	public void shouldReturn200ForExistentCustomer() throws Exception {
		Customer customer = CustomerBuilder.aCustomer().build();
		//BDDMockito.given(service.find(Mockito.any(Long.class))).willReturn(Optional.ofNullable(customer));
		//when(customerService.findByIdOrFail(anyLong())).thenReturn(customer);
		
		when(testService.test(anyInt(), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(2);
		
		RestAssured.
			given()
				.pathParam("id", lastId)
				.accept(ContentType.JSON)
			.when()
				.get("/{id}")
			.then()
				.log().ifValidationFails()
				.statusCode(HttpStatus.OK.value())
				.body("name", hasSize(1))
				.body("name", hasItems("testerson tester"));
	}
	
}
