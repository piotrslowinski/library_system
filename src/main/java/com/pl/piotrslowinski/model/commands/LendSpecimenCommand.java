package com.pl.piotrslowinski.model.commands;


public class LendSpecimenCommand implements Command {

    private Integer clientId;

    private String code;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }



    @Override
    public void validate(ValidationErrors errors) {
        validatePresence(errors,"code", code);
    }
}
