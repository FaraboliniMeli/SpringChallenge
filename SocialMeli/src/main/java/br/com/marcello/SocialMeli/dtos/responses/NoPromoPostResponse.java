package br.com.marcello.SocialMeli.dtos.responses;

public class NoPromoPostResponse {

    private String message;

    public NoPromoPostResponse() {
        this.message = "This seller haven't posted any promotional product.";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
