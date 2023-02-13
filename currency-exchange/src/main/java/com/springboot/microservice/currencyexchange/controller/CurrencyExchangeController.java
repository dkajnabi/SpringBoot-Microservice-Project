package com.springboot.microservice.currencyexchange.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.microservice.currencyexchange.beans.CurrencyExchange;
import com.springboot.microservice.currencyexchange.repository.CurrencyExchangeRepository;

@RestController
public class CurrencyExchangeController {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private CurrencyExchangeRepository repo;
	
	@GetMapping("currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(
			@PathVariable String from, @PathVariable String to) {
		
		String port = environment.getProperty("local.server.port");
		CurrencyExchange currencyExchange = repo.findByFromAndTo(from, to);
		if(currencyExchange == null) {
			throw new RuntimeException("Unable to find data for "+ from +" to "+to);
		}
		currencyExchange.setEnvironment(port);
		return currencyExchange;
	}
}









