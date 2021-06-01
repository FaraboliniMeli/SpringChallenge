package br.com.marcello.SocialMeli.controllers;

import br.com.marcello.SocialMeli.dtos.sellers.RegisterSeller;
import br.com.marcello.SocialMeli.dtos.sellers.SellerDto;
import br.com.marcello.SocialMeli.model.Seller;
import br.com.marcello.SocialMeli.model.UserType;
import br.com.marcello.SocialMeli.repositories.seller.SellerRepository;
import br.com.marcello.SocialMeli.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users/seller")
public class SellerController {

    @Autowired
    private SellerRepository sellerRepositoryImpl;

    @Autowired
    private UserRepository userRepositoryImpl;

    @GetMapping("/list")
    public ResponseEntity<List<SellerDto>> getSellers() {
        return new ResponseEntity<>(this.sellerRepositoryImpl.getSellers(), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<SellerDto> registerSeller(@RequestBody RegisterSeller registerSeller) {

        this.userRepositoryImpl.addUser(registerSeller.getUsername(), UserType.SELLER);
        this.sellerRepositoryImpl.addSeller(registerSeller.getUsername());

        SellerDto sellerDto = new SellerDto();
        sellerDto.setUsername(registerSeller.getUsername());
        sellerDto.setUserId(this.sellerRepositoryImpl.getMaxId());

        return new ResponseEntity<>(sellerDto, HttpStatus.OK);

    }

}
