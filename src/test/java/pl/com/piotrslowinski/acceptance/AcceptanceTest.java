package pl.com.piotrslowinski.acceptance;

import org.junit.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


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
            em.createNativeQuery("ALTER TABLE lendings AUTO_INCREMENT = 1").executeUpdate();
            em.createNativeQuery("ALTER TABLE specimens AUTO_INCREMENT = 1").executeUpdate();
            em.createNativeQuery("ALTER TABLE books_authors AUTO_INCREMENT = 1").executeUpdate();
            em.createNativeQuery("ALTER TABLE authors AUTO_INCREMENT = 1").executeUpdate();
            em.createNativeQuery("ALTER TABLE books AUTO_INCREMENT = 1").executeUpdate();
            em.createNativeQuery("ALTER TABLE genres AUTO_INCREMENT = 1").executeUpdate();
            em.createNativeQuery("ALTER TABLE addresses AUTO_INCREMENT = 1").executeUpdate();
            em.createNativeQuery("ALTER TABLE clients AUTO_INCREMENT = 1").executeUpdate();

            return null;
        });
    }
}