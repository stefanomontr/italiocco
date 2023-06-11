package codeswitch.italiocco.services;

import java.util.List;
import java.util.stream.Stream;

import codeswitch.italiocco.dtos.LemmaDto;
import org.assertj.core.api.Assertions;
import org.codehaus.plexus.util.cli.Arg;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import codeswitch.italiocco.constants.Composition;
import codeswitch.italiocco.constants.Diathesis;
import codeswitch.italiocco.constants.Duration;
import codeswitch.italiocco.constants.Mode;
import codeswitch.italiocco.constants.Tense;
import codeswitch.italiocco.dtos.VerbConstraint;
import codeswitch.italiocco.exceptions.IllegalVerbConstraintException;

@ExtendWith(MockitoExtension.class)
class VerbRandomizerTest {
	
	private static VerbConstraint indicative() {
		return VerbConstraint.builder().mode(Mode.INDICATIVE).build();
	}
	private static VerbConstraint conditional() {
		return VerbConstraint.builder().mode(Mode.CONDITIONAL).build();
	}
	private static VerbConstraint gerund() {
		return VerbConstraint.builder().mode(Mode.GERUND).build();
	}
	private static VerbConstraint infinitive() {
		return VerbConstraint.builder().mode(Mode.INFINITIVE).build();
	}
	private static VerbConstraint voidVerb() {
		return VerbConstraint.builder().build();
	}
	private VerbConstraint active() {
		return VerbConstraint.builder().diathesis(Diathesis.ACTIVE).build();
	}

	@Test
	void testRandomInt() {
		Assertions.assertThat(VerbRandomizer.randomInt(1)).isEqualTo(0);
		Assertions.assertThat(VerbRandomizer.randomInt(10)).isBetween(0, 10);
	}
	
	@Test
	void testGetRandomElement() {
		var list = List.of("A", "B", "C");
		Assertions.assertThat(VerbRandomizer.getRandomElement(list.toArray()))
			.matches(list::contains);
	}
	
	@Test
	void testRandomMethods() {
		Assertions.assertThat(VerbRandomizer.randomMode()).isIn((Object[]) Mode.values());
		Assertions.assertThat(VerbRandomizer.randomTense()).isIn((Object[]) Tense.values());
		Assertions.assertThat(VerbRandomizer.randomComposition()).isIn((Object[]) Composition.values());
		Assertions.assertThat(VerbRandomizer.randomDiathesis()).isIn((Object[]) Diathesis.values());
		Assertions.assertThat(VerbRandomizer.randomDuration()).isIn((Object[]) Duration.values());		
	}
	
	@Test
	void testDrawLegalPerson_noMode() {
		try {
			VerbRandomizer.drawLegalPerson(voidVerb());
			Assertions.fail("Should have thrown IllegalVerbConstraintException");
		}
		catch (IllegalVerbConstraintException e) {
			Assertions.assertThat(e.getMessage()).isEqualTo("Cannot determine person without mode");
		}
	}
	
	@Test
	void testDrawLegalPerson() throws IllegalVerbConstraintException {
		Assertions.assertThat(VerbRandomizer.drawLegalPerson(indicative())).isBetween(0, 8);
		Assertions.assertThat(VerbRandomizer.drawLegalPerson(gerund())).isNull();
		Assertions.assertThat(VerbRandomizer.drawLegalPerson(infinitive())).isNull();
	}
	
	@Test
	void testDrawLegalTense_noModeOrDuration() {
		var errorMsg = "Cannot determine tense without mode and duration";

		var verbWithoutMode = active();
		try {
			VerbRandomizer.drawLegalTense(verbWithoutMode);
			Assertions.fail("Should have thrown IllegalVerbConstraintException");
		}
		catch (IllegalVerbConstraintException e) {
			Assertions.assertThat(e.getMessage()).isEqualTo(errorMsg);
		}
		
		var verbWithoutDuration = indicative();
		try {
			VerbRandomizer.drawLegalTense(verbWithoutDuration);
			Assertions.fail("Should have thrown IllegalVerbConstraintException");
		}
		catch (IllegalVerbConstraintException e) {
			Assertions.assertThat(e.getMessage()).isEqualTo(errorMsg);
		}
	}
	
	@Test
	void testDrawLegalTense_indicative() throws IllegalVerbConstraintException {
		var indicativeContinuous = VerbConstraint.builder()
				.mode(Mode.INDICATIVE)
				.duration(Duration.CONTINUOUS)
				.build();
		Assertions.assertThat(VerbRandomizer.drawLegalTense(indicativeContinuous)).isIn((Object[]) Tense.values());
	}
	
	@Test
	void testDrawLegalDuration_noMode() {
		try {
			VerbRandomizer.drawLegalDuration(voidVerb());
			Assertions.fail("Should have thrown IllegalVerbConstraintException");
		}
		catch (IllegalVerbConstraintException e) {
			Assertions.assertThat(e.getMessage()).isEqualTo("Cannot determine duration without mode");
		}
	}
	
	@Test
	void testDrawLegalDuration() throws IllegalVerbConstraintException {
		Assertions.assertThat(VerbRandomizer.drawLegalDuration(indicative())).isIn((Object[]) Duration.values());
		Assertions.assertThat(VerbRandomizer.drawLegalDuration(conditional())).isIn((Object[]) Duration.values());
		Assertions.assertThat(VerbRandomizer.drawLegalDuration(gerund())).isNull();
		Assertions.assertThat(VerbRandomizer.drawLegalDuration(infinitive())).isNull();
	}

