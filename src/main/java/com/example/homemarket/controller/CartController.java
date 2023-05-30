package com.example.homemarket.controller;

import com.example.homemarket.dtos.CartDTO;
import com.example.homemarket.dtos.request.PlaceOrderRequestDTO;
import com.example.homemarket.dtos.request.ItemEditRequestDTO;
import com.example.homemarket.dtos.request.ItemRequestDTO;
import com.example.homemarket.dtos.response.BaseResponse;
import com.example.homemarket.entities.Order;
import com.example.homemarket.service.cart.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @CrossOrigin
    @GetMapping("/items")
    public ResponseEntity<CartDTO> getCart(@RequestParam("key") Integer userId){
        return ResponseEntity.ok(cartService.getCart(userId));
    }


//   @GetMapping("/{id}")
//    public ResponseEntity<CheckoutDTO> getCheckout(@PathVariable Integer id){
//        return ResponseEntity.ok(cartService.getCheckoutInfo(id));
//    }

    @CrossOrigin
    @GetMapping("/checkout")
    public ResponseEntity<BaseResponse> checkout(@RequestParam("key") List<Integer> cartItemId) {
        BaseResponse response = cartService.checkout(cartItemId);
        return ResponseEntity.ok(response);
    }
    @CrossOrigin
    @PostMapping("/add")
    public ResponseEntity<BaseResponse> createItem(@ModelAttribute ItemRequestDTO itemRequestDTO){
        try {
            return ResponseEntity.ok(cartService.createItem(itemRequestDTO));
        }catch (RuntimeException e){
            return ResponseEntity.
                    status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new BaseResponse(false, e.getMessage()));
        }
    }
    @CrossOrigin
    @DeleteMapping("/delete")
    public ResponseEntity<BaseResponse> delete(@RequestParam("key") Integer itemId){
        try{
            return ResponseEntity.ok(cartService.deleteItem(itemId));
        }catch (RuntimeException e){
            return ResponseEntity.
                    status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new BaseResponse(false,e.getMessage()));
        }
    }
    @CrossOrigin
    @PostMapping("/edit")
    public ResponseEntity<BaseResponse> update(@ModelAttribute ItemEditRequestDTO itemEditRequestDTO){
        try {
            return ResponseEntity.ok(cartService.updateItemQuantity(itemEditRequestDTO));
        }catch (RuntimeException e){
            return ResponseEntity.
                    status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new BaseResponse(false, e.getMessage()));
        }
    }
    @CrossOrigin
    @PostMapping("/place_order")
    public ResponseEntity<BaseResponse> placeorder(@ModelAttribute PlaceOrderRequestDTO placeOrderRequestDTO){
        try {
            return ResponseEntity.ok(cartService.placeorder(placeOrderRequestDTO));
        }catch (RuntimeException e){
            return ResponseEntity.
                    status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new BaseResponse(false, e.getMessage()));
        }
    }
}
