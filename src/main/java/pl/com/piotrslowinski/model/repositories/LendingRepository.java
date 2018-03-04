package pl.com.piotrslowinski.model.repositories;


import pl.com.piotrslowinski.model.Lending;
import pl.com.piotrslowinski.model.Specimen;


public interface LendingRepository {

    void save(Lending lending);

    Lending get(Specimen specimen);
}
