package br.com.marcello.SocialMeli.repositories.buyer;

import br.com.marcello.SocialMeli.dtos.buyers.BuyerDto;
import br.com.marcello.SocialMeli.dtos.sellers.SellerDto;
import br.com.marcello.SocialMeli.model.Buyer;
import br.com.marcello.SocialMeli.model.Seller;
import br.com.marcello.SocialMeli.model.User;

import java.util.List;

public interface BuyerRepository {

    List<SellerDto> orderFollowingListByNameDesc(List<SellerDto> followingList);

    List<SellerDto> orderFollowingListByNameAsc(List<SellerDto> followingList);

    boolean unfollow(Integer userId, Integer unfollowUserId);

    void addFollow(Seller seller, Integer userId);

    Buyer findById(Integer id);

    void addBuyer(String username);

    List<BuyerDto> getBuyers();

    Integer getMaxId();

}
