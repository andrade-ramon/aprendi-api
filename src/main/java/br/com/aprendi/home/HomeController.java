package br.com.aprendi.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
	@Autowired
    TesteRepo repo;
	
	
	@RequestMapping(path="/home", method=RequestMethod.GET)
	public void home() {
		repo.save(new Teste("nome"));
	}
}
