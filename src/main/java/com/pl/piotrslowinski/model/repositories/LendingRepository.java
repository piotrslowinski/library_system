package com.pl.piotrslowinski.model.repositories;


import com.pl.piotrslowinski.model.Lending;

/**
 * Created by user on 08.01.2018.
 */
public interface LendingRepository {

    void save(Lending lending);

    Lending get(String code);
}
