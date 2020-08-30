package com.banco.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	
//	@Autowired
//	UserBaseRepository userDao;
//
//	@Autowired
//	ServiceRepository serviceDao;
//
//	@Autowired
//	SavingsRepository savingsDao;
//
//	@Autowired
//	MovementRepository movementDao;
//
//	@Autowired
//	CheckingRepository checkingDao;

	@RequestMapping("/")
	public String home() {
		return "home.jsp";
	}
}
