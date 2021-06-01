package br.com.marcello.SocialMeli.controllers.socialMeli;

import br.com.marcello.SocialMeli.model.User;
import br.com.marcello.SocialMeli.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/list")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(this.userRepository.getUsers(), HttpStatus.OK);
    }

}
