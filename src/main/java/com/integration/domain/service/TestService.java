package com.integration.domain.service;

import java.time.LocalDateTime;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.integration.domain.model.Customer;

@Service
public class TestService {
	
	final static String URL = "https://reqres.in";

	private Customer customer;
	
	private final RestTemplate restTemplate;
	
	public TestService() {
		this.restTemplate = new RestTemplate();
	}
	
	public Integer test(Integer inteiro, LocalDateTime tempo1, LocalDateTime tempo2) {
		
		try {
			final ResponseEntity<Object> responseEntity = restTemplate.exchange(
					URL + "/api/users/23",
	                HttpMethod.GET,
	                null,
	                new ParameterizedTypeReference<>() {
	                });

	        Object a = responseEntity.getBody();
	        
	        System.out.println("\n\n" + a.toString() + "\n\n");
		} catch (HttpClientErrorException e) {
			System.out.println("\n\n!!!!404!!!!\n\n");
		}
		
		return 1;
	}
	
//	public void test() {
//		throw new RuntimeException();
//	}
	
}
