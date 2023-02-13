package com.springboot.microservice.currencyexchange.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.microservice.currencyexchange.beans.CurrencyExchange;

public interface CurrencyExchangeRepository extends JpaRepository<CurrencyExchange, Long> {
	
	
	CurrencyExchange findByFromAndTo(String from,String to);
}
