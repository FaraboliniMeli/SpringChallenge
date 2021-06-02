package br.com.marcello.SocialMeli.dtos.responses;

import br.com.marcello.SocialMeli.model.Post;

import java.util.List;

public class TwoWeeksPostsResponse {

    private Integer userId;
    private List<Post> posts;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
