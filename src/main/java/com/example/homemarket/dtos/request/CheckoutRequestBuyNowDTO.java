package com.example.homemarket.dtos.request;


import com.example.homemarket.dtos.ItemDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutRequestBuyNowDTO {

    private Integer userId;

    private String userName;

    private String address;

    private String phone;

    private ItemDetailDTO itemDetail;

    private String paymentMethod;

    private Float totalPrice;

}
