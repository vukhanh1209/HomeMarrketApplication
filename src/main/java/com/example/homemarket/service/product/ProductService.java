package com.example.homemarket.service.product;


import com.example.homemarket.dtos.ProductDTO;
import com.example.homemarket.entities.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAllProduct();
    List<ProductDTO> searchProducts(String query);

    List<ProductDTO> filterProducts(String price, String nxb);
    List<ProductDTO> listProductByCategories(List<Integer> id);
//    Product addProduct(ProductDetailDTO productDetailDTO, MultipartFile thumbnail, List<MultipartFile> images);
    
    List<ProductDTO> getPopularBook();
}
