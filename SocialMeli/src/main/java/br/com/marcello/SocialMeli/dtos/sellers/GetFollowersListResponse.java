package br.com.marcello.SocialMeli.dtos.sellers;

import br.com.marcello.SocialMeli.dtos.user.UserDto;

import java.util.List;

public class GetFollowersListResponse {

    private Integer userId;
    private String username;
    private List<UserDto> followers;

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

    public List<UserDto> getFollowers() {
        return followers;
    }

    public void setFollowers(List<UserDto> followers) {
        this.followers = followers;
    }
}
