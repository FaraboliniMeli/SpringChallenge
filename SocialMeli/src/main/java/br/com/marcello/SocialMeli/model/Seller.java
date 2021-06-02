package br.com.marcello.SocialMeli.model;

import br.com.marcello.SocialMeli.dtos.sellers.SellerDto;

import java.util.List;

public class Seller {

    private Integer userId;
    private String username;
    private List<User> followerList;
    private List<SellerDto> followingList;
    private List<Post> postList;

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

    public List<User> getFollowerList() {
        return followerList;
    }

    public void setFollowerList(List<User> followerList) {
        this.followerList = followerList;
    }

    public List<SellerDto> getFollowingList() {
        return followingList;
    }

    public void setFollowingList(List<SellerDto> followingList) {
        this.followingList = followingList;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }
}
