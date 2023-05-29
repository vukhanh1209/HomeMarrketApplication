package com.example.homemarket.dtos;

import com.example.homemarket.entities.CartItem;
import com.example.homemarket.entities.Order;
import com.example.homemarket.entities.OrderItem;
import com.example.homemarket.entities.User;
import com.example.homemarket.utils.EnumOrderStatus;
import com.example.homemarket.utils.EnumPaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Integer orderID;
    private Date orderDate;
    private float totalValue;
    private String userName;
    private String address;
    private String phoneNumber;
    private EnumPaymentMethod paymentMethod;
    private EnumOrderStatus status;
    private List<OrderItemDTO> ItemList;
    public OrderDTO(Order order) {
        this.setOrderID(order.getOrderID());
        this.setOrderDate(order.getOrderDate());
        this.setTotalValue(order.getTotalValue());
        this.setUserName(order.getUserName());
        this.setAddress(order.getAddress());
        this.setPaymentMethod(order.getPaymentMethod());
        this.setStatus(order.getStatus());
        this.setPhoneNumber(order.getPhoneNumber());
        List<OrderItemDTO>Order_items = new ArrayList<>();
        for (OrderItem singleItem : order.getOrderItemList()){
            OrderItemDTO Order_item = new OrderItemDTO(singleItem);
            Order_items.add(Order_item);
        }
        this.setItemList(Order_items);
    }
}
