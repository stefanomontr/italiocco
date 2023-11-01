package codeswitch.italiocco.entities;

import java.math.BigInteger;

import jakarta.persistence.Table;
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
@Table(name = "ITALIOCCO")
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
	
	public String getString() {
		return string;
	}
}
