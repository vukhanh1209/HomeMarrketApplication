package com.example.homemarket.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemDetailDTO {

    private String itemName;

    private Integer quantity;

    private Float price;

    private String thumbnailPath;

    private Integer productId;

}
