package pl.com.piotrslowinski.model.repositories;

import pl.com.piotrslowinski.model.Specimen;

import java.util.Optional;


public interface SpecimenRepository {

    void save(Specimen specimen);

    Optional<Specimen> get(String code);

    void remove(String code);

    boolean isSpecimenPresent(String code);
}