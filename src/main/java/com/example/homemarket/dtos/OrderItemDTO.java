package com.example.homemarket.dtos;

import com.example.homemarket.entities.CartItem;
import com.example.homemarket.entities.Order;
import com.example.homemarket.entities.OrderItem;
import com.example.homemarket.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Long orderItemID;
    private int quantity;
    private String productname;
    private Integer productid;
    private Float price;
    private String productImageURL;
    private String weight;
    private String categoryname;
    public OrderItemDTO(OrderItem orderitem) {
        this.setOrderItemID(orderitem.getOrderItemID());
        this.setQuantity(orderitem.getQuantity());
        this.setProductname(orderitem.getProduct().getProductName());
        this.setProductid(orderitem.getProduct().getProductID());
        this.setPrice(orderitem.getProduct().getPrice());
        this.setProductImageURL(orderitem.getProduct().getProductImageURL());
        this.setWeight(orderitem.getProduct().getWeight());
        this.setCategoryname(orderitem.getProduct().getCategory().getCategoryName());
    }
}
