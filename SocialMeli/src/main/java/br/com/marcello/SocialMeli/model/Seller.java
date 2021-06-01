package br.com.marcello.SocialMeli.model;

import java.util.List;

public class Seller {

    private Integer userId;
    private String username;
    private List<Buyer> followerList;

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

    public List<Buyer> getFollowerList() {
        return followerList;
    }

    public void setFollowerList(List<Buyer> followerList) {
        this.followerList = followerList;
    }
}
