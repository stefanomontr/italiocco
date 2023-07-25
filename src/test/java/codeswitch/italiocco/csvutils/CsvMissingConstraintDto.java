package codeswitch.italiocco.csvutils;

import codeswitch.italiocco.constants.*;
import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import lombok.Getter;

import java.util.List;

@Getter
public class CsvMissingConstraintDto {

    @CsvBindByName(column = "MODE")
    private Mode mode;
    @CsvBindByName(column = "LEMMA")
    private String lemma;
    @CsvBindByName(column = "PERSON")
    private Integer person;
    @CsvBindByName(column = "TENSE")
    private Tense tense;
    @CsvBindByName(column = "DIATHESIS")
    private Diathesis diathesis;
    @CsvBindByName(column = "DURATION")
    private Duration duration;
    @CsvBindByName(column = "COMPOSITION")
    private Composition composition;
    @CsvBindAndSplitByName(column = "EXCEPTIONS", splitOn = ",", elementType = String.class)
    private List<String> exceptions;

}
