package br.com.marcello.SocialMeli.dtos.errors;

public class UserNotFoundResponse {

    private String message;

    public UserNotFoundResponse() {
        this.message = "User not found.";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
