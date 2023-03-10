package com.springboot.microserice.limitsservice.limitscontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.microserice.limitsservice.beans.Limits;
import com.springboot.microserice.limitsservice.configuration.Configuration;



@RestController
public class LimitsController {
	
	@Autowired
	private Configuration configuration;
	
    @GetMapping("/limits")
	public Limits retriveLimits() {
		return new Limits(configuration.getMinimum(),configuration.getMaximum());
	}
}
