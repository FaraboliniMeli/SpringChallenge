package br.com.marcello.SocialMeli.dtos.errors;

public class UnfollowBadRequest {

    private String message;

    public UnfollowBadRequest() {
        this.message = "Impossible to unfollow a buyer account";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
