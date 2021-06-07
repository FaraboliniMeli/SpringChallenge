package br.com.marcello.SocialMeli.dtos.responses;

public class AlreadyFollowResponse {

    private String message;

    public AlreadyFollowResponse() {
        this.message = "You already follows this seller.";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
