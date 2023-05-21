package codeswitch.italiocco.entities;

import java.math.BigInteger;

import org.hibernate.annotations.GeneratedColumn;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Italiocco {
	
	@Id
	@Column(name = "id")
	private BigInteger id;
	
	@Column(name = "verb")
	private String verb;
	
	@Column(name = "object")
	private String object;
	
	@GeneratedColumn(value = "string")
	private String string;
	
	public String getString() {
		return string;
	}
}
