package com.pl.piotrslowinski.model.commands;

/**
 * Created by user on 11.01.2018.
 */
public class ReturnSpecimenCommand implements Command {

    private Integer clientId;

    private String code;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void validate(ValidationErrors errors) {
        validatePresence(errors,"code", code);
    }
}
