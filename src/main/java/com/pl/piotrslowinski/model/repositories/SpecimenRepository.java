package com.pl.piotrslowinski.model.repositories;

import com.pl.piotrslowinski.model.Specimen;


public interface SpecimenRepository {

    void save(Specimen specimen);

    Specimen get(String code);

    void remove(String code);

}