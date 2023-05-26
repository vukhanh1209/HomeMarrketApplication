package com.example.homemarket.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterOtpRespone {
    private String status;
    private Boolean otp;
    private String email;
    private String otpCode;
    public UserRegisterOtpRespone(){
        this.setOtp(false);
        this.setOtpCode("");
        this.setStatus("");
        this.setEmail("");
    }
}
