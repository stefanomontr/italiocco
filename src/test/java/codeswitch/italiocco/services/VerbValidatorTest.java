package codeswitch.italiocco.services;

import codeswitch.italiocco.csvutils.CsvMapper;
import codeswitch.italiocco.csvutils.CsvMapperImpl;
import codeswitch.italiocco.csvutils.CsvMissingConstraintDto;
import com.opencsv.bean.CsvToBeanBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

class VerbValidatorTest {
	private CsvMapper csvMapper = new CsvMapperImpl();

	public static List<CsvMissingConstraintDto> missingConstraintArgs() {
		return parseCsv("/missing-verb-constraints.csv", CsvMissingConstraintDto.class);
	}

	@ParameterizedTest
	@MethodSource("missingConstraintArgs")
	void testMissingConstraints(CsvMissingConstraintDto missingConstraintDto) {
		var verbConstraint = csvMapper.toVerbConstraint(missingConstraintDto);
		Assertions.assertThat(VerbValidator.checkIllegalConstraints(verbConstraint)).isEmpty();
		Assertions.assertThat(VerbValidator.checkMissingConstraints(verbConstraint))
				.map(Throwable::getMessage)
				.containsExactlyInAnyOrderElementsOf(missingConstraintDto.getExceptions());
	}

	private static <T> List<T> parseCsv(String resourceLocation, Class<T> dtoClass) {
		var csvUrl = VerbValidatorTest.class.getResource(resourceLocation);
		Assertions.assertThat(csvUrl).isNotNull();
		try (var fileReader = new FileReader(csvUrl.getFile())) {
			return new CsvToBeanBuilder<T>(fileReader)
					.withType(dtoClass)
					.withSeparator(';')
					.build()
					.parse();
		}
		catch (IOException e) {
			Assertions.fail("Couldn't find file");
		}
		return List.of();
	}
}
