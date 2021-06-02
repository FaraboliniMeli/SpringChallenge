package br.com.marcello.SocialMeli.dtos.errors;

public class PostBadRequest {

    private String message;

    public PostBadRequest() {
        this.message = "A buyer account cannot post products to sell. Please create a seller account.";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
