package com.example.homemarket.controller;

import com.example.homemarket.dtos.ProductDTO;
import com.example.homemarket.entities.Product;
import com.example.homemarket.service.product.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

      private final ProductService productService;

//    private final ProductDetailService productDetailService;
//    private final CloudinaryService cloudinaryService;

    public ProductController(ProductService productService){//, ProductDetailService productDetailService, CloudinaryService cloudinaryService) {
        this.productService = productService;
//        this.productDetailService = productDetailService;
//        this.cloudinaryService = cloudinaryService;
    }
    @CrossOrigin
    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAllProduct(){
        return new ResponseEntity<>(productService.getAllProduct(), HttpStatus.OK);
    }
//    @PostMapping
//    public ResponseEntity<Product> create(@RequestPart ProductDetailDTO productDetailDTO,
//                                          @RequestPart("thumbnail") MultipartFile thumbnail,
//                                          @RequestPart("images") List<MultipartFile> images){
//        try{
//            Product product = productService.addProduct(productDetailDTO,thumbnail, images);
//            return ResponseEntity.ok().body(product);
//        }catch (Exception e){
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error adding product",e);
//        }
//    }
//    @GetMapping("/{id}")
//    public ResponseEntity<ProductDetailDTO> getDetailProduct(@PathVariable Integer id){
//        return new ResponseEntity<>(productDetailService.getProductDetail(id),HttpStatus.OK);
//    }
    @CrossOrigin
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> getSearchProduct(@RequestParam("key") String query){
        return new ResponseEntity<>(productService.searchProducts(query),HttpStatus.OK);
    }
    @CrossOrigin
    @GetMapping("/productByCategory")
    public ResponseEntity<List<ProductDTO>> listProductByIds(@RequestParam("key") List<Integer> ids) {
        List<ProductDTO> products = productService.listProductByCategories(ids);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}

