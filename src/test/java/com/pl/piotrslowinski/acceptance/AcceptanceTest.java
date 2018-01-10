package com.pl.piotrslowinski.acceptance;

import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by user on 07.01.2018.
 */
public class AcceptanceTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private TransactionTemplate tt;

    @After
    public void cleanUp() {
        tt.execute((c) -> {
            em.createNativeQuery("DELETE FROM lendings").executeUpdate();
            em.createNativeQuery("DELETE FROM specimens").executeUpdate();
            em.createNativeQuery("DELETE FROM books_authors").executeUpdate();
            em.createNativeQuery("DELETE FROM authors").executeUpdate();
            em.createNativeQuery("DELETE FROM books").executeUpdate();
            em.createNativeQuery("DELETE FROM genres").executeUpdate();
            em.createNativeQuery("DELETE FROM addresses").executeUpdate();
            em.createNativeQuery("DELETE FROM clients").executeUpdate();
            return null;
        });
    }
}