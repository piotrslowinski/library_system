package pl.com.piotrslowinski.model.commands;


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
