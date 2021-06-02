package br.com.marcello.SocialMeli.dtos.errors;

public class NoPostsLastTwoWeeksResponse {

    private String message;

    public NoPostsLastTwoWeeksResponse() {
        this.message = "Followed sellers haven't made any post last two weeks";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
