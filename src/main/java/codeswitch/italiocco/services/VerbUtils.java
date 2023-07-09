package codeswitch.italiocco.services;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import codeswitch.italiocco.constants.Mode;
import codeswitch.italiocco.constants.Tense;
import codeswitch.italiocco.dtos.VerbConstraint;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class VerbUtils {
	
	public static boolean hasMode(VerbConstraint verb) {
		return !Objects.isNull(verb.getMode());
	}
	public static boolean hasDiathesis(VerbConstraint verb) {
		return !Objects.isNull(verb.getDiathesis());
	}
	public static boolean hasPerson(VerbConstraint verb) {
		return !Objects.isNull(verb.getPerson());
	}
	public static boolean hasDuration(VerbConstraint verb) {
		return !Objects.isNull(verb.getDuration());
	}
	public static boolean hasTense(VerbConstraint verb) {
		return !Objects.isNull(verb.getTense());
	}
	
	
	public static boolean isFuture(VerbConstraint verb) {
		return hasTense(verb) && Tense.FUTURE.equals(verb.getTense());
	}

	public static boolean isConditional(VerbConstraint verb) {
		return hasMode(verb) && Mode.CONDITIONAL.equals(verb.getMode());
	}

	public static boolean isIndicative(VerbConstraint verb) {
		return hasMode(verb) && Mode.INDICATIVE.equals(verb.getMode());
	}

	public static boolean isAuxiliaryMode(VerbConstraint verb) {
		return hasMode(verb) && List.of(Mode.CONDITIONAL, Mode.FUTURE).contains(verb.getMode());
	}

	public static boolean isInfinitiveOrGerund(VerbConstraint verb) {
		return hasMode(verb) && List.of(Mode.INFINITIVE, Mode.GERUND).contains(verb.getMode());
	}
}
