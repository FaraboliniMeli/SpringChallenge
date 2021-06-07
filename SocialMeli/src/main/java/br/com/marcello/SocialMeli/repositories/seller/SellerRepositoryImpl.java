package br.com.marcello.SocialMeli.repositories.seller;

import br.com.marcello.SocialMeli.dtos.sellers.SellerDto;
import br.com.marcello.SocialMeli.dtos.user.UserDto;
import br.com.marcello.SocialMeli.model.Post;
import br.com.marcello.SocialMeli.model.Seller;
import br.com.marcello.SocialMeli.model.User;
import br.com.marcello.SocialMeli.repositories.users.UserRepository;
import br.com.marcello.SocialMeli.utils.seller.SellerUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class SellerRepositoryImpl implements SellerRepository {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SellerUtils sellerUtils;

    private final String jsonPath = "./src/main/java/br/com/marcello/SocialMeli/json/sellers.json";

    @Override
    public List<Post> listPromoPost(Integer sellerId) {
        List<Seller> sellerList = this.initJsonRepo();

        return sellerList.stream()
                .filter(seller -> seller.getUserId().equals(sellerId))
                .findFirst()
                .orElse(null)
                .getPostList()
                .stream()
                .filter(post -> post.getHasPromo())
                .collect(Collectors.toList());
    }

    @Override
    public Integer countPromoPostList(Integer sellerId) {
        List<Seller> sellerList = this.initJsonRepo();

        return sellerList.stream()
                .filter(seller -> seller.getUserId().equals(sellerId))
                .findFirst()
                .orElse(null)
                .getPostList()
                .stream()
                .filter(post -> post.getHasPromo())
                .collect(Collectors.toList())
                .size();
    }

    @Override
    public List<Post> orderPostListByDateAsc(List<Post> postList) {
        return this.sortByAscPostDate(postList);
    }

    @Override
    public List<Post> orderPostListByDateDesc(List<Post> postList) {
        return this.sortByDescPostDate(postList);
    }

    @Override
    public List<SellerDto> orderFollowingListByNameDesc(List<SellerDto> followingList) {
        return followingList.stream()
                .sorted(Comparator.comparing(SellerDto::getUsername).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<SellerDto> orderFollowingListByNameAsc(List<SellerDto> followingList) {
        return followingList.stream()
                .sorted(Comparator.comparing(SellerDto::getUsername))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> orderFollowerByNameDesc(List<UserDto> followerList) {
        return followerList.stream()
                .sorted(Comparator.comparing(UserDto::getUsername).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> orderFollowerByNameAsc(List<UserDto> followerList) {
        return followerList.stream()
                .sorted(Comparator.comparing(UserDto::getUsername))
                .collect(Collectors.toList());
    }

    @Override
    public boolean unfollow(Integer userId, Integer unfollowUserId) {
        List<Seller> sellerList = this.initJsonRepo();

        boolean removed =  sellerList.stream()
                .filter(seller -> seller.getUserId().equals(userId))
                .findFirst()
                .orElse(null)
                .getFollowingList()
                .removeIf(sellerDto -> sellerDto.getUserId().equals(unfollowUserId));

        this.writeOnJsonFile(sellerList);
        return removed;
    }

    @Override
    public List<Post> getTwoWeeksPosts(List<SellerDto> sellerDtoList) {
        List<Seller> followedSellers = new ArrayList<>();
        LocalDate twoWeeks = LocalDate.now().minusWeeks(2);
        List<Post> twoWeeksPostList = new ArrayList<>();

        for(SellerDto sellerDto : sellerDtoList) {
            Seller seller = this.findById(sellerDto.getUserId());
            followedSellers.add(seller);
        }

        for(Seller seller : followedSellers) {
            seller.getPostList().stream()
                    .filter(post -> post.getDate().isAfter(twoWeeks))
                    .forEach(
                            twoWeeksPostList::add
                    );
        }

        return this.sortByDescPostDate(twoWeeksPostList);
    }

    @Override
    public void makeNewPost(Post post, Integer sellerId) {
        List<Seller> sellerList = this.initJsonRepo();

        sellerList.stream()
                .filter(seller -> seller.getUserId().equals(sellerId))
                .findFirst()
                .orElse(null)
                .getPostList()
                .add(post);

        this.writeOnJsonFile(sellerList);
    }

    @Override
    public void addFollow(Seller seller, Integer userId) {
        List<Seller> sellerList = this.initJsonRepo();

        sellerList.stream()
                .filter(s -> s.getUserId().equals(userId))
                .findFirst()
                .orElse(null)
                .getFollowingList()
                .add(this.sellerUtils.convertEntityToDto(seller));

        this.writeOnJsonFile(sellerList);
    }

    @Override
    public Integer getFollowersCount(Integer sellerId) {
        List<Seller> sellerList = this.initJsonRepo();

       return sellerList.stream()
                .filter(seller -> seller.getUserId().equals(sellerId))
                .findFirst()
                .orElse(null)
                .getFollowerList()
                .size();
    }

    @Override
    public void addFollower(User user, Integer sellerId) {
        List<Seller> sellerList = this.initJsonRepo();

        sellerList.stream()
                .filter(seller -> seller.getUserId().equals(sellerId))
                .findFirst()
                .orElse(null)
                .getFollowerList()
                .add(user);

        this.writeOnJsonFile(sellerList);
    }

    @Override
    public Seller findById(Integer id) {
        List<Seller> sellerList = this.initJsonRepo();

        return sellerList.stream()
                .filter(user -> user.getUserId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void addSeller(String username) {
        List<Seller> sellerList = this.initJsonRepo();

        Seller seller = new Seller();
        seller.setUserId(this.getMaxId());
        seller.setUsername(username);
        seller.setFollowerList(new ArrayList<>());
        seller.setFollowingList(new ArrayList<>());
        seller.setPostList(new ArrayList<>());

        sellerList.add(seller);
        this.writeOnJsonFile(sellerList);
    }

    @Override
    public Integer getMaxId() {
        return this.userRepository.getMaxId();
    }

    @Override
    public List<SellerDto> getSellers() {
        List<Seller> sellerList = this.initJsonRepo();
        List<SellerDto> sellerDtoList = new ArrayList<>();

        for(Seller seller : sellerList) {
            SellerDto sellerDto = new SellerDto();
            sellerDto.setUserId(seller.getUserId());
            sellerDto.setUsername(seller.getUsername());
            sellerDtoList.add(sellerDto);
        }

        return sellerDtoList;
    }

    private List<Post> sortByDescPostDate(List<Post> postList) {
        return postList.stream()
                .sorted(Comparator.comparing(Post::getDate).reversed())
                .collect(Collectors.toList());
    }

    private List<Post> sortByAscPostDate(List<Post> postList) {
        return postList.stream()
                .sorted(Comparator.comparing(Post::getDate))
                .collect(Collectors.toList());
    }

    private List<Seller> initJsonRepo() {

        File jsonFile = null;

        jsonFile = new File(this.jsonPath);

        if(jsonFile.length() == 0)
            return new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        TypeReference<List<Seller>> typeReference = new TypeReference<List<Seller>>() {};
        List<Seller> sellerList = null;

        try {
            sellerList = mapper.readValue(jsonFile, typeReference);
        } catch (IOException e ) {
            throw new RuntimeException("Impossible to convert JSON file", e);
        }

        return sellerList;
    }

    private void writeOnJsonFile(List<Seller> sellerList) {

        JSONArray sellersArray = new JSONArray(sellerList);

        try(FileWriter writer = new FileWriter(this.jsonPath)) {
            writer.write(sellersArray.toString());
            writer.flush();
        } catch(IOException e) {
            throw new RuntimeException("Impossible to write on JSON file", e);
        }

    }

}
