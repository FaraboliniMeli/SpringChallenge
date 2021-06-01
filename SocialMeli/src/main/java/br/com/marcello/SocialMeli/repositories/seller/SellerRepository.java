package br.com.marcello.SocialMeli.repositories.seller;

import br.com.marcello.SocialMeli.dtos.sellers.SellerDto;
import br.com.marcello.SocialMeli.model.Buyer;
import br.com.marcello.SocialMeli.model.Seller;

import java.util.List;

public interface SellerRepository {

    Integer getFollowersCount(Integer sellerId);

    void addFollower(Buyer buyer, Integer sellerId);

    Seller findById(Integer id);

    void addSeller(String username);

    List<SellerDto> getSellers();

    Integer getMaxId();

}
