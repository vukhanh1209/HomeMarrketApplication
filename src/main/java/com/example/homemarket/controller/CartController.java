package com.example.homemarket.controller;

import com.example.homemarket.dtos.CartDTO;
import com.example.homemarket.dtos.request.ItemEditRequestDTO;
import com.example.homemarket.dtos.request.ItemRequestDTO;
import com.example.homemarket.dtos.response.BaseResponse;
import com.example.homemarket.service.cart.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/items/{userId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable Integer userId){
        return ResponseEntity.ok(cartService.getCart(userId));
    }

//   @GetMapping("/{id}")
//    public ResponseEntity<CheckoutDTO> getCheckout(@PathVariable Integer id){
//        return ResponseEntity.ok(cartService.getCheckoutInfo(id));
//    }

//    @PostMapping("/buy-now")
//    public ResponseEntity<BaseResponse> buyNow(@RequestBody CheckoutRequestBuyNowDTO checkoutRequestBuyNowDTO){
//        try{
//            return ResponseEntity.ok(cartService.buyNow(checkoutRequestBuyNowDTO));
//        }catch (RuntimeException e){
//            return ResponseEntity.
//                    status(HttpStatus.INTERNAL_SERVER_ERROR).
//                    body(new BaseResponse(false, e.getMessage()));
//        }
//    }
//    @PostMapping("/checkout")
//    public ResponseEntity<BaseResponse> checkout(@RequestBody CheckoutRequestDTO checkoutRequestDTO){
//        try {
//            return ResponseEntity.ok(cartService.checkout(checkoutRequestDTO));
//        }catch (RuntimeException e){
//            return ResponseEntity.
//                    status(HttpStatus.INTERNAL_SERVER_ERROR).
//                    body(new BaseResponse(false,e.getMessage()));
//        }
//    }
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
    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<BaseResponse> delete(@PathVariable Integer itemId){
        try{
            return ResponseEntity.ok(cartService.deleteItem(itemId));
        }catch (RuntimeException e){
            return ResponseEntity.
                    status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body(new BaseResponse(false,e.getMessage()));
        }
    }
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
}
