package com.example.homemarket.dtos;

import com.example.homemarket.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Integer id;

    private String productName;

    private Float price;
    private Integer Quantity;
    private String imagePath;
    private String weight;
    private String category;
    public ProductDTO(Product product){
        this.setId(product.getProductID());
        this.setProductName(product.getProductName());
        this.setPrice(product.getPrice());
        this.setWeight(product.getWeight());
        this.setImagePath(product.getProductImageURL());
        this.setCategory(product.getCategory().getCategoryName());
        this.setQuantity(product.getQuantity());
    }
}
