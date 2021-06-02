package br.com.marcello.SocialMeli.controllers.socialMeli.Follow;

import br.com.marcello.SocialMeli.dtos.errors.FollowBadRequest;
import br.com.marcello.SocialMeli.dtos.errors.InvalidOrder;
import br.com.marcello.SocialMeli.dtos.errors.UnfollowBadRequest;
import br.com.marcello.SocialMeli.dtos.errors.UserNotFoundResponse;
import br.com.marcello.SocialMeli.dtos.responses.NotFollowingResponse;
import br.com.marcello.SocialMeli.dtos.responses.SuccessFollow;
import br.com.marcello.SocialMeli.dtos.responses.SuccessUnfollow;
import br.com.marcello.SocialMeli.dtos.sellers.GetFollowersCountResponse;
import br.com.marcello.SocialMeli.dtos.sellers.GetFollowersListResponse;
import br.com.marcello.SocialMeli.dtos.user.GetFollowingListResponse;
import br.com.marcello.SocialMeli.model.Buyer;
import br.com.marcello.SocialMeli.model.Seller;
import br.com.marcello.SocialMeli.model.User;
import br.com.marcello.SocialMeli.model.UserType;
import br.com.marcello.SocialMeli.repositories.buyer.BuyerRepository;
import br.com.marcello.SocialMeli.repositories.seller.SellerRepository;
import br.com.marcello.SocialMeli.repositories.users.UserRepository;
import br.com.marcello.SocialMeli.utils.buyer.BuyerUtils;
import br.com.marcello.SocialMeli.utils.seller.SellerUtils;
import br.com.marcello.SocialMeli.utils.user.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class FollowController {

    @Autowired
    private UserUtils userUtilsImpl;

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

        if(userToFollow.getUserType().equals(UserType.BUYER))
            return new ResponseEntity<>(new FollowBadRequest(), HttpStatus.BAD_REQUEST);

        Seller seller = this.sellerRepositoryImpl.findById(integerUserIdToFollow);

        if(user.getUserType().equals(UserType.BUYER)) {
            this.buyerRepositoryImpl.addFollow(seller, integerUserId);
            this.sellerRepositoryImpl.addFollower(user, integerUserIdToFollow);
            return new ResponseEntity<>(new SuccessFollow(), HttpStatus.OK);
        }

        this.sellerRepositoryImpl.addFollow(seller, integerUserId);
        this.sellerRepositoryImpl.addFollower(user, integerUserIdToFollow);
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
        followersList.setFollowers(this.userUtilsImpl.convertEntityToDto(seller.getFollowerList()));

        return new ResponseEntity<>(followersList, HttpStatus.OK);
    }

    @GetMapping("{userId}/followed/list")
    public ResponseEntity<?> getFollowingList(@PathVariable String userId) {
        Integer integerUserId =  Integer.parseInt(userId);
        User user = this.userRepositoryImpl.findById(integerUserId);
        GetFollowingListResponse followingList = new GetFollowingListResponse();

        if(user == null)
            return new ResponseEntity<>(new UserNotFoundResponse(), HttpStatus.NOT_FOUND);

        if(user.getUserType().equals(UserType.BUYER)) {
            Buyer buyer = this.buyerRepositoryImpl.findById(integerUserId);
            followingList.setUserId(integerUserId);
            followingList.setUsername(buyer.getUsername());
            followingList.setFollowed(buyer.getFollowingList());
            return new ResponseEntity<>(followingList, HttpStatus.OK);
        }

        Seller seller = this.sellerRepositoryImpl.findById(integerUserId);
        followingList.setUserId(integerUserId);
        followingList.setUsername(seller.getUsername());
        followingList.setFollowed(seller.getFollowingList());
        return new ResponseEntity<>(followingList, HttpStatus.OK);
    }

    @PostMapping("/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<?> unfollow(@PathVariable String userId, @PathVariable String userIdToUnfollow) {
        Integer integerUserId = Integer.parseInt(userId);
        Integer integerUserIdToUnfollow = Integer.parseInt(userIdToUnfollow);
        User user = this.userRepositoryImpl.findById(integerUserId);
        User userToUnfollow = this.userRepositoryImpl.findById(integerUserIdToUnfollow);

        if(user == null || userToUnfollow == null)
            return new ResponseEntity<>(new UserNotFoundResponse(), HttpStatus.NOT_FOUND);

        if(userToUnfollow.getUserType().equals(UserType.BUYER))
            return new ResponseEntity<>(new UnfollowBadRequest(), HttpStatus.BAD_REQUEST);

        if(user.getUserType().equals(UserType.BUYER)) {
            if(this.buyerRepositoryImpl.unfollow(integerUserId, integerUserIdToUnfollow))
                return new ResponseEntity<>(new SuccessUnfollow(), HttpStatus.OK);
        }

        if(this.sellerRepositoryImpl.unfollow(integerUserId, integerUserIdToUnfollow))
            return new ResponseEntity<>(new SuccessUnfollow(), HttpStatus.OK);

        return new ResponseEntity<>(new NotFollowingResponse(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{userId}/followers/orderedList")
    public ResponseEntity<?> getFollowersList(@PathVariable String userId, @RequestParam(value = "order") String order) {
        Integer integerUserId = Integer.parseInt(userId);
        GetFollowersListResponse followersList = new GetFollowersListResponse();
        Seller seller = this.sellerRepositoryImpl.findById(integerUserId);

        if(seller == null)
            return new ResponseEntity<>(new UserNotFoundResponse(), HttpStatus.NOT_FOUND);

        followersList.setUserId(integerUserId);
        followersList.setUsername(seller.getUsername());
        if(order.equalsIgnoreCase("name_asc")) {
            followersList.setFollowers(
                    this.sellerRepositoryImpl.orderFollowerByNameAsc(this.userUtilsImpl.convertEntityToDto(seller.getFollowerList()))
            );
        } else if(order.equalsIgnoreCase("name_desc")) {
            followersList.setFollowers(
                    this.sellerRepositoryImpl.orderFollowerByNameDesc(this.userUtilsImpl.convertEntityToDto(seller.getFollowerList()))
            );
        } else {
            return new ResponseEntity<>(new InvalidOrder(), HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(followersList, HttpStatus.OK);
    }

    @GetMapping("{userId}/followed/orderedList")
    public ResponseEntity<?> getFollowingList(@PathVariable String userId, @RequestParam(value = "order") String order) {
        Integer integerUserId =  Integer.parseInt(userId);
        User user = this.userRepositoryImpl.findById(integerUserId);
        GetFollowingListResponse followingList = new GetFollowingListResponse();

        if(user == null)
            return new ResponseEntity<>(new UserNotFoundResponse(), HttpStatus.NOT_FOUND);

        if(user.getUserType().equals(UserType.BUYER)) {
            Buyer buyer = this.buyerRepositoryImpl.findById(integerUserId);
            followingList.setUserId(integerUserId);
            followingList.setUsername(buyer.getUsername());

            if(order.equalsIgnoreCase("name_asc")) {
                followingList.setFollowed(
                        this.buyerRepositoryImpl.orderFollowingListByNameAsc(buyer.getFollowingList())
                );
            } else if(order.equalsIgnoreCase("name_desc")) {
                followingList.setFollowed(
                        this.buyerRepositoryImpl.orderFollowingListByNameDesc(buyer.getFollowingList())
                );
            } else {
                return new ResponseEntity<>(new InvalidOrder(), HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(followingList, HttpStatus.OK);
        }

        Seller seller = this.sellerRepositoryImpl.findById(integerUserId);
        followingList.setUserId(integerUserId);
        followingList.setUsername(seller.getUsername());

        if(order.equalsIgnoreCase("name_asc")) {
            followingList.setFollowed(
                    this.sellerRepositoryImpl.orderFollowingListByNameAsc(seller.getFollowingList())
            );
        } else if(order.equalsIgnoreCase("name_desc")) {
            followingList.setFollowed(
                    this.sellerRepositoryImpl.orderFollowingListByNameDesc(seller.getFollowingList())
            );
        } else {
            return new ResponseEntity<>(new InvalidOrder(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(followingList, HttpStatus.OK);
    }

}


