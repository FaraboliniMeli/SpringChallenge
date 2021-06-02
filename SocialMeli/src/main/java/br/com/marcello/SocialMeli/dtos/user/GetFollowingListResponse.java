package br.com.marcello.SocialMeli.dtos.user;

import br.com.marcello.SocialMeli.dtos.sellers.SellerDto;

import java.util.List;

public class GetFollowingListResponse {

    private Integer userId;
    private String username;
    private List<SellerDto> followed;

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

    public List<SellerDto> getFollowed() {
        return followed;
    }

    public void setFollowed(List<SellerDto> followed) {
        this.followed = followed;
    }

}
