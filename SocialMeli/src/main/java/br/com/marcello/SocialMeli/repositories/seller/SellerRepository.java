package br.com.marcello.SocialMeli.repositories.seller;

import br.com.marcello.SocialMeli.dtos.sellers.SellerDto;
import br.com.marcello.SocialMeli.dtos.user.UserDto;
import br.com.marcello.SocialMeli.model.Post;
import br.com.marcello.SocialMeli.model.Seller;
import br.com.marcello.SocialMeli.model.User;

import java.util.List;

public interface SellerRepository {

    /**
     * Checks if user already follows the seller.
     * @param userId User ID which will follow seller.
     * @param userIdToFollow Seller ID which will be followed.
     * @return alreadyFollow True if user already follows the seller. On the contrary, false.
     */
    Boolean alreadyFollows(Integer userId, Integer userIdToFollow);

    /**
     * Get all Promo Posts for a specific Seller.
     * @param sellerId Seller ID.
     * @return promoPostList
     */
    List<Post> listPromoPost(Integer sellerId);

    /**
     * Get current promo posts quantity of a specific Seller.
     * @param sellerId Seller ID.
     * @return promoCount Current promo posts quantity.
     */
    Integer countPromoPostList(Integer sellerId);

    /**
     * Sorts post list of a specific seller by ascending date.
     * @param postList Seller posts.
     * @return postList Sorted seller posts.
     */
    List<Post> orderPostListByDateAsc(List<Post> postList);

    /**
     * Sorts post list of a specific seller by descending date.
     * @param postList Seller posts.
     * @return postList Sorted seller posts.
     */
    List<Post> orderPostListByDateDesc(List<Post> postList);

    /**
     * Sorts Seller following List by descending name.
     * @param followingList Current seller following list.
     * @return followingList Sorted following list.
     */
    List<SellerDto> orderFollowingListByNameDesc(List<SellerDto> followingList);

    /**
     * Sorts Seller following list by ascending name.
     * @param followingList Current following list.
     * @return followingList Sorted following list.
     */
    List<SellerDto> orderFollowingListByNameAsc(List<SellerDto> followingList);

    /**
     * Sorts Seller followers by descending name.
     * @param followerList Current followers list.
     * @return followersList Sorted followers list.
     */
    List<UserDto> orderFollowerByNameDesc(List<UserDto> followerList);

    /**
     * Sorts Seller followers by ascending name.
     * @param followerList Current followers list.
     * @return followersList Sorted followers list.
     */
    List<UserDto> orderFollowerByNameAsc(List<UserDto> followerList);

    /**
     * Remove User from seller followers list.
     * @param userId User ID to remove from followers list.
     * @param sellerId Seller ID to remove User from follwers list.
     * @return void
     */
    void removeFollow(Integer userId, Integer sellerId);

    /**
     * Unfollow a specific seller. Returns true if unfollow was succeeded or false if unsucceeded.
     * @param userId Seller which will unfollow someone.
     * @param unfollowUserId Unfollowed Seller
     * @return
     */
    boolean unfollow(Integer userId, Integer unfollowUserId);

    /**
     * Get products posted in last two weeks for specific sellers.
     * @param sellerList Seller list followed by user.
     * @return postList List of products posted in last two weeks.
     */
    List<Post> getTwoWeeksPosts(List<SellerDto> sellerList);

    /**
     * Add a new product post to specific seller.
     * @param post Product to be posted.
     * @param sellerId Seller ID which will make the post.
     */
    void makeNewPost(Post post, Integer sellerId);

    /**
     * Add a Seller to following list.
     * @param seller Seller to be followed.
     * @param userId User ID which will follow the seller.
     */
    void addFollow(Seller seller, Integer userId);

    /**
     * Get followers count.
     * @param sellerId User ID to count followers.
     * @return followerCount Followers number.
     */
    Integer getFollowersCount(Integer sellerId);

    /**
     * Add a User to seller followers list.
     * @param user User which is following seller.
     * @param sellerId Current seller.
     */
    void addFollower(User user, Integer sellerId);

    /**
     * Find a Seller by ID.
     * @param id Seller ID.
     * @return seller Seller found.
     */
    Seller findById(Integer id);

    /**
     * Add a Seller to repository
     * @param username Account username.
     */
    void addSeller(String username);

    /**
     * Get all sellers in repository
     * @return sellerList Seller list stored in repository.
     */
    List<SellerDto> getSellers();

    /**
     * Get current max ID for creating accounts based on User Repository.
     * @return maxId ID for last account created.
     */
    Integer getMaxId();

}
