package com.banco.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.banco.repository.CheckingRepository;
import com.banco.repository.MovementRepository;
import com.banco.repository.SavingsRepository;
import com.banco.repository.ServiceRepository;
import com.banco.repository.UserRepository;

@Controller
public class HomeController {
	
	@Autowired
	UserRepository userDao;
	
	@Autowired
	ServiceRepository serviceDao;
	
	@Autowired
	SavingsRepository savingsDao;

	@Autowired
	MovementRepository movementDao;
	
	@Autowired
	CheckingRepository checkingDao;

	@RequestMapping("/")
	public String home() {
		return "home.jsp";
	}
}
