package br.com.marcello.SocialMeli.dtos.responses;

public class NoFollowersResponse {

    private String message;

    public NoFollowersResponse() {
        this.message = "You don't have followers.";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
