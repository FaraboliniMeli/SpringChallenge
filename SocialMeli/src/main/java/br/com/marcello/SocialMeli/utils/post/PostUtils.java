package br.com.marcello.SocialMeli.utils.post;

import br.com.marcello.SocialMeli.dtos.post.PostDto;
import br.com.marcello.SocialMeli.model.Post;

public interface PostUtils {

    Post convertDtoToEntity(PostDto postDto);

}
