package codeswitch.italiocco.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Builder
@AllArgsConstructor
@Getter
public class Lemma {
    @Id
    @Column(name = "ID")
    private String present;

    @Column(name = "PAST")
    private String past;

    @Column(name = "PARTICIPLE")
    private String participle;
}
