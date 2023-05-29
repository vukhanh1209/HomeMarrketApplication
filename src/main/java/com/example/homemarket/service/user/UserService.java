package com.example.homemarket.service.user;

import com.example.homemarket.dtos.OrderDTO;
import com.example.homemarket.dtos.UserDTO;
import com.example.homemarket.dtos.response.UserForgetPasswordResponse;
import com.example.homemarket.dtos.response.UserLoginResponse;
import com.example.homemarket.dtos.response.UserRegisterOtpRespone;
import com.example.homemarket.dtos.response.UserResetPasswordResponse;
import com.example.homemarket.entities.User;

public interface UserService {
    User createUser(UserDTO userDTO);
    UserLoginResponse login(UserLoginResponse userLoginResponse);
    UserRegisterOtpRespone otp(UserRegisterOtpRespone userRegisterOtpRespone);
    UserForgetPasswordResponse forgetPassword(UserForgetPasswordResponse userForgetPasswordResponse);
    UserResetPasswordResponse resetPassword(UserResetPasswordResponse userResetPasswordResponse);
    UserDTO getUser(Integer id);
}
