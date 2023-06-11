package codeswitch.italiocco.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
 
		return italioccoRepo.findAll().stream()
				.map(Italiocco::getString)
				.reduce("", (prev, str) -> prev.concat(str).concat("\r\n"));
	}
	
	@GetMapping("/nlg")
	private String useNLG(@RequestParam String input) {
		return "";
	}
	
	protected String doSth() {
		return "string";
	}
}
