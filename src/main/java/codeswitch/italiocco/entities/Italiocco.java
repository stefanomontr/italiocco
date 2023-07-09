package codeswitch.italiocco.entities;

import java.math.BigInteger;

import org.hibernate.annotations.GeneratedColumn;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class Italiocco {
	
	@Id
	@Column(name = "ID")
	private BigInteger id;
	
	@Column(name = "VERB")
	private String verb;
	
	@Column(name = "OBJECT")
	private String object;
	
	@GeneratedColumn(value = "STRING")
	private String string;
}
