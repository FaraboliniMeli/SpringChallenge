package br.com.marcello.SocialMeli.utils.seller;

import br.com.marcello.SocialMeli.dtos.sellers.SellerDto;
import br.com.marcello.SocialMeli.model.Seller;

import java.util.List;

public interface SellerUtils {

    /**
     * Converts a List of Seller Entity to a List of Seller DTO.
     * @param sellerList
     * @return sellerDtoList
     */
    List<SellerDto> convertEntityToDto(List<Seller> sellerList);

    /**
     * Converts a entity Seller to Seller DTO.
     * @param seller
     * @return sellerDto
     */
    SellerDto convertEntityToDto(Seller seller);

}
