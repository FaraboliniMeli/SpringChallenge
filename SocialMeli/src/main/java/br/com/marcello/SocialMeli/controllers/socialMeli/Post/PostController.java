package br.com.marcello.SocialMeli.controllers.socialMeli.Post;

import br.com.marcello.SocialMeli.dtos.errors.InvalidOrder;
import br.com.marcello.SocialMeli.dtos.errors.NoPostsLastTwoWeeksResponse;
import br.com.marcello.SocialMeli.dtos.errors.PostBadRequest;
import br.com.marcello.SocialMeli.dtos.errors.UserNotFoundResponse;
import br.com.marcello.SocialMeli.dtos.post.PostDto;
import br.com.marcello.SocialMeli.dtos.post.PromoPostDto;
import br.com.marcello.SocialMeli.dtos.responses.*;
import br.com.marcello.SocialMeli.dtos.sellers.SellerDto;
import br.com.marcello.SocialMeli.model.Post;
import br.com.marcello.SocialMeli.model.Seller;
import br.com.marcello.SocialMeli.model.User;
import br.com.marcello.SocialMeli.model.UserType;
import br.com.marcello.SocialMeli.repositories.buyer.BuyerRepository;
import br.com.marcello.SocialMeli.repositories.seller.SellerRepository;
import br.com.marcello.SocialMeli.repositories.users.UserRepository;
import br.com.marcello.SocialMeli.utils.post.PostUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class PostController {

    @Autowired
    private BuyerRepository buyerRepositoryImpl;

    @Autowired
    private SellerRepository sellerRepositoryImpl;

    @Autowired
    private UserRepository userRepositoryImpl;

    @Autowired
    private PostUtils postUtilsImpl;

    @PostMapping("/newpost")
    public ResponseEntity<?> makeNewPost(@RequestBody PostDto postDto) {
        Integer sellerId = postDto.getUserId();

        if(!this.userExists(sellerId))
            return new ResponseEntity<>(new UserNotFoundResponse(), HttpStatus.NOT_FOUND);

        if(this.isBuyer(sellerId))
            return new ResponseEntity<>(new PostBadRequest(), HttpStatus.BAD_REQUEST);

        Post post = this.postUtilsImpl.convertDtoToEntity(postDto);
        this.sellerRepositoryImpl.makeNewPost(post, sellerId);

        return new ResponseEntity<>(new SuccessPost(), HttpStatus.OK);
    }

    @GetMapping("/followed/{userId}/list")
    public ResponseEntity<?> getTwoWeeksPosts(@PathVariable String userId) {
        Integer integerUserId = Integer.parseInt(userId);

        if(!this.userExists(integerUserId))
            return new ResponseEntity<>(new UserNotFoundResponse(), HttpStatus.NOT_FOUND);

        List<SellerDto> followedSellers = null;
        if(this.isBuyer(integerUserId)) {
            followedSellers = this.buyerRepositoryImpl.findById(integerUserId)
                    .getFollowingList();
        } else {
            followedSellers = this.sellerRepositoryImpl.findById(integerUserId)
                    .getFollowingList();
        }

        List<Post> twoWeeksPostList = this.sellerRepositoryImpl.getTwoWeeksPosts(followedSellers);

        if(twoWeeksPostList.size() == 0)
            return new ResponseEntity<>(new NoPostsLastTwoWeeksResponse(), HttpStatus.NOT_FOUND);

        TwoWeeksPostsResponse postsResponse = new TwoWeeksPostsResponse();
        postsResponse.setUserId(integerUserId);
        postsResponse.setPosts(twoWeeksPostList);

        return new ResponseEntity<>(postsResponse, HttpStatus.OK);
    }

    @GetMapping("/followed/{userId}/orderedList")
    public ResponseEntity<?> getTwoWeeksPosts(@PathVariable String userId, @RequestParam(value = "order") String order) {
        Integer integerUserId = Integer.parseInt(userId);
        List<SellerDto> followedSellers = null;

        if(!this.userExists(integerUserId))
            return new ResponseEntity<>(new UserNotFoundResponse(), HttpStatus.NOT_FOUND);

        if(this.isBuyer(integerUserId)) {
            followedSellers = this.buyerRepositoryImpl.findById(integerUserId)
                    .getFollowingList();
        } else {
            followedSellers = this.sellerRepositoryImpl.findById(integerUserId)
                    .getFollowingList();
        }

        List<Post> twoWeeksPostList = this.sellerRepositoryImpl.getTwoWeeksPosts(followedSellers);

        if(twoWeeksPostList.size() == 0)
            return new ResponseEntity<>(new NoPostsLastTwoWeeksResponse(), HttpStatus.NOT_FOUND);

        if(order.equalsIgnoreCase("date_asc"))
            twoWeeksPostList = this.sellerRepositoryImpl.orderPostListByDateAsc(twoWeeksPostList);
        else if(order.equalsIgnoreCase("date_desc"))
            twoWeeksPostList = this.sellerRepositoryImpl.orderPostListByDateDesc(twoWeeksPostList);
        else
            return new ResponseEntity<>(new InvalidOrder(), HttpStatus.BAD_REQUEST);

        TwoWeeksPostsResponse postsResponse = new TwoWeeksPostsResponse();
        postsResponse.setUserId(integerUserId);
        postsResponse.setPosts(twoWeeksPostList);

        return new ResponseEntity<>(postsResponse, HttpStatus.OK);
    }

    @PostMapping("/newPromoPost")
    public ResponseEntity<?> makeNewPromoPost(@RequestBody PromoPostDto promoPostDto) {
        Integer sellerId = promoPostDto.getUserId();

        if(!this.userExists(sellerId))
            return new ResponseEntity<>(new UserNotFoundResponse(), HttpStatus.NOT_FOUND);

        if(this.isBuyer(sellerId))
            return new ResponseEntity<>(new PostBadRequest(), HttpStatus.BAD_REQUEST);

        Post post = this.postUtilsImpl.convertDtoToEntity(promoPostDto);
        this.sellerRepositoryImpl.makeNewPost(post, sellerId);

        return new ResponseEntity<>(new SuccessPost(), HttpStatus.OK);
    }

    @GetMapping("/{userId}/countPromo")
    public ResponseEntity<?> getPromoPostCount(@PathVariable String userId) {
        Integer sellerId = Integer.parseInt(userId);

        if(!this.userExists(sellerId))
            return new ResponseEntity<>(new UserNotFoundResponse(), HttpStatus.NOT_FOUND);

        if(this.isBuyer(sellerId))
            return new ResponseEntity<>(new PostBadRequest(), HttpStatus.BAD_REQUEST);

        Seller seller = this.sellerRepositoryImpl.findById(sellerId);
        Integer countPromo = this.sellerRepositoryImpl.countPromoPostList(sellerId);

        if(countPromo == 0)
            return new ResponseEntity<>(new NoPromoPostResponse(), HttpStatus.NOT_FOUND);

        CountPromoPostResponse countPromoResponse = new CountPromoPostResponse();
        countPromoResponse.setUserId(seller.getUserId());
        countPromoResponse.setUsername(seller.getUsername());
        countPromoResponse.setPromoProductsCount(countPromo);

        return new ResponseEntity<>(countPromoResponse, HttpStatus.OK);
    }

    @GetMapping("/{userId}/list")
    public ResponseEntity<?> getPromoPostList(@PathVariable String userId) {
        Integer sellerId = Integer.parseInt(userId);

        if(!this.userExists(sellerId))
            return new ResponseEntity<>(new UserNotFoundResponse(), HttpStatus.NOT_FOUND);

        if(this.isBuyer(sellerId))
            return new ResponseEntity<>(new PostBadRequest(), HttpStatus.BAD_REQUEST);

        Seller seller = this.sellerRepositoryImpl.findById(sellerId);
        List<Post> promoPostList = this.sellerRepositoryImpl.listPromoPost(sellerId);

        if(promoPostList.size() == 0)
            return new ResponseEntity<>(new NoPromoPostResponse(), HttpStatus.NOT_FOUND);

        PromoPostResponse promoPostResponse = new PromoPostResponse();
        promoPostResponse.setUserId(seller.getUserId());
        promoPostResponse.setUsername(seller.getUsername());
        promoPostResponse.setPromoPosts(promoPostList);

        return new ResponseEntity<>(promoPostResponse, HttpStatus.OK);
    }

    private Boolean userExists(Integer userId) {
        return this.userRepositoryImpl.findById(userId) != null;
    }

    private Boolean isBuyer(Integer userId) {
        return this.userRepositoryImpl.findById(userId).getUserType().equals(UserType.BUYER);
    }

}
