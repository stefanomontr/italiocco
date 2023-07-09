package codeswitch.italiocco.dtos;

import codeswitch.italiocco.services.IllegalVerbConstraintChecker;
import codeswitch.italiocco.services.MissingVerbCostraintChecker;
import codeswitch.italiocco.services.VerbUtils;

public class ConditionalVerb extends VerbConstraint {

    protected void findIllegalVerbConstraints(IllegalVerbConstraintChecker checker) {
        checker.accept(
                "Incompatible Mode (!= Conditional): ".concat(String.valueOf(this.mode)),
                !VerbUtils.isConditional(this)
        );
    }

    protected void findMissingVerbConstraints(MissingVerbCostraintChecker checker) {
        checker.accept("Conditional - Person", !VerbUtils.hasPerson(this));
        checker.accept("Conditional - Duration", !VerbUtils.hasDuration(this));
    }
}
