package codeswitch.italiocco.repos;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import codeswitch.italiocco.entities.Italiocco;

public interface ItalioccoRepo extends CrudRepository<Italiocco, BigInteger> {
	
	List<Italiocco> findAll();
}
