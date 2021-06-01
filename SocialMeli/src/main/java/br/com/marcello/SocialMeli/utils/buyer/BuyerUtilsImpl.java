package br.com.marcello.SocialMeli.utils.buyer;

import br.com.marcello.SocialMeli.dtos.buyers.BuyerDto;
import br.com.marcello.SocialMeli.dtos.sellers.SellerDto;
import br.com.marcello.SocialMeli.model.Buyer;
import br.com.marcello.SocialMeli.model.Seller;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BuyerUtilsImpl implements BuyerUtils {

    @Override
    public List<BuyerDto> convertEntityToDto(List<Buyer> buyerList) {
        List<BuyerDto> buyerDtoList = new ArrayList<>();
        for(Buyer buyer : buyerList) {
            BuyerDto buyerDto = new BuyerDto();
            buyerDto.setUserId(buyer.getUserId());
            buyerDto.setUsername(buyer.getUsername());
            buyerDtoList.add(buyerDto);
        }

        return buyerDtoList;
    }
}
