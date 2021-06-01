package br.com.marcello.SocialMeli.dtos.responses;

public class SuccessFollow {

    private String message;

    public SuccessFollow() {
        this.message = "Now you are following this Seller!";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
