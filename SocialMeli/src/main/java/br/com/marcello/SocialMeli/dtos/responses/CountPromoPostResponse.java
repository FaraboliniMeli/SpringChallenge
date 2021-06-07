package br.com.marcello.SocialMeli.dtos.responses;

public class CountPromoPostResponse {

    Integer userId;
    String username;
    Integer promoProductsCount;

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

    public Integer getPromoProductsCount() {
        return promoProductsCount;
    }

    public void setPromoProductsCount(Integer promoProductsCount) {
        this.promoProductsCount = promoProductsCount;
    }
}
