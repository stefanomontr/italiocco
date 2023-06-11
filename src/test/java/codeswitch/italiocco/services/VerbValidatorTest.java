package codeswitch.italiocco.services;

import java.util.List;
import java.util.stream.Stream;

import codeswitch.italiocco.constants.Diathesis;
import codeswitch.italiocco.constants.Duration;
import codeswitch.italiocco.constants.Tense;
import codeswitch.italiocco.exceptions.MissingVerbConstraintException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import codeswitch.italiocco.constants.Mode;
import codeswitch.italiocco.dtos.VerbConstraint;
import codeswitch.italiocco.exceptions.IllegalVerbConstraintException;

class VerbValidatorTest {
	
	private static Stream<Arguments> illegalVerbConstraintArgs() {
		// conditional
		var conditionalAndFuture = VerbConstraint.builder().mode(Mode.CONDITIONAL).tense(Tense.FUTURE).build();

		// infinitive
		var infinitiveAndPerson = VerbConstraint.builder().mode(Mode.INFINITIVE).person(1).build();
		var infinitiveAndFuture = VerbConstraint.builder().mode(Mode.INFINITIVE).tense(Tense.FUTURE).build();
		var infinitiveAndDuration = VerbConstraint.builder().mode(Mode.INFINITIVE).duration(Duration.PUNCTUAL).build();
		var infinitiveAndPersonAndFuture =  VerbConstraint.builder()
				.mode(Mode.INFINITIVE).person(1).tense(Tense.FUTURE).build();
		var infinitiveAndPersonAndDuration =  VerbConstraint.builder()
				.mode(Mode.INFINITIVE).person(1).duration(Duration.PUNCTUAL).build();
		var infinitiveAndFutureAndDuration =  VerbConstraint.builder()
				.mode(Mode.INFINITIVE).tense(Tense.FUTURE).duration(Duration.PUNCTUAL).build();

		// gerund
		var gerundAndPerson = VerbConstraint.builder().mode(Mode.GERUND).person(1).build();
		var gerundAndFuture = VerbConstraint.builder().mode(Mode.GERUND).tense(Tense.FUTURE).build();
		var gerundAndDuration = VerbConstraint.builder().mode(Mode.GERUND).duration(Duration.PUNCTUAL).build();
		var gerundAndPersonAndFuture =  VerbConstraint.builder()
				.mode(Mode.GERUND).person(1).tense(Tense.FUTURE).build();
		var gerundAndPersonAndDuration =  VerbConstraint.builder()
				.mode(Mode.GERUND).person(1).duration(Duration.PUNCTUAL).build();
		var gerundAndFutureAndDuration =  VerbConstraint.builder()
				.mode(Mode.GERUND).tense(Tense.FUTURE).duration(Duration.PUNCTUAL).build();

		return Stream.of(
				// conditional
				Arguments.of(conditionalAndFuture, List.of("Conditional + Future")),

				// infinitive
				Arguments.of(infinitiveAndPerson, List.of("Infinitive + Person")),
				Arguments.of(infinitiveAndFuture, List.of("Infinitive + Future")),
				Arguments.of(infinitiveAndDuration, List.of("Infinitive + Duration")),
				Arguments.of(infinitiveAndPersonAndFuture, List.of("Infinitive + Person", "Infinitive + Future")),
				Arguments.of(infinitiveAndPersonAndDuration, List.of("Infinitive + Person", "Infinitive + Duration")),
				Arguments.of(infinitiveAndFutureAndDuration, List.of("Infinitive + Future", "Infinitive + Duration")),

				// gerund
				Arguments.of(gerundAndPerson, List.of("Gerund + Person")),
				Arguments.of(gerundAndFuture, List.of("Gerund + Future")),
				Arguments.of(gerundAndDuration, List.of("Gerund + Duration")),
				Arguments.of(gerundAndPersonAndFuture, List.of("Gerund + Person", "Gerund + Future")),
				Arguments.of(gerundAndPersonAndDuration, List.of("Gerund + Person", "Gerund + Duration")),
				Arguments.of(gerundAndFutureAndDuration, List.of("Gerund + Future", "Gerund + Duration"))
		);
	}

