package br.com.marcello.SocialMeli.utils.user;

import br.com.marcello.SocialMeli.dtos.user.UserDto;
import br.com.marcello.SocialMeli.model.User;

import java.util.List;

public interface UserUtils {

    List<UserDto> convertEntityToDto(List<User> userList);

}
