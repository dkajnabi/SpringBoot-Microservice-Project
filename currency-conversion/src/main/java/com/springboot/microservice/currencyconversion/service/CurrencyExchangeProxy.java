package com.springboot.microservice.currencyconversion.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.springboot.microservice.currencyconversion.beans.CurrencyConversion;

//@FeignClient(name = "currency-exchange",url = "localhost:8000")  //when we use only FiegnClient
@FeignClient(name = "currency-exchange")
public interface CurrencyExchangeProxy {
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversion retrieveExchangeValue(@PathVariable String from, @PathVariable String to);

}
