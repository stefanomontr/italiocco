package codeswitch.italiocco.dtos;

import codeswitch.italiocco.services.IllegalVerbConstraintChecker;
import codeswitch.italiocco.services.MissingVerbCostraintChecker;
import codeswitch.italiocco.services.VerbUtils;

public class IndicativeVerb extends VerbConstraint {

    protected void findIllegalVerbConstraints(IllegalVerbConstraintChecker checker) {
        checker.accept(
                "Incompatible Mode (!= Indicative): ".concat(String.valueOf(this.mode)),
                !VerbUtils.isIndicative(this)
        );
        checker.accept("Infinitive + Person", VerbUtils.hasPerson(this));
        checker.accept("Infinitive + Future", VerbUtils.isFuture(this));
        checker.accept("Infinitive + Duration", VerbUtils.hasDuration(this));
    }

    protected void findMissingVerbConstraints(MissingVerbCostraintChecker checker) {
        checker.accept("Indicative - Person", !VerbUtils.hasPerson(this));
        checker.accept("Indicative - Duration", !VerbUtils.hasDuration(this));
    }
}
