package br.com.marcello.SocialMeli.utils.seller;

import br.com.marcello.SocialMeli.dtos.sellers.SellerDto;
import br.com.marcello.SocialMeli.model.Seller;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SellerUtilsImpl implements SellerUtils {

    @Override
    public List<SellerDto> convertEntityToDto(List<Seller> sellerList) {
        List<SellerDto> sellerDtoList = new ArrayList<>();
        for(Seller seller : sellerList) {
            SellerDto sellerDto = new SellerDto();
            sellerDto.setUserId(seller.getUserId());
            sellerDto.setUsername(seller.getUsername());
            sellerDtoList.add(sellerDto);
        }

        return sellerDtoList;
    }
}
