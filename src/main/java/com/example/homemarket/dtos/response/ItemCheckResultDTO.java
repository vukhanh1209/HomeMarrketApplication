package com.example.homemarket.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class ItemCheckResultDTO {
    private String itemName;
    private int requestedQuantity;
    private int availableQuantity;
    private boolean isQuantityAvailable;

    public ItemCheckResultDTO(String itemName, int requestedQuantity, int availableQuantity, boolean isQuantityAvailable) {
        this.itemName = itemName;
        this.requestedQuantity = requestedQuantity;
        this.availableQuantity = availableQuantity;
        this.isQuantityAvailable = isQuantityAvailable;
    }
}

