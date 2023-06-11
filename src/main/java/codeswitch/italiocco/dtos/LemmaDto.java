package codeswitch.italiocco.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LemmaDto {
    private String present;
    private String past;
    private String participle;
}
