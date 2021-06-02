package br.com.marcello.SocialMeli.dtos.errors;

public class InvalidOrder {

    private String message;

    public InvalidOrder() {
        this.message = "Invalid parameter informed, please try again.";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
