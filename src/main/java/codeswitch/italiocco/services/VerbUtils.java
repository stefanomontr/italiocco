package codeswitch.italiocco.services;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import codeswitch.italiocco.constants.Diathesis;
import codeswitch.italiocco.constants.Duration;
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
	public static boolean hasComposition(VerbConstraint verb) {
		return !Objects.isNull(verb.getComposition());
	}
	public static boolean hasLemma(VerbConstraint verb) {
		return !Objects.isNull(verb.getLemma());
	}
	
	
	public static boolean isFuture(VerbConstraint verb) {
		return hasMode(verb) && Mode.FUTURE.equals(verb.getMode());
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

	public static boolean isPassive(VerbConstraint verb) {
		return hasDiathesis(verb) && Diathesis.PASSIVE.equals(verb.getDiathesis());
	}

	public static boolean isContinuous(VerbConstraint verb) {
		return hasDuration(verb) && Duration.CONTINUOUS.equals(verb.getDuration());
	}
}
