package codeswitch.italiocco.services;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import org.springframework.data.util.Pair;

import codeswitch.italiocco.dtos.VerbConstraint;
import codeswitch.italiocco.exceptions.IllegalVerbConstraintException;
import codeswitch.italiocco.exceptions.MissingVerbConstraintException;

public class VerbValidator {
	
	protected static Pair<List<IllegalVerbConstraintException>, BiConsumer<String, Boolean>> illegalConstraintChecker() {
		var exceptions = new ArrayList<IllegalVerbConstraintException>();
		BiConsumer<String, Boolean> check = (String errorMsg, Boolean condition) -> {
			if (condition) {
				exceptions.add((new IllegalVerbConstraintException(errorMsg)));
			}
		};	
		return Pair.of(exceptions, check);
	}

	protected static Pair<List<MissingVerbConstraintException>, BiConsumer<String, Boolean>> missingConstraintChecker() {
		var exceptions = new ArrayList<MissingVerbConstraintException>();
		BiConsumer<String, Boolean> check = (String errorMsg, Boolean condition) -> {
			if (condition) {
				exceptions.add((new MissingVerbConstraintException(errorMsg)));
			}
		};
		return Pair.of(exceptions, check);
	}
	
	public static List<IllegalVerbConstraintException> checkIllegalConstraints(VerbConstraint verb) {
		var illegalConstraintChecker = illegalConstraintChecker();
		var exceptions = illegalConstraintChecker.getFirst();
		var check = illegalConstraintChecker.getSecond();

		if (VerbUtils.hasMode(verb)) {
			switch (verb.getMode()) {
				case CONDITIONAL -> check.accept("Conditional + Future", VerbUtils.isFuture(verb));
				case INFINITIVE -> {
					check.accept("Infinitive + Person", VerbUtils.hasPerson(verb));
					check.accept("Infinitive + Future", VerbUtils.isFuture(verb));
					check.accept("Infinitive + Duration", VerbUtils.hasDuration(verb));
				}
				case GERUND -> {
					check.accept("Gerund + Person", VerbUtils.hasPerson(verb));
					check.accept("Gerund + Future", VerbUtils.isFuture(verb));
					check.accept("Gerund + Duration", VerbUtils.hasDuration(verb));
				}
				default -> {
				}
			}
		}
		return exceptions;
	}
	
	public static List<MissingVerbConstraintException> checkMissingConstraints(VerbConstraint verb) {
		var missingConstraintChecker = missingConstraintChecker();
		var exceptions = missingConstraintChecker.getFirst();
		var check = missingConstraintChecker.getSecond();

		// TODO: lemma
		check.accept("- Mode", !VerbUtils.hasMode(verb));
		check.accept("- Tense", !VerbUtils.hasTense(verb));
		check.accept("- Diathesis", !VerbUtils.hasDiathesis(verb));

		if (VerbUtils.hasMode(verb)) {
			switch (verb.getMode()) {
				case INDICATIVE -> {
					check.accept("Indicative - Person", !VerbUtils.hasPerson(verb));
					check.accept("Indicative - Duration", !VerbUtils.hasDuration(verb));
				}
				case CONDITIONAL -> {
					check.accept("Conditional - Person", !VerbUtils.hasPerson(verb));
					check.accept("Conditional - Duration", !VerbUtils.hasDuration(verb));
				}
				default -> {}
			}
		}
		return exceptions;
	}
	
}
