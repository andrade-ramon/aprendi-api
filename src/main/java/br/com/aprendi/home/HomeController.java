package br.com.aprendi.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.aprendi.repository.Teste;
import br.com.aprendi.repository.TesteRepo;

@RestController
public class HomeController {

	@Autowired
	private TesteRepo repository;

	@RequestMapping(path = "/home", method = RequestMethod.GET)
	public void home() {
		repository.save(new Teste("nome"));
	}
}
