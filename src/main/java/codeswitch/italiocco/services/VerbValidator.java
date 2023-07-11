package codeswitch.italiocco.services;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import org.springframework.data.util.Pair;

import codeswitch.italiocco.dtos.VerbConstraint;
import codeswitch.italiocco.exceptions.IllegalVerbConstraintException;
import codeswitch.italiocco.exceptions.MissingVerbConstraintException;

public class VerbValidator {
	
	public static List<IllegalVerbConstraintException> checkIllegalConstraints(VerbConstraint verb) {
		var checker = new IllegalVerbConstraintChecker();

		if (VerbUtils.hasMode(verb)) {
			switch (verb.getMode()) {
				case CONDITIONAL -> {
					checker.accept(
							"CONDITIONAL + PASSIVE + CONTINUOUS",
							VerbUtils.isPassive(verb) && VerbUtils.isContinuous(verb)
					);
					checker.accept("CONDITIONAL + TENSE", VerbUtils.hasTense(verb));
				}
				case FUTURE -> {
					checker.accept(
							"FUTURE + PASSIVE + CONTINUOUS",
							VerbUtils.isPassive(verb) && VerbUtils.isContinuous(verb)
					);
					checker.accept("FUTURE + TENSE", VerbUtils.hasTense(verb));
				}
				case INFINITIVE -> {
					checker.accept("INFINITIVE + PERSON", VerbUtils.hasPerson(verb));
					checker.accept("INFINITIVE + DURATION", VerbUtils.hasDuration(verb));
					checker.accept("INFINITIVE + COMPOSITION", VerbUtils.hasComposition(verb));
				}
				case GERUND -> {
					checker.accept("GERUND + PERSON", VerbUtils.hasPerson(verb));
					checker.accept("GERUND + DURATION", VerbUtils.hasDuration(verb));
					checker.accept("GERUND + COMPOSITION", VerbUtils.hasComposition(verb));
				}
				default -> {
				}
			}
		}
		return checker.getExceptions();
	}
	
	public static List<MissingVerbConstraintException> checkMissingConstraints(VerbConstraint verb) {
		var checker = new MissingVerbCostraintChecker();

		if (VerbUtils.hasMode(verb)) {
			switch (verb.getMode()) {
				case INDICATIVE -> {
					checker.accept("INDICATIVE – DIATHESIS", !VerbUtils.hasDiathesis(verb));
					checker.accept("INDICATIVE – TENSE", !VerbUtils.hasTense(verb));
					checker.accept("INDICATIVE – PERSON", !VerbUtils.hasPerson(verb));
					checker.accept("INDICATIVE – DURATION", !VerbUtils.hasDuration(verb));
					checker.accept("INDICATIVE – COMPOSITION", !VerbUtils.hasComposition(verb));
					checker.accept("INDICATIVE – LEMMA", !VerbUtils.hasLemma(verb));
				}
				case CONDITIONAL -> {
					checker.accept("CONDITIONAL – DIATHESIS", !VerbUtils.hasDiathesis(verb));
					checker.accept("CONDITIONAL – PERSON", !VerbUtils.hasPerson(verb));
					checker.accept("CONDITIONAL – DURATION", !VerbUtils.hasDuration(verb));
					checker.accept("CONDITIONAL – COMPOSITION", !VerbUtils.hasComposition(verb));
					checker.accept("CONDITIONAL – LEMMA", !VerbUtils.hasLemma(verb));
				}
				case FUTURE -> {
					checker.accept("FUTURE – DIATHESIS", !VerbUtils.hasDiathesis(verb));
					checker.accept("FUTURE – PERSON", !VerbUtils.hasPerson(verb));
					checker.accept("FUTURE – DURATION", !VerbUtils.hasDuration(verb));
					checker.accept("FUTURE – COMPOSITION", !VerbUtils.hasComposition(verb));
					checker.accept("FUTURE – LEMMA", !VerbUtils.hasLemma(verb));
				}
				case INFINITIVE -> {
					checker.accept("INFINITIVE – DIATHESIS", !VerbUtils.hasDiathesis(verb));
					checker.accept("INFINITIVE – TENSE", !VerbUtils.hasTense(verb));
					checker.accept("INFINITIVE – LEMMA", !VerbUtils.hasLemma(verb));
				}
				case GERUND -> {
					checker.accept("GERUND – DIATHESIS", !VerbUtils.hasDiathesis(verb));
					checker.accept("GERUND – TENSE", !VerbUtils.hasTense(verb));
					checker.accept("GERUND – LEMMA", !VerbUtils.hasLemma(verb));
				}
				default -> checker.accept("MISSING MODE", !VerbUtils.hasMode(verb));
			}
		}
		return checker.getExceptions();
	}
	
}
