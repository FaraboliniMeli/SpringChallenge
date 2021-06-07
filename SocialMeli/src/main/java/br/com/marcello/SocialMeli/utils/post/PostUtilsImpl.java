package br.com.marcello.SocialMeli.utils.post;

import br.com.marcello.SocialMeli.dtos.post.PostDto;
import br.com.marcello.SocialMeli.dtos.post.PromoPostDto;
import br.com.marcello.SocialMeli.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostUtilsImpl implements PostUtils {

    @Override
    public Post convertDtoToEntity(PromoPostDto promoPostDto) {
        Post post = new Post();
        post.setPostId(promoPostDto.getPostId());
        post.setDate(promoPostDto.getDate());
        post.setDetail(promoPostDto.getDetail());
        post.setCategory(promoPostDto.getCategory());
        post.setPrice(promoPostDto.getPrice());
        post.setHasPromo(promoPostDto.getHasPromo());
        post.setDiscount(promoPostDto.getDiscount());

        return post;
    }

    @Override
    public Post convertDtoToEntity(PostDto postDto) {
        Post post = new Post();
        post.setPostId(postDto.getPostId());
        post.setDate(postDto.getDate());
        post.setDetail(postDto.getDetail());
        post.setCategory(postDto.getCategory());
        post.setPrice(postDto.getPrice());

        return post;
    }
}
