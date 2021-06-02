package br.com.marcello.SocialMeli.dtos.responses;

public class SuccessPost {

    private String message;

    public SuccessPost() {
        this.message = "Congratulations !! You have made a post!";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
