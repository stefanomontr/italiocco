package codeswitch.italiocco.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codeswitch.italiocco.entities.Italiocco;
import codeswitch.italiocco.repos.ItalioccoRepo;

@RestController
@RequestMapping("/italiocco")
public class ItalioccoResource {
	
	@Autowired
	private ItalioccoRepo italioccoRepo;
	
	@GetMapping("/list")
	private String findAll() {
		String res = "";
		for (Italiocco it: italioccoRepo.findAll()) {
			res = res.concat(it.getString()).concat("\n");
		}
		return res;
	}
	
}
