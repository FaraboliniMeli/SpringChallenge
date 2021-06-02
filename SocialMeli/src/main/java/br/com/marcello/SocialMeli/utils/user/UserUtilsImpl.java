package br.com.marcello.SocialMeli.utils.user;

import br.com.marcello.SocialMeli.dtos.user.UserDto;
import br.com.marcello.SocialMeli.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserUtilsImpl implements UserUtils {

    @Override
    public List<UserDto> convertEntityToDto(List<User> userList) {
        List<UserDto> userDtoList = new ArrayList<>();
        for(User user : userList) {
            UserDto userDto = new UserDto();
            userDto.setUserId(user.getUserId());
            userDto.setUsername(user.getUsername());
            userDtoList.add(userDto);
        }

        return userDtoList;
    }
}
