package br.com.marcello.SocialMeli.dtos.errors;

public class GetFollowersBadRequest {

    private String message;

    public GetFollowersBadRequest() {
        this.message = "Buyer account doesn't has followers";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
