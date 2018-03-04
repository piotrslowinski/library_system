package pl.com.piotrslowinski.model.repositories;

import pl.com.piotrslowinski.model.Specimen;


public interface SpecimenRepository {

    void save(Specimen specimen);

    Specimen get(String code);

    void remove(String code);

}