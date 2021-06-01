package br.com.marcello.SocialMeli.repositories.buyer;

import br.com.marcello.SocialMeli.dtos.buyers.BuyerDto;
import br.com.marcello.SocialMeli.model.Buyer;
import br.com.marcello.SocialMeli.model.Seller;
import br.com.marcello.SocialMeli.repositories.users.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BuyerRepositoryImpl implements BuyerRepository {

    @Autowired
    private UserRepository userRepository;

    private final String jsonPath = "./src/main/java/br/com/marcello/SocialMeli/json/buyers.json";

    @Override
    public void addFollow(Seller seller, Integer buyerId) {
        List<Buyer> buyerList = this.initJsonRepo();

        buyerList.stream()
                .filter(buyer -> buyer.getUserId().equals(buyerId))
                .findFirst()
                .orElse(null)
                .getFollowingList()
                .add(seller);

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
