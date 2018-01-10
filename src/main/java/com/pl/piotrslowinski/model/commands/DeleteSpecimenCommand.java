package com.pl.piotrslowinski.model.commands;

/**
 * Created by user on 10.01.2018.
 */
public class DeleteSpecimenCommand implements Command {

    private Integer bookId;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    @Override
    public void validate(ValidationErrors errors) {
        validatePresence(errors,"code", code);
    }
}
