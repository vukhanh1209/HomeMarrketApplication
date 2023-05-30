package com.example.homemarket.service.cart;

import com.example.homemarket.dtos.CartDTO;
import com.example.homemarket.dtos.OrderDTO;
import com.example.homemarket.dtos.UserDTO;
import com.example.homemarket.dtos.request.ItemEditRequestDTO;
import com.example.homemarket.dtos.request.ItemRequestDTO;
import com.example.homemarket.dtos.request.PlaceOrderRequestDTO;
import com.example.homemarket.dtos.response.BaseResponse;
import com.example.homemarket.dtos.response.CheckoutDTO;
import com.example.homemarket.entities.Order;

import java.util.List;

public interface CartService {
    CartDTO getCart(Integer userId);
    CheckoutDTO getCheckoutInfo(Integer userId);
    BaseResponse checkout(List<Integer> cartItemIds);
    BaseResponse createItem(ItemRequestDTO itemRequestDTO);
    BaseResponse deleteItem(Integer itemId);
    BaseResponse updateItemQuantity(ItemEditRequestDTO itemEditRequestDTO);
    BaseResponse placeorder(PlaceOrderRequestDTO placeOrderRequestDTO);
//    UserDTO getOrder(Integer id);
}
