package pl.com.piotrslowinski.model.commands;


public class AssignAuthorCommand implements Command {

    private Integer bookId, authorId;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    @Override
    public void validate(ValidationErrors errors) {

    }
}
