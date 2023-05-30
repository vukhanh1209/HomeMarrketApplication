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
    private Integer CartItemid;

    private String itemName;

    private Integer quantity;

    private Float price;

    private String weight;

    private Integer productId;

    private Integer cartId;
    private String urlImage;
    private String categoryName;

    public CartItemDTO(CartItem item){
        this.setCartItemid(item.getCartItemID());
        this.setItemName(item.getProduct().getProductName());
        this.setWeight(item.getProduct().getWeight());
        this.setQuantity(item.getQuantity());
        this.setPrice(item.getProduct().getPrice());
        this.setProductId(item.getProduct().getProductID());
        this.setCartId(item.getCart().getCartID());
        this.setUrlImage(item.getProduct().getProductImageURL());
        this.setCategoryName(item.getProduct().getCategory().getCategoryName());
    }
}
