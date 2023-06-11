package codeswitch.italiocco.dtos;

import codeswitch.italiocco.constants.Composition;
import codeswitch.italiocco.constants.Diathesis;
import codeswitch.italiocco.constants.Duration;
import codeswitch.italiocco.constants.Mode;
import codeswitch.italiocco.constants.Tense;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
public class VerbConstraint {
	
	private LemmaDto lemma;
	private Integer person;	
	private Mode mode;
	private Tense tense;
	private Diathesis diathesis;
	private Duration duration;
	private Composition composition;
	private AdverbDto adverb;
	
	public String getDescriptiveVerbForm() {
		return String.format("%s %s %s %s", mode, tense, composition, duration);
	}

}
