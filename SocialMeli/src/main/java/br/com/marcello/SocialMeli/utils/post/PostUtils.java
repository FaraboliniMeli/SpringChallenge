package br.com.marcello.SocialMeli.utils.post;

import br.com.marcello.SocialMeli.dtos.post.PostDto;
import br.com.marcello.SocialMeli.dtos.post.PromoPostDto;
import br.com.marcello.SocialMeli.model.Post;

public interface PostUtils {

    /**
     * Converts a promoPostDto to entity Post.
     * @param promoPostDto
     * @return post
     */
    Post convertDtoToEntity(PromoPostDto promoPostDto);

    /**
     * Converts a postDto to entityDto
     * @param postDto
     * @return post
     */
    Post convertDtoToEntity(PostDto postDto);

}
