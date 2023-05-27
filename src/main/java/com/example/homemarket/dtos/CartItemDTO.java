package com.example.homemarket.dtos;

import com.example.homemarket.entities.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
    private Integer id;

    private String itemName;

    private Integer quantity;

    private Float price;

    private String thumbnailPath;

    private Integer productId;

    private Integer cartId;


    public CartItemDTO(CartItem item){
        this.setId(item.getCartItemID());
        this.setItemName(item.getProduct().getProductName());
        this.setQuantity(item.getQuantity());
        this.setPrice(item.getProduct().getPrice());
        this.setProductId(item.getProduct().getProductID());
        this.setCartId(item.getCart().getCartID());
    }
}
