package br.com.marcello.SocialMeli.controllers.socialMeli.Follow;

import br.com.marcello.SocialMeli.dtos.buyers.GetFollowingListResponse;
import br.com.marcello.SocialMeli.dtos.errors.FollowBadRequest;
import br.com.marcello.SocialMeli.dtos.errors.UserNotFoundResponse;
import br.com.marcello.SocialMeli.dtos.responses.SuccessFollow;
import br.com.marcello.SocialMeli.dtos.sellers.GetFollowersCountResponse;
import br.com.marcello.SocialMeli.dtos.sellers.GetFollowersListResponse;
import br.com.marcello.SocialMeli.model.Buyer;
import br.com.marcello.SocialMeli.model.Seller;
import br.com.marcello.SocialMeli.model.User;
import br.com.marcello.SocialMeli.model.UserType;
import br.com.marcello.SocialMeli.repositories.buyer.BuyerRepository;
import br.com.marcello.SocialMeli.repositories.seller.SellerRepository;
import br.com.marcello.SocialMeli.repositories.users.UserRepository;
import br.com.marcello.SocialMeli.utils.buyer.BuyerUtils;
import br.com.marcello.SocialMeli.utils.seller.SellerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class FollowController {

    @Autowired
    private BuyerUtils buyerUtilsImpl;

    @Autowired
    private SellerUtils sellerUtilsImpl;

    @Autowired
    private UserRepository userRepositoryImpl;

    @Autowired
    private BuyerRepository buyerRepositoryImpl;

    @Autowired
    private SellerRepository sellerRepositoryImpl;

    @PostMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<?> followUser(@PathVariable String userId, @PathVariable String userIdToFollow) {

        Integer integerUserId = Integer.parseInt(userId);
        Integer integerUserIdToFollow = Integer.parseInt(userIdToFollow);

        User userToFollow = this.userRepositoryImpl.findById(integerUserIdToFollow);
        User user = this.userRepositoryImpl.findById(integerUserId);

        if(userToFollow == null || user == null)
            return new ResponseEntity<>(new UserNotFoundResponse(), HttpStatus.NOT_FOUND);

        if(userToFollow.getUserType().equals(UserType.BUYER) || user.getUserType().equals(UserType.SELLER))
            return new ResponseEntity<>(new FollowBadRequest(), HttpStatus.BAD_REQUEST);

        Buyer buyer = this.buyerRepositoryImpl.findById(integerUserId);
        Seller seller = this.sellerRepositoryImpl.findById(integerUserIdToFollow);
        this.buyerRepositoryImpl.addFollow(seller, integerUserId);
        this.sellerRepositoryImpl.addFollower(buyer, integerUserIdToFollow);

        return new ResponseEntity<>(new SuccessFollow(), HttpStatus.OK);

    }

    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<?> getFollowersCount(@PathVariable String userId) {
        Integer integerUserId = Integer.parseInt(userId);
        GetFollowersCountResponse followersCount = new GetFollowersCountResponse();
        Seller seller = this.sellerRepositoryImpl.findById(integerUserId);

        if(seller == null)
            return new ResponseEntity<>(new UserNotFoundResponse(), HttpStatus.NOT_FOUND);

        followersCount.setUserId(integerUserId);
        followersCount.setFollowersCount(this.sellerRepositoryImpl.getFollowersCount(integerUserId));
        followersCount.setUsername(seller.getUsername());

        return new ResponseEntity<>(followersCount, HttpStatus.OK);
    }

    @GetMapping("/{userId}/followers/list")
    public ResponseEntity<?> getFollowersList(@PathVariable String userId) {
        Integer integerUserId = Integer.parseInt(userId);
        GetFollowersListResponse followersList = new GetFollowersListResponse();
        Seller seller = this.sellerRepositoryImpl.findById(integerUserId);

        if(seller == null)
            return new ResponseEntity<>(new UserNotFoundResponse(), HttpStatus.NOT_FOUND);

        followersList.setUserId(integerUserId);
        followersList.setUsername(seller.getUsername());
        followersList.setFollowers(this.buyerUtilsImpl.convertEntityToDto(seller.getFollowerList()));

        return new ResponseEntity<>(followersList, HttpStatus.OK);
    }

    @GetMapping("{userId}/followed/list")
    public ResponseEntity<?> getFollowingList(@PathVariable String userId) {
        Integer integerUserId =  Integer.parseInt(userId);
        GetFollowingListResponse followingList = new GetFollowingListResponse();
        Buyer buyer = this.buyerRepositoryImpl.findById(integerUserId);

        if(buyer == null)
            return new ResponseEntity<>(new UserNotFoundResponse(), HttpStatus.NOT_FOUND);

        followingList.setUserId(integerUserId);
        followingList.setUsername(buyer.getUsername());
        followingList.setFollowed(this.sellerUtilsImpl.convertEntityToDto(buyer.getFollowingList()));

        return new ResponseEntity<>(followingList, HttpStatus.OK);
    }

}
