package com.hades.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hades.repository.Teste;
import com.hades.repository.TesteRepo;

@RestController
public class HomeController {

	@Autowired
	private TesteRepo repository;

	@RequestMapping(path = "/home", method = RequestMethod.GET)
	public Teste home() {
		Teste teste = new Teste("teste");
		repository.save(teste);
		return teste;
	}
}
