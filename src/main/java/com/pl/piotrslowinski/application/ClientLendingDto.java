package com.pl.piotrslowinski.application;

import com.pl.piotrslowinski.model.Lending;

import java.time.LocalDate;


public class ClientLendingDto {

    private LocalDate lendingDate, returnDate;

    private String specimenCode;

    private String title;

    public ClientLendingDto(Lending lending) {
        this.lendingDate = lending.getLendingDate();
        this.returnDate = lending.getReturnDate();
        this.specimenCode = lending.getSpecimen().getCode();
        this.title = lending.getSpecimen().getBook().getTitle();
    }

    public LocalDate getLendingDate() {
        return lendingDate;
    }

    public void setLendingDate(LocalDate lendingDate) {
        this.lendingDate = lendingDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getSpecimenCode() {
        return specimenCode;
    }

    public void setSpecimenCode(String specimenCode) {
        this.specimenCode = specimenCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
