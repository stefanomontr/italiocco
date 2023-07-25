package codeswitch.italiocco.services;

import codeswitch.italiocco.exceptions.MissingVerbConstraintException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MissingVerbCostraintChecker extends VerbConstraintChecker<MissingVerbConstraintException> {

    @Override
    protected MissingVerbConstraintException newException(String message) {
        return new MissingVerbConstraintException(message);
    }

}
