package com.example.homemarket.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResetPasswordResponse {
    private String otpCode;
    private String email;
    private String status;
    private String newPassword;
    public UserResetPasswordResponse(){
        this.setStatus("");
        this.setOtpCode("");
        this.setEmail("");
        this.setNewPassword("");
    }
}
