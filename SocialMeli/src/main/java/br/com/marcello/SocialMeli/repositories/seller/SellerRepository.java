package br.com.marcello.SocialMeli.repositories.seller;

import br.com.marcello.SocialMeli.dtos.sellers.SellerDto;
import br.com.marcello.SocialMeli.dtos.user.UserDto;
import br.com.marcello.SocialMeli.model.Post;
import br.com.marcello.SocialMeli.model.Seller;
import br.com.marcello.SocialMeli.model.User;

import java.util.List;

public interface SellerRepository {

    List<Post> listPromoPost(Integer sellerId);

    Integer countPromoPostList(Integer sellerId);

    List<Post> orderPostListByDateAsc(List<Post> postList);

    List<Post> orderPostListByDateDesc(List<Post> postList);

    List<SellerDto> orderFollowingListByNameDesc(List<SellerDto> followingList);

    List<SellerDto> orderFollowingListByNameAsc(List<SellerDto> followingList);

    List<UserDto> orderFollowerByNameDesc(List<UserDto> followerList);

    List<UserDto> orderFollowerByNameAsc(List<UserDto> followerList);

    boolean unfollow(Integer userId, Integer unfollowUserId);

    List<Post> getTwoWeeksPosts(List<SellerDto> sellerList);

    void makeNewPost(Post post, Integer sellerId);

    void addFollow(Seller seller, Integer userId);

    Integer getFollowersCount(Integer sellerId);

    void addFollower(User user, Integer sellerId);

    Seller findById(Integer id);

    void addSeller(String username);

    List<SellerDto> getSellers();

    Integer getMaxId();

}
