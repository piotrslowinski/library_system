package pl.com.piotrslowinski.application;

import java.time.LocalDate;

public class BookSearchCriteria {

    private String titleQuery;
    private String isbnQuery;
    private LocalDate publishedAt;
    private String authorsFirstName;
    private String authorsLastName;
    private String genreName;
    private int pageSize = 20;
    private int pageNumber = 1;

    public String getTitleQuery() {
        return titleQuery;
    }

    public void setTitleQuery(String titleQuery) {
        this.titleQuery = titleQuery;
    }

    public String getIsbnQuery() {
        return isbnQuery;
    }

    public void setIsbnQuery(String isbnQuery) {
        this.isbnQuery = isbnQuery;
    }

    public LocalDate getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDate publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getAuthorsFirstName() {
        return authorsFirstName;
    }

    public void setAuthorsFirstName(String authorsFirstName) {
        this.authorsFirstName = authorsFirstName;
    }

    public String getAuthorsLastName() {
        return authorsLastName;
    }

    public void setAuthorsLastName(String authorsLastName) {
        this.authorsLastName = authorsLastName;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
