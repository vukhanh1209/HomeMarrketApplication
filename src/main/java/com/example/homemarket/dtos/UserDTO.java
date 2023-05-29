package com.example.homemarket.dtos;

import com.example.homemarket.entities.CartItem;
import com.example.homemarket.entities.Order;
import com.example.homemarket.entities.User;
import com.example.homemarket.utils.EnumRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

//
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO extends User {
    private Integer userid;
    private String address;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;
    private EnumRole role;
    private String status;
    private List<OrderDTO> orderlist;
    public UserDTO(User user) {
        this.setUserID(user.getUserID());
        this.setAddress(user.getAddress());
        this.setEmail(user.getEmail());
        this.setFirstName(user.getFirstName());
        this.setLastName(user.getLastName());
        this.setPassword(user.getPassword());
        this.setPhoneNumber(user.getPhoneNumber());
        this.setRole(user.getRole());
        if(user.getIsActive()==true){
        this.setStatus("Tài khoản đã kích hoạt");
    }
        else{
            this.setStatus("Tài khoản chưa kích hoạt");
        }
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for (Order singleItem : user.getOrderList()){
            OrderDTO orderDTO = new OrderDTO(singleItem);
            orderDTOS.add(orderDTO);
        }
        this.setOrderlist(orderDTOS);
    }
}
