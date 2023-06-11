package codeswitch.italiocco.services;

import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

import codeswitch.italiocco.constants.Composition;
import codeswitch.italiocco.constants.Diathesis;
import codeswitch.italiocco.constants.Duration;
import codeswitch.italiocco.constants.Mode;
import codeswitch.italiocco.constants.Tense;
import codeswitch.italiocco.dtos.LemmaDto;
import codeswitch.italiocco.dtos.VerbConstraint;
import codeswitch.italiocco.exceptions.IllegalVerbConstraintException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class VerbRandomizer {
	
	// TODO: lemma
	
	protected static int randomInt(int max) {
		var random = new Random();
		return random.ints(1, 0, max)
				.findFirst()
				.getAsInt();
	}
	
	protected static <T> T getRandomElement(T[] array) {
		var idx = randomInt(array.length);
		return array[idx];
	}
	
	// random methods disregarding any legal constraint
	protected static Mode randomMode() {
		return getRandomElement(Mode.values());
	}
	protected static Tense randomTense() {
		return getRandomElement(Tense.values());	
	}
	protected static Diathesis randomDiathesis() {
		return getRandomElement(Diathesis.values());
	}
	protected static Duration randomDuration() {
		return getRandomElement(Duration.values());
	}
	protected static Composition randomComposition() {
		return getRandomElement(Composition.values());
	}
	protected static LemmaDto randomLemma() {
		// TODO: implementation
		return LemmaDto.builder().build();
	}
	
	// legal constraints
	protected static Integer drawLegalPerson(VerbConstraint verb) throws IllegalVerbConstraintException {
		if (VerbUtils.isInfinitiveOrGerund(verb)) {
			return null;
		}
		if (!VerbUtils.hasMode(verb)) {
			throw new IllegalVerbConstraintException("Cannot determine person without mode");
		}
		return randomInt(8);
	}
	
	protected static Tense drawLegalTense(VerbConstraint verb) throws IllegalVerbConstraintException {
		if (VerbUtils.isInfinitiveOrGerund(verb) || VerbUtils.isConditional(verb)) {
			return getRandomElement(new Tense[] { Tense.PRESENT, Tense.PAST });
		}
		if (!VerbUtils.hasMode(verb) || !VerbUtils.hasDuration(verb)) {
			throw new IllegalVerbConstraintException("Cannot determine tense without mode and duration");
		}
		return getRandomElement(Tense.values());
	}
	
	protected static Duration drawLegalDuration(VerbConstraint verb) throws IllegalVerbConstraintException {
		if (VerbUtils.isInfinitiveOrGerund(verb)) {
			return null;
		}
		if (!VerbUtils.hasMode(verb)) {
			throw new IllegalVerbConstraintException("Cannot determine duration without mode");
		}
		return randomDuration();
	}
	
	protected static Composition drawLegalComposition(VerbConstraint verb) throws IllegalVerbConstraintException {
		if(VerbUtils.isInfinitiveOrGerund(verb)) {
			return null;
		}
		if (!VerbUtils.hasMode(verb) || !VerbUtils.hasTense(verb)) {
			throw new IllegalVerbConstraintException("Cannot determine composition without mode and tense");
		}
		return randomComposition();
	}
	
	protected static <T> T getOrDraw(T value, Supplier<T> draw) {
		return Optional.ofNullable(value).orElse(draw.get());	
	}
	
	// get or draws without legal constraints
	public static Mode getOrDrawMode(VerbConstraint verb) {
		return getOrDraw(verb.getMode(), VerbRandomizer::randomMode);
	}
	public static Diathesis getOrDrawDiathesis(VerbConstraint verb) {
		return getOrDraw(verb.getDiathesis(), VerbRandomizer::randomDiathesis);
	}
	public static LemmaDto getOrDrawLemma(VerbConstraint verb) {
		return getOrDraw(verb.getLemma(), VerbRandomizer::randomLemma);
	}
	
	// get or draws with legal constraints
	public static Integer getOrDrawPerson(VerbConstraint verb) throws IllegalVerbConstraintException {
		return Optional
				.ofNullable(verb.getPerson())
				.orElse(drawLegalPerson(verb));
	}
	public static Tense getOrDrawTense(VerbConstraint verb) throws IllegalVerbConstraintException {
		return Optional
			.ofNullable(verb.getTense())
			.orElse(drawLegalTense(verb));
	}
	public static Duration getOrDrawDuration(VerbConstraint verb) throws IllegalVerbConstraintException {
		return Optional
				.ofNullable(verb.getDuration())
				.orElse(drawLegalDuration(verb));
	}
	public static Composition getOrDrawComposition(VerbConstraint verb) throws IllegalVerbConstraintException {
		return Optional
				.ofNullable(verb.getComposition())
				.orElse(drawLegalComposition(verb));
	}

	public static VerbConstraint randomize(VerbConstraint verb) throws IllegalVerbConstraintException {
		var randomizedVerb = verb.toBuilder().build();
		randomizedVerb.setMode(getOrDrawMode(randomizedVerb));
		randomizedVerb.setDiathesis(getOrDrawDiathesis(randomizedVerb));
		randomizedVerb.setLemma(getOrDrawLemma(randomizedVerb));
		randomizedVerb.setDuration(getOrDrawDuration(randomizedVerb));
		randomizedVerb.setPerson(getOrDrawPerson(randomizedVerb));
		randomizedVerb.setTense(getOrDrawTense(randomizedVerb));
		randomizedVerb.setComposition(getOrDrawComposition(randomizedVerb));
		return randomizedVerb;
	}
}
