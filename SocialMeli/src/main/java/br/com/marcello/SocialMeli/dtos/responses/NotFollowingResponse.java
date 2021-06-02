package br.com.marcello.SocialMeli.dtos.responses;

public class NotFollowingResponse {

    private String message;

    public NotFollowingResponse() {
        this.message = "Impossible to unfollow. You already don't follow this seller";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
