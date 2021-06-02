package br.com.marcello.SocialMeli.utils.seller;

import br.com.marcello.SocialMeli.dtos.sellers.SellerDto;
import br.com.marcello.SocialMeli.model.Seller;

import java.util.List;

public interface SellerUtils {

    List<SellerDto> convertEntityToDto(List<Seller> sellerList);

    SellerDto convertEntityToDto(Seller seller);

}
