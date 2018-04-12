package pl.com.piotrslowinski.infrastructure;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.piotrslowinski.application.*;
import pl.com.piotrslowinski.model.Book;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.List;

@Component
public class JPACriteriaBookFinder implements BookFinder {

    private EntityManager entityManager;

    public JPACriteriaBookFinder(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<BookDto> getAll() {
        List<BookDto> results = entityManager.createQuery("SELECT NEW pl.com.piotrslowinski.application.BookDto(" +
                "b.id, b.title, b.isbn) FROM Book b").getResultList();
        return results;
    }

    @Override
    @Transactional
    public DetailedBookDto getBookDetails(Integer bookId) {
        Book book = entityManager.find(Book.class, bookId);
        if(book == null)
            throw new NoSuchEntityException();
        return new DetailedBookDto(book);
    }

    @Override
    public BookSearchResults search(BookSearchCriteria criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookDto> cq = cb.createQuery(BookDto.class);
        Root book = cq.from(Book.class);
        cq.select(cb.construct(BookDto.class, book.get("id"), book.get("title"), book.get("isbn")));

        Predicate predicate = buildPredicate(criteria, cb, book);

        cq.where(predicate);
        cq.distinct(true);
        Query query = entityManager.createQuery(cq);
        query.setMaxResults(criteria.getPageSize());
        query.setFirstResult((criteria.getPageNumber() - 1) * criteria.getPageSize());

        List<BookDto> results = query.getResultList();
        BookSearchResults bookSearchResults = new BookSearchResults();
        bookSearchResults.setResults(results);

        int total = searchTotalCount(criteria);
        bookSearchResults.setTotalCount(total);
        bookSearchResults.setPageNumber(criteria.getPageNumber());
        bookSearchResults.setPagesCount(total / criteria.getPageSize() +
                (total % criteria.getPageSize() == 0 ? 0 : 1));

        return bookSearchResults;
    }

    private int searchTotalCount(BookSearchCriteria criteria) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root book = cq.from(Book.class);
        cq.select(cb.count(book));
        Predicate predicate = buildPredicate(criteria, cb, book);
        cq.where(predicate);
        Query query = entityManager.createQuery(cq);
        return ((Long) query.getSingleResult()).intValue();
    }

    private Predicate buildPredicate(BookSearchCriteria criteria, CriteriaBuilder cb, Root book) {
        Predicate predicate = cb.conjunction();
        predicate = addTitlePredicate(criteria, cb, book, predicate);
        predicate = addIsbnPredicate(criteria, cb, book, predicate);
        predicate = addGenrePredicate(criteria, cb, book, predicate);
        predicate = addAuthorsLastNamePredicate(criteria, cb, book, predicate);
        predicate = addAuthorsFirstNamePredicate(criteria, cb, book, predicate);

        return predicate;
    }

    private Predicate addAuthorsFirstNamePredicate(BookSearchCriteria criteria, CriteriaBuilder cb, Root book, Predicate predicate) {
        if (criteria.getAuthorsFirstName() != null){
            Join authors = book.join("authors");
            predicate = cb.and(predicate, authors.get("firstName").in(criteria.getAuthorsFirstName()));
        }
        return predicate;
    }

    private Predicate addAuthorsLastNamePredicate(BookSearchCriteria criteria, CriteriaBuilder cb, Root book, Predicate predicate) {
        if (criteria.getAuthorsLastName() != null){
            Join authors = book.join("authors");
            predicate = cb.and(predicate, (authors.get("lastName").in(criteria.getAuthorsLastName())));
        }
        return predicate;
    }

    private Predicate addGenrePredicate(BookSearchCriteria criteria, CriteriaBuilder cb, Root book, Predicate predicate) {
        if (criteria.getGenreName() != null) {
            Join genre = book.join("genre");
            predicate = cb.and(predicate, genre.get("name").in(criteria.getGenreName()));
        }
        return predicate;
    }

    private Predicate addIsbnPredicate(BookSearchCriteria criteria, CriteriaBuilder cb, Root book, Predicate predicate) {
        if (criteria.getIsbnQuery() != null)  {
            predicate = cb.and(predicate, cb.like(book.get("isbn"), criteria.getIsbnQuery() + "%"));
        }
        return predicate;
    }

    private Predicate addTitlePredicate(BookSearchCriteria criteria, CriteriaBuilder cb, Root book, Predicate predicate) {
        if (criteria.getTitleQuery() != null) {
            predicate = cb.and(predicate, cb.like(book.get("title"), criteria.getTitleQuery() + "%"));
        }

        return predicate;
    }
}
