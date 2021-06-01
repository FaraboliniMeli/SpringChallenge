package br.com.marcello.SocialMeli.repositories.buyer;

import br.com.marcello.SocialMeli.dtos.buyers.BuyerDto;
import br.com.marcello.SocialMeli.model.Buyer;
import br.com.marcello.SocialMeli.model.Seller;

import java.util.List;

public interface BuyerRepository {

    void addFollow(Seller seller, Integer buyerId);

    Buyer findById(Integer id);

    void addBuyer(String username);

    List<BuyerDto> getBuyers();

    Integer getMaxId();

}
