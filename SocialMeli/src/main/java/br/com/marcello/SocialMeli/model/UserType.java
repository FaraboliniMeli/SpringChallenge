package br.com.marcello.SocialMeli.model;

public enum UserType {

    BUYER("Buyer"),
    SELLER("Seller");

    private String type;

    UserType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User type: " + this.type;
    }

}
