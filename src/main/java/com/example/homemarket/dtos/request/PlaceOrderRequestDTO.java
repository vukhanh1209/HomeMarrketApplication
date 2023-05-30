package com.example.homemarket.dtos.request;

import com.example.homemarket.dtos.CartItemDTO;
import com.example.homemarket.entities.Cart;
import com.example.homemarket.entities.CartItem;
import com.example.homemarket.utils.EnumOrderStatus;
import com.example.homemarket.utils.EnumPaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlaceOrderRequestDTO {
    private Date orderDate;
    private String address;
    private String phone;
    private EnumPaymentMethod paymentMethod;
    private Integer cartID;
    private String firstName;
    private  String lastName;
}
