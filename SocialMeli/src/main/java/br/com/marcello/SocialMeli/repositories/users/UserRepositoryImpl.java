package br.com.marcello.SocialMeli.repositories.users;

import br.com.marcello.SocialMeli.model.User;
import br.com.marcello.SocialMeli.model.UserType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final String jsonPath = "./src/main/java/br/com/marcello/SocialMeli/json/users.json";

    @Override
    public User findById(Integer id) {
        List<User> userList = this.initJsonRepo();

        return userList.stream()
                .filter(user -> user.getUserId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void addUser(String username, UserType userType) {
        User user = new User();
        user.setUserId(this.getMaxId() + 1);
        user.setUsername(username);
        user.setUserType(userType);
        this.writeOnJsonFile(user);
    }

    @Override
    public List<User> getUsers() {
        return this.initJsonRepo();
    }

    @Override
    public Integer getMaxId() {
        List<User> userList = this.initJsonRepo();
        if(userList.size() == 0)
            return 0;

        return (userList.stream()
                .mapToInt(User::getUserId)
                .max()
                .getAsInt());
    }

    private List<User> initJsonRepo() {

        File jsonFile = null;

        jsonFile = new File(this.jsonPath);

        if(jsonFile.length() == 0)
            return new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<User>> typeReference = new TypeReference<List<User>>() {};
        List<User> userList = null;

        try {
            userList = mapper.readValue(jsonFile, typeReference);
        } catch (IOException e ) {
            throw new RuntimeException("Impossible to convert JSON file", e);
        }

        return userList;
    }

    private void writeOnJsonFile(User user) {

        List<User> userList = this.initJsonRepo();
        JSONArray usersArray = new JSONArray(userList);
        JSONObject userJson = new JSONObject(user);
        usersArray.put(userJson);

        try(FileWriter writer = new FileWriter(this.jsonPath)) {
            writer.write(usersArray.toString());
            writer.flush();
        } catch(IOException e) {
            throw new RuntimeException("Impossible to write on JSON file", e);
        }

    }

}
