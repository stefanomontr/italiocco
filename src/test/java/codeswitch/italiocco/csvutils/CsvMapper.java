package codeswitch.italiocco.csvutils;

import codeswitch.italiocco.dtos.LemmaDto;
import codeswitch.italiocco.dtos.VerbConstraint;
import org.codehaus.plexus.util.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface CsvMapper {

    @Mapping(target = "lemma", qualifiedByName = "toLemma")
    VerbConstraint toVerbConstraint(CsvMissingConstraintDto missingConstraintDto);

    @Named("toLemma")
    default LemmaDto toLemma(String paradigm) {
        if (StringUtils.isBlank(paradigm)) {
            return null;
        }
        var verbForms = paradigm.split(",");
        return LemmaDto.builder()
                .present(verbForms[0])
                .past(verbForms[1])
                .participle(verbForms[2])
                .build();
    }
}
