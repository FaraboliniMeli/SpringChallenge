package br.com.marcello.SocialMeli.repositories.buyer;

import br.com.marcello.SocialMeli.dtos.buyers.BuyerDto;
import br.com.marcello.SocialMeli.dtos.sellers.SellerDto;
import br.com.marcello.SocialMeli.model.Buyer;
import br.com.marcello.SocialMeli.model.Seller;
import br.com.marcello.SocialMeli.model.User;

import java.util.List;

public interface BuyerRepository {

    /**
     * Checks if user already follows the seller.
     * @param userId User ID which will follow seller.
     * @param userIdToFollow Seller ID which will be followed.
     * @return alreadyFollow True if user already follows the seller. On the contrary, false.
     */
    Boolean alreadyFollows(Integer userId, Integer userIdToFollow);

    /**
     * Sorts following list by name in descending order.
     * @param followingList List of following Sellers of Buyer.
     * @return followingList Sorted following List.
     */
    List<SellerDto> orderFollowingListByNameDesc(List<SellerDto> followingList);

    /**
     * Sorts following list by name in ascending order.
     * @param followingList List of following Sellers of Buyer.
     * @return followingList Sorted following List.
     */
    List<SellerDto> orderFollowingListByNameAsc(List<SellerDto> followingList);

    /**
     * Unfollow a Seller. Returns true if unfollow was succeeded or false if unsucceeded.
     * @param userId ID of Buyer
     * @param unfollowUserId ID of Seller to unfollow
     * @return
     */
    boolean unfollow(Integer userId, Integer unfollowUserId);

    /**
     * Add a Seller to user Following List.
     * @param seller Seller to be followed.
     * @param userId ID of Buyer which will follow someone.
     */
    void addFollow(Seller seller, Integer userId);

    /**
     * Finds a Buyer by searching for ID.
     * @param id Buyer ID
     * @return user Buyer found by ID
     */
    Buyer findById(Integer id);

    /**
     * Add a Buyer to repository.
     * @param username Username of buyer account.
     */
    void addBuyer(String username);

    /**
     * Get all buyers in repository.
     * @return buyerList All buyers stored in repository.
     */
    List<BuyerDto> getBuyers();

    /**
     * Get current max ID for creating accounts based on User Repository.
     * @return maxId ID for last account created.
     */
    Integer getMaxId();

}
