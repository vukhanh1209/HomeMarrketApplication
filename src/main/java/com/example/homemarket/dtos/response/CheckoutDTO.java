package com.example.homemarket.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutDTO {
    private Integer userId;

    private String userName;

    private String address;

    private String phone;
}
