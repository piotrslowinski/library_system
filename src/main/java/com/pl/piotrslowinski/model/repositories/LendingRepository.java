package com.pl.piotrslowinski.model.repositories;


import com.pl.piotrslowinski.model.Lending;
import com.pl.piotrslowinski.model.Specimen;


public interface LendingRepository {

    void save(Lending lending);

    Lending get(Specimen specimen);
}
