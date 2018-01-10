package com.pl.piotrslowinski.model;

import java.time.Clock;
import java.time.LocalDate;

/**
 * Created by user on 07.01.2018.
 */
public interface TimeProvider {

    LocalDate MAX_DATE = LocalDate.parse("9999-01-01");

    Clock clock();

    default LocalDate today() {
        return LocalDate.now(clock());
    }

}