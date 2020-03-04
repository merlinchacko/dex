package com.assignment.mediasearch.validator;

import java.util.function.Supplier;

public class Validator {

    public static boolean isNull(Supplier<Object> toCheck) {
        try {
            return toCheck.get() == null;
        } catch (NullPointerException ex) {
            return true;
        }
    }

}
