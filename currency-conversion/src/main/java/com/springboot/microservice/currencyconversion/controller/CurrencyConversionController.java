package com.springboot.microservice.currencyconversion.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.springboot.microservice.currencyconversion.beans.CurrencyConversion;
import com.springboot.microservice.currencyconversion.service.CurrencyExchangeProxy;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	CurrencyExchangeProxy proxy;

	@GetMapping("/currency-conversion/from/{from}/to/{to}/qty/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(
			@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity
			) {
		
		return new CurrencyConversion(1001l, from, to, BigDecimal.valueOf(65), quantity, BigDecimal.valueOf(1), "");
	}
	
	// communicate microservice using RestTemplate
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calCurrencyConv(
			@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity
			) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);
		HashMap<String,String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		ResponseEntity<CurrencyConversion> forEntity = restTemplate.getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversion.class,
				uriVariables );
		CurrencyConversion currencyConversion = forEntity.getBody();
		
		return new CurrencyConversion(currencyConversion.getId(), currencyConversion.getFrom(), currencyConversion.getTo(), 
				currencyConversion.getConversionMultiple(), quantity, BigDecimal.ONE, currencyConversion.getEnvironment());
		
		
	//return	restTemplate.exchange("http://localhost:8000/currency-exchange/from/USD/to/INR", HttpMethod.DELETE.GET, entity, String.class);
		
	
	}
	
	//communicate microservice using Feign Client and Eureka Server
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionFeign(
			@PathVariable String from,
			@PathVariable String to,
			@PathVariable BigDecimal quantity
			) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);
		HashMap<String,String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		CurrencyConversion currencyConversion = proxy.retrieveExchangeValue(from, to);
		
		return new CurrencyConversion(currencyConversion.getId(), currencyConversion.getFrom(), currencyConversion.getTo(), 
				currencyConversion.getConversionMultiple(), quantity, BigDecimal.ONE.add(BigDecimal.ONE), currencyConversion.getEnvironment());
		
		
	//return	restTemplate.exchange("http://localhost:8000/currency-exchange/from/USD/to/INR", HttpMethod.DELETE.GET, entity, String.class);
		
	
	}
}
