package br.com.marcello.SocialMeli.repositories.buyer;

import br.com.marcello.SocialMeli.controllers.SellerController;
import br.com.marcello.SocialMeli.dtos.buyers.BuyerDto;
import br.com.marcello.SocialMeli.dtos.sellers.SellerDto;
import br.com.marcello.SocialMeli.model.Buyer;
import br.com.marcello.SocialMeli.model.Seller;
import br.com.marcello.SocialMeli.model.User;
import br.com.marcello.SocialMeli.repositories.seller.SellerRepository;
import br.com.marcello.SocialMeli.repositories.users.UserRepository;
import br.com.marcello.SocialMeli.utils.seller.SellerUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BuyerRepositoryImpl implements BuyerRepository {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SellerUtils sellerUtils;

    private final String jsonPath = "./src/main/java/br/com/marcello/SocialMeli/json/buyers.json";

    @Override
    public Boolean alreadyFollows(Integer userId, Integer userIdToFollow) {
        Buyer buyer = this.findById(userId);

        SellerDto sellerToFollow = buyer.getFollowingList().stream()
                .filter(b -> b.getUserId().equals(userIdToFollow))
                .findFirst()
                .orElse(null);

        return sellerToFollow != null;
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
    public boolean unfollow(Integer userId, Integer unfollowUserId) {
        List<Buyer> buyerList = this.initJsonRepo();

        boolean removed = buyerList.stream()
                .filter(buyer -> buyer.getUserId().equals(userId))
                .findFirst()
                .orElse(null)
                .getFollowingList()
                .removeIf(sellerDto -> sellerDto.getUserId().equals(unfollowUserId));

        this.writeOnJsonFile(buyerList);
        return removed;
    }

    @Override
    public void addFollow(Seller seller, Integer userId) {
        List<Buyer> buyerList = this.initJsonRepo();

        buyerList.stream()
                .filter(buyer -> buyer.getUserId().equals(userId))
                .findFirst()
                .orElse(null)
                .getFollowingList()
                .add(this.sellerUtils.convertEntityToDto(seller));

        this.writeOnJsonFile(buyerList);
    }

    @Override
    public Buyer findById(Integer id) {
        List<Buyer> buyerList = this.initJsonRepo();

        return buyerList.stream()
                .filter(buyer -> buyer.getUserId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<BuyerDto> getBuyers() {
        List<Buyer> buyerList = this.initJsonRepo();
        List<BuyerDto> buyerDtoList = new ArrayList<>();

        for(Buyer buyer : buyerList) {
            BuyerDto buyerDto = new BuyerDto();
            buyerDto.setUserId(buyer.getUserId());
            buyerDto.setUsername(buyer.getUsername());
            buyerDtoList.add(buyerDto);
        }

        return buyerDtoList;
    }

    @Override
    public void addBuyer(String username) {
        List<Buyer> buyerList = this.initJsonRepo();

        Buyer buyer = new Buyer();
        buyer.setUserId(this.getMaxId());
        buyer.setUsername(username);
        buyer.setFollowingList(new ArrayList<>());

        buyerList.add(buyer);
        this.writeOnJsonFile(buyerList);
    }

    @Override
    public Integer getMaxId() {
        return this.userRepository.getMaxId();
    }

    private List<Buyer> initJsonRepo() {

        File jsonFile = null;

        jsonFile = new File(this.jsonPath);

        if(jsonFile.length() == 0)
            return new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Buyer>> typeReference = new TypeReference<List<Buyer>>() {};
        List<Buyer> buyerList = null;

        try {
            buyerList = mapper.readValue(jsonFile, typeReference);
        } catch (IOException e ) {
            throw new RuntimeException("Impossible to convert JSON file", e);
        }

        return buyerList;
    }

    private void writeOnJsonFile(List<Buyer> buyerList) {

        JSONArray buyersArray = new JSONArray(buyerList);

        try(FileWriter writer = new FileWriter(this.jsonPath)) {
            writer.write(buyersArray.toString());
            writer.flush();
        } catch(IOException e) {
            throw new RuntimeException("Impossible to write on JSON file", e);
        }

    }

}
