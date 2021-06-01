package br.com.marcello.SocialMeli.controllers;

import br.com.marcello.SocialMeli.dtos.buyers.BuyerDto;
import br.com.marcello.SocialMeli.dtos.buyers.RegisterBuyer;
import br.com.marcello.SocialMeli.model.UserType;
import br.com.marcello.SocialMeli.repositories.buyer.BuyerRepository;
import br.com.marcello.SocialMeli.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users/buyer")
public class BuyerController {

    @Autowired
    private BuyerRepository buyerRepositoryImpl;

    @Autowired
    private UserRepository userRepositoryImpl;

    @GetMapping("/list")
    public ResponseEntity<List<BuyerDto>> getBuyers() {
        return new ResponseEntity<>(this.buyerRepositoryImpl.getBuyers(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<BuyerDto> registerBuyer(@RequestBody RegisterBuyer registerBuyer) {

        this.userRepositoryImpl.addUser(registerBuyer.getUsername(), UserType.BUYER);
        this.buyerRepositoryImpl.addBuyer(registerBuyer.getUsername());

        BuyerDto buyerDto = new BuyerDto();
        buyerDto.setUsername(registerBuyer.getUsername());
        buyerDto.setUserId(this.buyerRepositoryImpl.getMaxId());

        return new ResponseEntity<>(buyerDto, HttpStatus.OK);
    }

}
