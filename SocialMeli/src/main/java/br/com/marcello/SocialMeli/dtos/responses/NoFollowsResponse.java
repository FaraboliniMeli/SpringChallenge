package br.com.marcello.SocialMeli.dtos.responses;

public class NoFollowsResponse {

    private String message;

    public NoFollowsResponse() {
        this.message = "You don't follow anyone.";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
