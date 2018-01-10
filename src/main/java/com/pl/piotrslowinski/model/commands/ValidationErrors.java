package com.pl.piotrslowinski.model.commands;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 07.01.2018.
 */
public class ValidationErrors {

    private Map<String, String> errors = new HashMap<>();

    public void add(String field, String error) {
        errors.put(field, error);
    }

    public boolean any() {
        return !errors.isEmpty();
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public String getMessage() {
        return "Invalid request parameters";
    }
}
