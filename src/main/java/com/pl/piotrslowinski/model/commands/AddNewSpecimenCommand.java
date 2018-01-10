package com.pl.piotrslowinski.model.commands;

/**
 * Created by user on 07.01.2018.
 */
public class AddNewSpecimenCommand implements Command {

    private Integer bookId;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void validate(ValidationErrors validationErrors) {
        validatePresence(validationErrors, "code", code);
    }


    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
}
