package codeswitch.italiocco.services;

import codeswitch.italiocco.exceptions.IllegalVerbConstraintException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class IllegalVerbConstraintChecker extends VerbConstraintChecker<IllegalVerbConstraintException> {

    @Override
    protected IllegalVerbConstraintException newException(String message) {
        return new IllegalVerbConstraintException(message);
    }

}
