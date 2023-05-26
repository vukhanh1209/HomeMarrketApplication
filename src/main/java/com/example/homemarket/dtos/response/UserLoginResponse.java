package com.example.homemarket.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
public class UserLoginResponse {
    private String email;
    private String password;
    private String status;
    private Integer id;

    public UserLoginResponse() {
        this.setStatus("");
        this.setPassword("");
        this.setEmail("");
        this.setId(0);
    }   
}
