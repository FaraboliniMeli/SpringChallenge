package br.com.marcello.SocialMeli.repositories.users;

import br.com.marcello.SocialMeli.model.User;
import br.com.marcello.SocialMeli.model.UserType;

import java.util.List;

public interface UserRepository {

    User findById(Integer id);

    void addUser(String username, UserType userType);

    List<User> getUsers();

    Integer getMaxId();

}
