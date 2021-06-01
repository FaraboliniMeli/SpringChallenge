package br.com.marcello.SocialMeli.utils.buyer;

import br.com.marcello.SocialMeli.dtos.buyers.BuyerDto;
import br.com.marcello.SocialMeli.model.Buyer;

import java.util.List;

public interface BuyerUtils {

    List<BuyerDto> convertEntityToDto(List<Buyer> buyerList);

}
