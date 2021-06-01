package br.com.marcello.SocialMeli.model;

import java.util.List;

public class Buyer {

    private Integer userId;
    private String username;
    private List<Seller> followingList;

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

    public List<Seller> getFollowingList() {
        return followingList;
    }

    public void setFollowingList(List<Seller> followingList) {
        this.followingList = followingList;
    }
}
