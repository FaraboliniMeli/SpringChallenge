package br.com.marcello.SocialMeli.repositories.users;

import br.com.marcello.SocialMeli.model.User;
import br.com.marcello.SocialMeli.model.UserType;

import java.util.List;

public interface UserRepository {

    /**
     * Finds a User by searching for ID.
     * @param id User ID.
     * @return user User found by ID.
     */
    User findById(Integer id);

    /**
     * Add a User to repository.
     * @param username Account username.
     * @param userType Account Type (Buyer or Seller).
     */
    void addUser(String username, UserType userType);

    /**
     * Returns a List of all Users in repository.
     * @return userList All accounts created.
     */
    List<User> getUsers();

    /**
     * Get the current max ID for generate next IDs.
     * @return maxId ID for last account created.
     */
    Integer getMaxId();

}
