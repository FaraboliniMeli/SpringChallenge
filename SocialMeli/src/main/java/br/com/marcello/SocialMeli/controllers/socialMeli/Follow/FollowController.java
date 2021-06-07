package br.com.marcello.SocialMeli.controllers.socialMeli.Follow;

import br.com.marcello.SocialMeli.dtos.errors.*;
import br.com.marcello.SocialMeli.dtos.responses.*;
import br.com.marcello.SocialMeli.dtos.sellers.GetFollowersCountResponse;
import br.com.marcello.SocialMeli.dtos.sellers.GetFollowersListResponse;
import br.com.marcello.SocialMeli.dtos.sellers.SellerDto;
import br.com.marcello.SocialMeli.dtos.user.GetFollowingListResponse;
import br.com.marcello.SocialMeli.dtos.user.UserDto;
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

import java.util.List;

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

        if(!this.userExists(integerUserId) || !this.userExists(integerUserIdToFollow))
            return new ResponseEntity<>(new UserNotFoundResponse(), HttpStatus.NOT_FOUND);

        if(this.isBuyer(integerUserIdToFollow))
            return new ResponseEntity<>(new FollowBadRequest(), HttpStatus.BAD_REQUEST);

        User user = this.userRepositoryImpl.findById(integerUserId);
        Seller seller = this.sellerRepositoryImpl.findById(integerUserIdToFollow);

        if(this.isBuyer(integerUserId)) {
            if(this.buyerRepositoryImpl.alreadyFollows(integerUserId, integerUserIdToFollow))
                return new ResponseEntity<>(new AlreadyFollowResponse(), HttpStatus.OK);
            this.buyerRepositoryImpl.addFollow(seller, integerUserId);
            this.sellerRepositoryImpl.addFollower(user, integerUserIdToFollow);
            return new ResponseEntity<>(new SuccessFollow(), HttpStatus.OK);
        }

        if(this.sellerRepositoryImpl.alreadyFollows(integerUserId, integerUserIdToFollow))
            return new ResponseEntity<>(new AlreadyFollowResponse(), HttpStatus.OK);
        this.sellerRepositoryImpl.addFollow(seller, integerUserId);
        this.sellerRepositoryImpl.addFollower(user, integerUserIdToFollow);
        return new ResponseEntity<>(new SuccessFollow(), HttpStatus.OK);
    }

    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<?> getFollowersCount(@PathVariable String userId) {
        Integer integerUserId = Integer.parseInt(userId);
        if(!this.userExists(integerUserId))
            return new ResponseEntity<>(new UserNotFoundResponse(), HttpStatus.NOT_FOUND);

        if(this.isBuyer(integerUserId))
            return new ResponseEntity<>(new GetFollowersBadRequest(), HttpStatus.BAD_REQUEST);

        GetFollowersCountResponse followersCount = new GetFollowersCountResponse();
        Seller seller = this.sellerRepositoryImpl.findById(integerUserId);
        Integer followerCount = this.sellerRepositoryImpl.getFollowersCount(integerUserId);

        if(followerCount == 0)
            return new ResponseEntity<>(new NoFollowersResponse(), HttpStatus.OK);

        followersCount.setUserId(integerUserId);
        followersCount.setFollowersCount(followerCount);
        followersCount.setUsername(seller.getUsername());

        return new ResponseEntity<>(followersCount, HttpStatus.OK);
    }

    @GetMapping("/{userId}/followers/list")
    public ResponseEntity<?> getFollowersList(@PathVariable String userId) {
        Integer integerUserId = Integer.parseInt(userId);
        GetFollowersListResponse followersList = new GetFollowersListResponse();

        if(!this.userExists(integerUserId))
            return new ResponseEntity<>(new UserNotFoundResponse(), HttpStatus.NOT_FOUND);

        if(this.isBuyer(integerUserId))
            return new ResponseEntity<>(new GetFollowersBadRequest(), HttpStatus.BAD_REQUEST);

        Seller seller = this.sellerRepositoryImpl.findById(integerUserId);

        if(seller.getFollowerList().size() == 0)
            return new ResponseEntity<>(new NoFollowersResponse(), HttpStatus.OK);

        followersList.setUserId(integerUserId);
        followersList.setUsername(seller.getUsername());
        followersList.setFollowers(this.userUtilsImpl.convertEntityToDto(seller.getFollowerList()));

        return new ResponseEntity<>(followersList, HttpStatus.OK);
    }

    @GetMapping("{userId}/followed/list")
    public ResponseEntity<?> getFollowingList(@PathVariable String userId) {
        Integer integerUserId =  Integer.parseInt(userId);
        GetFollowingListResponse followingList = new GetFollowingListResponse();

        if(!this.userExists(integerUserId))
            return new ResponseEntity<>(new UserNotFoundResponse(), HttpStatus.NOT_FOUND);

        if(this.isBuyer(integerUserId)) {
            Buyer buyer = this.buyerRepositoryImpl.findById(integerUserId);
            followingList.setUserId(integerUserId);
            followingList.setUsername(buyer.getUsername());

            if(buyer.getFollowingList().size() == 0)
                return new ResponseEntity<>(new NoFollowsResponse(), HttpStatus.OK);

            followingList.setFollowed(buyer.getFollowingList());
            return new ResponseEntity<>(followingList, HttpStatus.OK);
        }

        Seller seller = this.sellerRepositoryImpl.findById(integerUserId);
        followingList.setUserId(integerUserId);
        followingList.setUsername(seller.getUsername());

        if(seller.getFollowingList().size() == 0)
            return new ResponseEntity<>(new NoFollowsResponse(), HttpStatus.OK);

        followingList.setFollowed(seller.getFollowingList());
        return new ResponseEntity<>(followingList, HttpStatus.OK);
    }

    @PostMapping("/{userId}/unfollow/{userIdToUnfollow}")
    public ResponseEntity<?> unfollow(@PathVariable String userId, @PathVariable String userIdToUnfollow) {
        Integer integerUserId = Integer.parseInt(userId);
        Integer integerUserIdToUnfollow = Integer.parseInt(userIdToUnfollow);

        if(!this.userExists(integerUserId) || !this.userExists(integerUserIdToUnfollow))
            return new ResponseEntity<>(new UserNotFoundResponse(), HttpStatus.NOT_FOUND);

        if(this.isBuyer(integerUserIdToUnfollow))
            return new ResponseEntity<>(new UnfollowBadRequest(), HttpStatus.BAD_REQUEST);

        if(this.isBuyer(integerUserId)) {
            if(this.buyerRepositoryImpl.unfollow(integerUserId, integerUserIdToUnfollow)) {
                this.sellerRepositoryImpl.removeFollow(integerUserId, integerUserIdToUnfollow);
                return new ResponseEntity<>(new SuccessUnfollow(), HttpStatus.OK);
            }
            return new ResponseEntity<>(new NotFollowingResponse(), HttpStatus.NOT_FOUND);
        }

        if(this.sellerRepositoryImpl.unfollow(integerUserId, integerUserIdToUnfollow)) {
            this.sellerRepositoryImpl.removeFollow(integerUserId, integerUserIdToUnfollow);
            return new ResponseEntity<>(new SuccessUnfollow(), HttpStatus.OK);
        }

        return new ResponseEntity<>(new NotFollowingResponse(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{userId}/followers/orderedList")
    public ResponseEntity<?> getFollowersList(@PathVariable String userId, @RequestParam(value = "order") String order) {
        Integer integerUserId = Integer.parseInt(userId);
        GetFollowersListResponse followersListResponse = new GetFollowersListResponse();

        if(!this.userExists(integerUserId))
            return new ResponseEntity<>(new UserNotFoundResponse(), HttpStatus.NOT_FOUND);

        if(this.isBuyer(integerUserId))
            return new ResponseEntity<>(new GetFollowersBadRequest(), HttpStatus.BAD_REQUEST);

        Seller seller = this.sellerRepositoryImpl.findById(integerUserId);
        List<UserDto> followersList = this.userUtilsImpl.convertEntityToDto(seller.getFollowerList());

        if(followersList.size() == 0)
            return new ResponseEntity<>(new NoFollowersResponse(), HttpStatus.NOT_FOUND);

        followersList = this.getFollowersOrdereredList(order, followersList);

        if(followersList == null)
            return new ResponseEntity<>(new InvalidOrder(), HttpStatus.BAD_REQUEST);

        followersListResponse.setUserId(integerUserId);
        followersListResponse.setUsername(seller.getUsername());
        followersListResponse.setFollowers(followersList);

        return new ResponseEntity<>(followersList, HttpStatus.OK);
    }

    @GetMapping("{userId}/followed/orderedList")
    public ResponseEntity<?> getFollowingList(@PathVariable String userId, @RequestParam(value = "order") String order) {
        Integer integerUserId =  Integer.parseInt(userId);
        GetFollowingListResponse followingListResponse = new GetFollowingListResponse();

        if(!this.userExists(integerUserId))
            return new ResponseEntity<>(new UserNotFoundResponse(), HttpStatus.NOT_FOUND);


        if(this.isBuyer(integerUserId)) {
            Buyer buyer = this.buyerRepositoryImpl.findById(integerUserId);
            followingListResponse.setUserId(integerUserId);
            followingListResponse.setUsername(buyer.getUsername());
            List<SellerDto> followingList = buyer.getFollowingList();

            if(followingList.size() == 0)
                return new ResponseEntity<>(new NoFollowsResponse(), HttpStatus.NOT_FOUND);

            followingList = this.getBuyerOrderedFollows(order, followingList);

            if(followingList == null) {
                return new ResponseEntity<>(new InvalidOrder(), HttpStatus.BAD_REQUEST);
            }

            followingListResponse.setFollowed(followingList);
            return new ResponseEntity<>(followingListResponse, HttpStatus.OK);
        }

        Seller seller = this.sellerRepositoryImpl.findById(integerUserId);
        followingListResponse.setUserId(integerUserId);
        followingListResponse.setUsername(seller.getUsername());
        List<SellerDto> followingList = seller.getFollowingList();

        if(followingList.size() == 0)
            return new ResponseEntity<>(new NoFollowsResponse(), HttpStatus.NOT_FOUND);

        followingList = this.getSellerOrderedFollows(order, followingList);

       if(followingList == null)
           return new ResponseEntity<>(new InvalidOrder(), HttpStatus.BAD_REQUEST);

       followingListResponse.setFollowed(followingList);
        return new ResponseEntity<>(followingListResponse, HttpStatus.OK);
    }

    private Boolean userExists(Integer userId) {
        return this.userRepositoryImpl.findById(userId) != null;
    }

    private Boolean isBuyer(Integer userId) {
        return this.userRepositoryImpl.findById(userId).getUserType().equals(UserType.BUYER);
    }

    private List<UserDto> getFollowersOrdereredList(String order, List<UserDto> followersList) {
        switch (order.toLowerCase()) {
            case "name_asc":
                return this.sellerRepositoryImpl.orderFollowerByNameAsc(followersList);
            case "name_desc":
                return this.sellerRepositoryImpl.orderFollowerByNameDesc(followersList);
            default:
                return null;
        }
    }

    private List<SellerDto> getBuyerOrderedFollows(String order, List<SellerDto> followsList) {
        switch (order.toLowerCase()) {
            case "name_asc":
                return this.buyerRepositoryImpl.orderFollowingListByNameAsc(followsList);
            case "name_desc":
                return this.buyerRepositoryImpl.orderFollowingListByNameDesc(followsList);
            default:
                return null;
        }
    }

    private List<SellerDto> getSellerOrderedFollows(String order, List<SellerDto> followsList) {
        switch (order.toLowerCase()) {
            case "name_asc":
                return this.sellerRepositoryImpl.orderFollowingListByNameAsc(followsList);
            case "name_desc":
                return this.sellerRepositoryImpl.orderFollowingListByNameDesc(followsList);
            default:
                return null;
        }
    }

}


