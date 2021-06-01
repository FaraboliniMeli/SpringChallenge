package br.com.marcello.SocialMeli.dtos.errors;

public class FollowBadRequest {

    private String message;

    public FollowBadRequest() {
        this.message = "Invalid operation. You are trying to follow a buyer account or atempting to follow someone using a seller account";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
