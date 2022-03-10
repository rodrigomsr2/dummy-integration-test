package com.integration.api.controller;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.integration.builder.CustomerBuilder;
import com.integration.domain.model.Customer;
import com.integration.domain.repository.CustomerRepository;
import com.integration.util.DatabaseCleaner;
import com.integration.util.FileUtils;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // precisa para o RestAssured
@AutoConfigureMockMvc // necessário caso o @WebMvcTest não seja utilizado
@TestPropertySource("classpath:application-test.properties") // precisa estar no path src/test/resources
public class CustomerControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private CustomerRepository repository;
	
	@LocalServerPort // precisa para o RestAssured
	private int port;
	
	@Autowired
	private DatabaseCleaner databaseCleaner;
	
	private static ObjectMapper mapper;
	
	private static Integer lastId;
	
	@BeforeAll
	public static void setUpOnce() {
		lastId = 1;
		mapper = new ObjectMapper();
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.basePath = "/customers";
	}
	
	@BeforeEach
	public void setUp() {
		RestAssured.port = port;
		prepareData();
	}
	
	private void prepareData() {
		Customer customer = CustomerBuilder.aCustomer().build();
		
		repository.save(customer);
		lastId++;
	}
	
	@AfterEach
	public void clean() {
		databaseCleaner.clearTables();
	}

	@Test
	public void shouldReturn404ForNonExistentCustomer() throws Exception {
		URI uri = new URI("/customers/10");
		
		mockMvc.perform(MockMvcRequestBuilders
						.get(uri)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
							.status()
							.is(HttpStatus.NOT_FOUND.value()));		
	}
	
	@Test
	public void shouldReturn200ForExistentCustomer() throws Exception {
		given()
			.pathParam("id", lastId)
			.accept(ContentType.JSON)
		.when()
			.get("/{id}")
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
	@Test
	public void shouldReturn4CustomersWhenGetAll() throws Exception {
		given()
			//.basePath("/customers")
			//.port(port)
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("name", hasSize(1))
			.body("name", hasItems("testerson tester"));
	}
	
	@Test
	public void shouldReturn201WhenRegisterValidCustomer() throws JsonProcessingException {
		Customer customer = CustomerBuilder.aCustomer().build();
		
		given()
			.body(mapper.writeValueAsString(customer))
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void shouldReturn201WhenRegisteringMultipleCustomers() throws IOException {
		Header header = new Header("active", "" + Boolean.TRUE);
		
		List<Header> headersList = new ArrayList<>();
		
		headersList.add(header);
		
		Headers headers = new Headers(headersList);
		
		given()
			.multiPart(FileUtils.getMultiPart("teste.xls", "file"))
			.header(new Header("content-type", "multipart/form-data"))
			.headers(headers)
			.accept(ContentType.JSON)
		.when()
			.post("/batch")
		.then()
			.statusCode(HttpStatus.CREATED.value())
			.body(is(""));
	}
	
	@Test
	public void shouldReturn400WhenRegisteringUnderageCustomer() throws Exception {
		URI uri = new URI("http://localhost:8080/customers");
		Customer customer = CustomerBuilder.aCustomer().withInvalidAge().build();
		
		mockMvc.perform(MockMvcRequestBuilders
						.post(uri)
						.content(mapper.writeValueAsString(customer))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers
							.status()
							.is(HttpStatus.BAD_REQUEST.value()));	
	}
	
	
	
}