	@ParameterizedTest
	@MethodSource("illegalVerbConstraintArgs")
	void testCheckIllegalVerbConstraint(VerbConstraint verb, List<String> errorMessages) {
		var actual = VerbValidator.checkIllegalConstraints(verb);
		Assertions.assertThat(actual)
			.isNotEmpty()
			.allMatch(exception -> exception.getClass().equals(IllegalVerbConstraintException.class));
		Assertions.assertThat(actual.stream().map(Throwable::getMessage).toList())
			.containsExactlyElementsOf(errorMessages);
	}

	private static Stream<Arguments> missingVerbConstraintArgs() {
		// indicative
		var indicativeWithoutPersonTenseDurationDiathesis = VerbConstraint.builder().mode(Mode.INDICATIVE).build();
		var indicativeWithoutPersonTenseDuration = VerbConstraint.builder()
				.mode(Mode.INDICATIVE).diathesis(Diathesis.ACTIVE).build();
		var indicativeWithoutPersonDurationDiathesis = VerbConstraint.builder()
				.mode(Mode.INDICATIVE).tense(Tense.PRESENT).build();
		var indicativeWithoutTenseDurationDiathesis = VerbConstraint.builder()
				.mode(Mode.INDICATIVE).person(1).build();
		var indicativeWithoutPersonTenseDiathesis = VerbConstraint.builder()
				.mode(Mode.INDICATIVE).duration(Duration.PUNCTUAL).build();
		var indicativeWithoutPerson = VerbConstraint.builder()
				.mode(Mode.INDICATIVE)
				.tense(Tense.PRESENT)
				.diathesis(Diathesis.ACTIVE)
				.duration(Duration.PUNCTUAL)
				.build();
		var indicativeWithoutTense = VerbConstraint.builder()
				.mode(Mode.INDICATIVE)
				.person(1)
				.diathesis(Diathesis.ACTIVE)
				.duration(Duration.PUNCTUAL)
				.build();
		var indicativeWithoutDuration = VerbConstraint.builder()
				.mode(Mode.INDICATIVE)
				.tense(Tense.PRESENT)
				.diathesis(Diathesis.ACTIVE)
				.person(1)
				.build();
		var indicativeWithoutDiathesis = VerbConstraint.builder()
				.mode(Mode.INDICATIVE)
				.tense(Tense.PRESENT)
				.duration(Duration.PUNCTUAL)
				.person(1)
				.build();

		// conditional
		var conditionalWithoutPersonTenseDurationDiathesis = VerbConstraint.builder().mode(Mode.CONDITIONAL).build();
		var conditionalWithoutPersonTenseDuration = VerbConstraint.builder()
				.mode(Mode.CONDITIONAL).diathesis(Diathesis.ACTIVE).build();
		var conditionalWithoutPersonDurationDiathesis = VerbConstraint.builder()
				.mode(Mode.CONDITIONAL).tense(Tense.PRESENT).build();
		var conditionalWithoutTenseDurationDiathesis = VerbConstraint.builder()
				.mode(Mode.CONDITIONAL).person(1).build();
		var conditionalWithoutPersonTenseDiathesis = VerbConstraint.builder()
				.mode(Mode.CONDITIONAL).duration(Duration.PUNCTUAL).build();
		var conditionalWithoutPerson = VerbConstraint.builder()
				.mode(Mode.CONDITIONAL)
				.tense(Tense.PRESENT)
				.diathesis(Diathesis.ACTIVE)
				.duration(Duration.PUNCTUAL)
				.build();
		var conditionalWithoutTense = VerbConstraint.builder()
				.mode(Mode.CONDITIONAL)
				.person(1)
				.diathesis(Diathesis.ACTIVE)
				.duration(Duration.PUNCTUAL)
				.build();
		var conditionalWithoutDuration = VerbConstraint.builder()
				.mode(Mode.CONDITIONAL)
				.tense(Tense.PRESENT)
				.diathesis(Diathesis.ACTIVE)
				.person(1)
				.build();
		var conditionalWithoutDiathesis = VerbConstraint.builder()
				.mode(Mode.CONDITIONAL)
				.tense(Tense.PRESENT)
				.duration(Duration.PUNCTUAL)
				.person(1)
				.build();

		// infinitive
		var infinitiveWithoutTenseDiathesis = VerbConstraint.builder().mode(Mode.INFINITIVE).build();
		var infinitiveWithoutTense = VerbConstraint.builder().mode(Mode.INFINITIVE).diathesis(Diathesis.ACTIVE).build();
		var infinitiveWithoutDiathesis = VerbConstraint.builder().mode(Mode.INFINITIVE).tense(Tense.PRESENT).build();

		// gerund
		var gerundWithoutTenseDiathesis = VerbConstraint.builder().mode(Mode.GERUND).build();
		var gerundWithoutTense = VerbConstraint.builder().mode(Mode.GERUND).diathesis(Diathesis.ACTIVE).build();
		var gerundWithoutDiathesis = VerbConstraint.builder().mode(Mode.GERUND).tense(Tense.PRESENT).build();

		// no mode
		var noMode = VerbConstraint.builder().tense(Tense.PRESENT).diathesis(Diathesis.ACTIVE).build();

		return Stream.of(
				// indicative
				Arguments.of(
						indicativeWithoutPersonTenseDurationDiathesis,
						List.of(
								"Indicative - Person",
								"- Tense",
								"Indicative - Duration",
								"- Diathesis"
						)
				),
				Arguments.of(
						indicativeWithoutPersonTenseDuration,
						List.of(
								"Indicative - Person",
								"- Tense",
								"Indicative - Duration"
						)
				),
				Arguments.of(
						indicativeWithoutPersonDurationDiathesis,
						List.of(
								"Indicative - Person",
								"Indicative - Duration",
								"- Diathesis"
						)
				),
				Arguments.of(
						indicativeWithoutTenseDurationDiathesis,
						List.of(
								"- Tense",
								"Indicative - Duration",
								"- Diathesis"
						)
				),
				Arguments.of(
						indicativeWithoutPersonTenseDiathesis,
						List.of(
								"Indicative - Person",
								"- Tense",
								"- Diathesis"
						)
				),
				Arguments.of(indicativeWithoutPerson, List.of("Indicative - Person")),
				Arguments.of(indicativeWithoutTense, List.of("- Tense")),
				Arguments.of(indicativeWithoutDuration, List.of("Indicative - Duration")),
				Arguments.of(indicativeWithoutDiathesis, List.of("- Diathesis")),

				// conditional
				Arguments.of(
						conditionalWithoutPersonTenseDurationDiathesis,
						List.of(
								"Conditional - Person",
								"- Tense",
								"Conditional - Duration",
								"- Diathesis"
						)
				),
				Arguments.of(
						conditionalWithoutPersonTenseDuration,
						List.of(
								"Conditional - Person",
								"- Tense",
								"Conditional - Duration"
						)
				),
				Arguments.of(
						conditionalWithoutPersonDurationDiathesis,
						List.of(
								"Conditional - Person",
								"Conditional - Duration",
								"- Diathesis"
						)
				),
				Arguments.of(
						conditionalWithoutTenseDurationDiathesis,
						List.of(
								"- Tense",
								"Conditional - Duration",
								"- Diathesis"
						)
				),
				Arguments.of(
						conditionalWithoutPersonTenseDiathesis,
						List.of(
								"Conditional - Person",
								"- Tense",
								"- Diathesis"
						)
				),
				Arguments.of(conditionalWithoutPerson, List.of("Conditional - Person")),
				Arguments.of(conditionalWithoutTense, List.of("- Tense")),
				Arguments.of(conditionalWithoutDuration, List.of("Conditional - Duration")),
				Arguments.of(conditionalWithoutDiathesis, List.of("- Diathesis")),

				// infinitive
				Arguments.of(infinitiveWithoutTenseDiathesis, List.of("- Tense", "- Diathesis")),
				Arguments.of(infinitiveWithoutTense, List.of("- Tense")),
				Arguments.of(infinitiveWithoutDiathesis, List.of("- Diathesis")),

				// gerund
				Arguments.of(gerundWithoutTenseDiathesis, List.of("- Tense", "- Diathesis")),
				Arguments.of(gerundWithoutTense, List.of("- Tense")),
				Arguments.of(gerundWithoutDiathesis, List.of("- Diathesis")),

				// no mode
				Arguments.of(noMode, List.of("- Mode"))
		);
	}

	@ParameterizedTest
	@MethodSource("missingVerbConstraintArgs")
	void testCheckMissingVerbConstraint(VerbConstraint verb, List<String> errorMessages) {
		var actual = VerbValidator.checkMissingConstraints(verb);
		Assertions.assertThat(actual)
				.isNotEmpty()
				.allMatch(exception -> exception.getClass().equals(MissingVerbConstraintException.class));
		Assertions.assertThat(actual.stream().map(Throwable::getMessage).sorted().toList())
				.containsExactlyElementsOf(errorMessages.stream().sorted().toList());
	}

}
