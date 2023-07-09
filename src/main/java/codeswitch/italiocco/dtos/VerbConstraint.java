package codeswitch.italiocco.dtos;

import codeswitch.italiocco.constants.*;
import codeswitch.italiocco.exceptions.IllegalVerbConstraintException;
import codeswitch.italiocco.exceptions.MissingVerbConstraintException;
import codeswitch.italiocco.services.IllegalVerbConstraintChecker;
import codeswitch.italiocco.services.MissingVerbCostraintChecker;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public abstract class VerbConstraint {
	
	protected LemmaDto lemma;
	protected Integer person;
	protected Mode mode;
	protected Tense tense;
	protected Diathesis diathesis;
	protected Duration duration;
	protected Composition composition;
	protected AdverbDto adverb;
	
	public String getDescriptiveVerbForm() {
		return String.format("%s %s %s %s", mode, tense, composition, duration);
	}

	abstract void findIllegalVerbConstraints(IllegalVerbConstraintChecker checker);
	abstract void findMissingVerbConstraints(MissingVerbCostraintChecker checker);
	public void checkIllegalVerbConstraints() throws IllegalVerbConstraintException {
		var checker = new IllegalVerbConstraintChecker();
		this.findIllegalVerbConstraints(checker);
		checker.check();
	}

	public void checkMissingVerbConstraints() throws MissingVerbConstraintException {
		var checker = new MissingVerbCostraintChecker();
		this.findMissingVerbConstraints(checker);
		checker.check();
	}
}
