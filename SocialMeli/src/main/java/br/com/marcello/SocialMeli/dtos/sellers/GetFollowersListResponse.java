package br.com.marcello.SocialMeli.dtos.sellers;

import br.com.marcello.SocialMeli.dtos.buyers.BuyerDto;
import br.com.marcello.SocialMeli.model.Buyer;

import java.util.List;

public class GetFollowersListResponse {

    private Integer userId;
    private String username;
    private List<BuyerDto> followers;

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

    public List<BuyerDto> getFollowers() {
        return followers;
    }

    public void setFollowers(List<BuyerDto> followers) {
        this.followers = followers;
    }
}
