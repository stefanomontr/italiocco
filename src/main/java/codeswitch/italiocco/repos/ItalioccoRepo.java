package codeswitch.italiocco.repos;

import java.math.BigInteger;

import org.springframework.data.repository.CrudRepository;

import codeswitch.italiocco.entities.Italiocco;

public interface ItalioccoRepo extends CrudRepository<Italiocco, BigInteger> {
	
}