	@Test
	void testDrawLegalComposition_noMode() {
		try {
			VerbRandomizer.drawLegalComposition(voidVerb());
			Assertions.fail("Should have thrown IllegalVerbConstraintException");
		}
		catch (IllegalVerbConstraintException e) {
			Assertions.assertThat(e.getMessage()).isEqualTo("Cannot determine composition without mode and tense");
		}
	}
	@Test
	void testDrawLegalComposition_noTense() {
		try {
			VerbRandomizer.drawLegalComposition(indicative());
			Assertions.fail("Should have thrown IllegalVerbConstraintException");
		}
		catch (IllegalVerbConstraintException e) {
			Assertions.assertThat(e.getMessage()).isEqualTo("Cannot determine composition without mode and tense");
		}
	}

	@Test
	void testDrawLegalComposition() throws IllegalVerbConstraintException {
		var indicativePresent = indicative().toBuilder().tense(Tense.PRESENT).build();
		var infinitivePresent = infinitive().toBuilder().tense(Tense.PRESENT).build();
		var gerundPresent = gerund().toBuilder().tense(Tense.PRESENT).build();
		Assertions.assertThat(VerbRandomizer.drawLegalComposition(indicativePresent)).isIn((Object[]) Composition.values());
		Assertions.assertThat(VerbRandomizer.drawLegalComposition(infinitivePresent)).isNull();
		Assertions.assertThat(VerbRandomizer.drawLegalComposition(gerundPresent)).isNull();
	}

	
	@Test
	void testGetOrDrawMode() {
		Assertions.assertThat(VerbRandomizer.getOrDrawMode(indicative())).isEqualTo(Mode.INDICATIVE);
		Assertions.assertThat(VerbRandomizer.getOrDrawMode(voidVerb())).isIn((Object[]) Mode.values());
	}
	
	@Test
	void testGetOrDrawDiathesis() {
		Assertions.assertThat(VerbRandomizer.getOrDrawDiathesis(active())).isEqualTo(Diathesis.ACTIVE);
		Assertions.assertThat(VerbRandomizer.getOrDrawDiathesis(voidVerb())).isIn((Object[]) Diathesis.values());
	}
	
	@Test
	void testGetOrDrawPerson() throws IllegalVerbConstraintException {
		var firstPersonIndicative = indicative().toBuilder().person(0).build();
		Assertions.assertThat(VerbRandomizer.getOrDrawPerson(firstPersonIndicative)).isEqualTo(0);
		Assertions.assertThat(VerbRandomizer.getOrDrawPerson(indicative())).isBetween(0, 8);
	}

	@Test
	void testGetOrDrawTense() throws IllegalVerbConstraintException {
		var indicativeContinuous = VerbConstraint.builder()
				.mode(Mode.INDICATIVE)
				.duration(Duration.CONTINUOUS)
				.build();
		var indicativePastContinuous = indicativeContinuous.toBuilder().tense(Tense.PAST).build();
		Assertions.assertThat(VerbRandomizer.getOrDrawTense(indicativePastContinuous)).isEqualTo(Tense.PAST);
		Assertions.assertThat(VerbRandomizer.getOrDrawTense(indicativeContinuous)).isIn((Object[]) Tense.values());
	}

	@Test
	void testGetOrDrawDuration() throws IllegalVerbConstraintException {
		var indicativeContinuous = indicative().toBuilder().duration(Duration.CONTINUOUS).build();
		Assertions.assertThat(VerbRandomizer.getOrDrawDuration(indicativeContinuous)).isEqualTo(Duration.CONTINUOUS);
		Assertions.assertThat(VerbRandomizer.getOrDrawDuration(indicative())).isIn((Object[]) Duration.values());
	}

	@Test
	void testGetOrDrawComposition() throws IllegalVerbConstraintException {
		var indicativePresent = indicative().toBuilder().tense(Tense.PRESENT).build();
		var indicativePresentPerfect = indicativePresent.toBuilder().composition(Composition.PERFECT).build();
		Assertions.assertThat(VerbRandomizer.getOrDrawComposition(indicativePresentPerfect)).isEqualTo(Composition.PERFECT);
		Assertions.assertThat(VerbRandomizer.getOrDrawComposition(indicativePresent)).isIn((Object[]) Composition.values());
	}

	@Test
	void testGetOrDrawLemma() {
		var lemma = LemmaDto.builder().present("MAKE").build();
		var verb = VerbConstraint.builder().lemma(lemma).build();
		Assertions.assertThat(VerbRandomizer.getOrDrawLemma(verb)).isEqualTo(lemma);
		Assertions.assertThat(VerbRandomizer.getOrDrawLemma(voidVerb())).isNotNull();
	}

	private static Stream<Arguments> randomizeArgs() {
		return Stream.of(
				Arguments.of(voidVerb()),
				Arguments.of(indicative()),
				Arguments.of(conditional()),
				Arguments.of(gerund()),
				Arguments.of(infinitive())
		);
	}

	@ParameterizedTest
	@MethodSource("randomizeArgs")
	void testRandomize(VerbConstraint verb) throws IllegalVerbConstraintException {
		var randomizedVerb = VerbRandomizer.randomize(verb);
		Assertions.assertThat(VerbValidator.checkIllegalConstraints(randomizedVerb)).isEmpty();
		Assertions.assertThat(VerbValidator.checkMissingConstraints(randomizedVerb)).isEmpty();
	}
}
