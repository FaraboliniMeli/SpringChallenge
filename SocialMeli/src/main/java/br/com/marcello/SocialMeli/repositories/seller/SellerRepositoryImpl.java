package br.com.marcello.SocialMeli.repositories.seller;

import br.com.marcello.SocialMeli.dtos.sellers.SellerDto;
import br.com.marcello.SocialMeli.model.Buyer;
import br.com.marcello.SocialMeli.model.Seller;
import br.com.marcello.SocialMeli.model.User;
import br.com.marcello.SocialMeli.repositories.users.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SellerRepositoryImpl implements SellerRepository {

    @Autowired
    private UserRepository userRepository;

    private final String jsonPath = "./src/main/java/br/com/marcello/SocialMeli/json/sellers.json";

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
    public void addFollower(Buyer buyer, Integer sellerId) {
        List<Seller> sellerList = this.initJsonRepo();

        sellerList.stream()
                .filter(seller -> seller.getUserId().equals(sellerId))
                .findFirst()
                .orElse(null)
                .getFollowerList()
                .add(buyer);

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

    private List<Seller> initJsonRepo() {

        File jsonFile = null;

        jsonFile = new File(this.jsonPath);

        if(jsonFile.length() == 0)
            return new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
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
