package br.com.marcello.SocialMeli.dtos.responses;

public class SuccessUnfollow {

    private String message;

    public SuccessUnfollow() {
        this.message = "You unfollowed this seller";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
