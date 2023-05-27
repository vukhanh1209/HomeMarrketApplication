package com.example.homemarket.service.cart;

import com.example.homemarket.dtos.CartDTO;
import com.example.homemarket.dtos.request.ItemEditRequestDTO;
import com.example.homemarket.dtos.request.ItemRequestDTO;
import com.example.homemarket.dtos.response.BaseResponse;
import org.springframework.transaction.annotation.Transactional;

public interface CartService {
    CartDTO getCart(Integer userId);
//    BaseResponse buyNow(CheckoutRequestBuyNowDTO checkoutBuyNowRequestDTO);
//    CheckoutDTO getCheckoutInfo(Integer userId);
//    BaseResponse checkout(CheckoutRequestDTO checkoutRequestDTO);
    BaseResponse createItem(ItemRequestDTO itemRequestDTO);
    BaseResponse deleteItem(Integer itemId);
    BaseResponse updateItemQuantity(ItemEditRequestDTO itemEditRequestDTO);
}
