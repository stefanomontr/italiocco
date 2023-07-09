package codeswitch.italiocco.services;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public abstract class VerbConstraintChecker<E extends Throwable> {

    @Getter
    private List<E> exceptions;

    abstract E newException(String message);

    public void accept(String errorMsg, Boolean condition) {
        if (condition) {
            exceptions.add(newException(errorMsg));
        }
    }

    public void check() throws E {
        if (!exceptions.isEmpty()) {
            var msg = exceptions.stream()
                    .map(Throwable::getMessage)
                    .collect(Collectors.joining("; "));
            throw newException(msg);
        }
    }
}
