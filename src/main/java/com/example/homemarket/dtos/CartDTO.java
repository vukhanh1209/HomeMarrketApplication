package com.example.homemarket.dtos;

import com.example.homemarket.entities.Cart;
import com.example.homemarket.entities.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Integer CartId;

    private Integer userId;

    private List<CartItemDTO> itemList;
    public CartDTO(Cart cart){
        this.setCartId(cart.getCartID());
        this.setUserId(cart.getUser().getUserID());
        List<CartItemDTO> itemDTOS = new ArrayList<>();
        for (CartItem singleItem : cart.getItems()){
            CartItemDTO itemDTO = new CartItemDTO(singleItem);
            itemDTOS.add(itemDTO);
        }
        this.setItemList(itemDTOS);
    }

}
