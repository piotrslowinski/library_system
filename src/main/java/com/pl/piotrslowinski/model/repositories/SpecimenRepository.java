package com.pl.piotrslowinski.model.repositories;

import com.pl.piotrslowinski.model.Specimen;

/**
 * Created by user on 07.01.2018.
 */
public interface SpecimenRepository {

    void save(Specimen specimen);

    Specimen get(String code);

}