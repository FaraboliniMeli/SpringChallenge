package br.com.marcello.SocialMeli.controllers.socialMeli.Post;

import br.com.marcello.SocialMeli.dtos.errors.InvalidOrder;
import br.com.marcello.SocialMeli.dtos.errors.NoPostsLastTwoWeeksResponse;
import br.com.marcello.SocialMeli.dtos.errors.PostBadRequest;
import br.com.marcello.SocialMeli.dtos.errors.UserNotFoundResponse;
import br.com.marcello.SocialMeli.dtos.post.PostDto;
import br.com.marcello.SocialMeli.dtos.responses.SuccessPost;
import br.com.marcello.SocialMeli.dtos.responses.TwoWeeksPostsResponse;
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
        if(this.userRepositoryImpl.findById(postDto.getUserId()).getUserType().equals(UserType.BUYER))
            return new ResponseEntity<>(new PostBadRequest(), HttpStatus.BAD_REQUEST);

        Integer sellerId = postDto.getUserId();
        Seller seller = this.sellerRepositoryImpl.findById(sellerId);

        if(seller == null)
            return new ResponseEntity<>(new UserNotFoundResponse(), HttpStatus.NOT_FOUND);

        Post post = this.postUtilsImpl.convertDtoToEntity(postDto);
        this.sellerRepositoryImpl.makeNewPost(post, sellerId);

        return new ResponseEntity<>(new SuccessPost(), HttpStatus.OK);
    }

    @GetMapping("/followed/{userId}/list")
    public ResponseEntity<?> getTwoWeeksPosts(@PathVariable String userId) {
        Integer integerUserId = Integer.parseInt(userId);
        User user = this.userRepositoryImpl.findById(integerUserId);
        List<SellerDto> followedSellers = null;

        if(user == null)
            return new ResponseEntity<>(new UserNotFoundResponse(), HttpStatus.NOT_FOUND);

        if(user.getUserType().equals(UserType.BUYER)) {
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
        User user = this.userRepositoryImpl.findById(integerUserId);
        List<SellerDto> followedSellers = null;

        if(user == null)
            return new ResponseEntity<>(new UserNotFoundResponse(), HttpStatus.NOT_FOUND);

        if(user.getUserType().equals(UserType.BUYER)) {
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

}
