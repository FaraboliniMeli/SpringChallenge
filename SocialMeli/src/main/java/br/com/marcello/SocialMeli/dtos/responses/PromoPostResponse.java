package br.com.marcello.SocialMeli.dtos.responses;

import br.com.marcello.SocialMeli.model.Post;

import java.util.List;

public class PromoPostResponse {

    Integer userId;
    String username;
    List<Post> promoPosts;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Post> getPromoPosts() {
        return promoPosts;
    }

    public void setPromoPosts(List<Post> promoPosts) {
        this.promoPosts = promoPosts;
    }
}
