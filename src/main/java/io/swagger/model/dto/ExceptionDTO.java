package io.swagger.model.dto;

public class ExceptionDTO {
    private String reason;

    public ExceptionDTO() {
    }

    public ExceptionDTO(String reason) {
        this.reason = reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
