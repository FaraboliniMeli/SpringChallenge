package br.com.marcello.SocialMeli.model;

import br.com.marcello.SocialMeli.dtos.sellers.SellerDto;

import java.util.List;

public class Buyer {

    private Integer userId;
    private String username;
    private List<SellerDto> followingList;

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

    public List<SellerDto> getFollowingList() {
        return followingList;
    }

    public void setFollowingList(List<SellerDto> followingList) {
        this.followingList = followingList;
    }
}
