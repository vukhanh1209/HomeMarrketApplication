package com.example.homemarket.dtos.request;

import com.example.homemarket.dtos.CartItemDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutRequestDTO {
    private Integer userId;

    private String userName;

    private String address;

    private String phone;

    private List<CartItemDTO> itemDTOS;

    private String paymentMethod;

    private Float totalPrice;
}
