package com.example.homemarket.service.user;

import com.example.homemarket.dtos.OrderDTO;
import com.example.homemarket.dtos.UserDTO;
import com.example.homemarket.dtos.response.UserForgetPasswordResponse;
import com.example.homemarket.dtos.response.UserLoginResponse;
import com.example.homemarket.dtos.response.UserRegisterOtpRespone;
import com.example.homemarket.dtos.response.UserResetPasswordResponse;
import com.example.homemarket.entities.Order;
import com.example.homemarket.entities.User;
import com.example.homemarket.repositories.OrderRepository;
import com.example.homemarket.repositories.UserRepository;
import com.example.homemarket.utils.EnumRole;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private OrderRepository orderRepository;
    public void sendEmail(String toEmail, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("enddismyrapname@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        System.out.println("Mail sent successfully....");
    }
    @Override
    public User createUser(UserDTO userDTO) {
        int OTP = 5;
        User user = new User();
        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0','9').build();
        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());
        if(existingUser.isPresent() && existingUser.get().getIsActive()==true){
            userDTO.setStatus("Tài khoản đã đăng ký");
            userDTO.setIsActive(true);
            return userDTO;
        }
        if(existingUser.isPresent() && existingUser.get().getIsActive()==false){
            userRepository.delete(existingUser.get());
            user.setUserID(userDTO.getUserID());
            user.setAddress(userDTO.getAddress());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setRole(EnumRole.USER);
            user.setIsActive(false);
            user.setVerificationCode(generator.generate(OTP));
            sendEmail(userDTO.getEmail(),"OTP CODE FOR REGISTER","Here is your OTP Code: " + user.getVerificationCode());
        }
        else{
        user.setUserID(userDTO.getUserID());
        user.setAddress(userDTO.getAddress());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setRole(EnumRole.USER);
        user.setIsActive(false);
        user.setVerificationCode(generator.generate(OTP));
        sendEmail(userDTO.getEmail(),"OTP CODE FOR REGISTER","Here is your OTP Code: " + user.getVerificationCode());
        }
        return userRepository.save(user);
    }

    @Override
    public UserLoginResponse login(UserLoginResponse userLoginResponse) {
        UserLoginResponse response = new UserLoginResponse();
        List<User> users = userRepository.findAll();
        for(User user:users){
            if(user.getEmail().equals(userLoginResponse.getEmail())){
                if(user.getIsActive() == false){
                    response.setStatus("Tài khoản chưa được kích hoạt");
                    System.out.println(response.getEmail());
                    System.out.println(response.getPassword());
                    System.out.println(response.getStatus());
                }
                else if (!user.getPassword().equals(userLoginResponse.getPassword())) {
                    response.setStatus("Mật khẩu sai");
                    System.out.println(response.getEmail());
                    System.out.println(response.getPassword());
                    System.out.println(response.getStatus());
                }
                else{
                    response.setEmail(user.getEmail());
                    response.setPassword(user.getPassword());
                    response.setId(user.getUserID());
                    response.setStatus("Đăng nhập thành công");
                }
                break;
            }
            else{
                if(!user.getEmail().equals(userLoginResponse.getEmail())){
                    response.setStatus("Email này chưa đăng ký");
                    System.out.println(response.getEmail());
                    System.out.println(response.getEmail());
                    System.out.println(response.getPassword());
                    System.out.println(response.getStatus());
                }
            }
        }
        return response;
    }

    @Override
    public UserRegisterOtpRespone otp(UserRegisterOtpRespone userRegisterOtpRespone) {
        UserRegisterOtpRespone respone = new UserRegisterOtpRespone();
        List<User> users = userRepository.findAll();
        for(User user:users){
            if(user.getEmail().equals(userRegisterOtpRespone.getEmail())){
                if(!user.getVerificationCode().equals(userRegisterOtpRespone.getOtpCode())){
                    respone.setStatus("Mã OTP nhập vào không chính xác");
                    respone.setEmail(user.getEmail());
                }
                else{
                    user.setVerificationCode(null);
                    user.setIsActive(true);
                    userRepository.save(user);
                    respone.setStatus("Tài khoản được kích hoạt");
                    respone.setEmail(user.getEmail());
                    respone.setOtp(true);
                    respone.setOtpCode(user.getVerificationCode());
                }
            }
        }
        return respone;
    }

    @Override
    public UserForgetPasswordResponse forgetPassword(UserForgetPasswordResponse userForgetPasswordResponse) {
        int OTP = 5;
        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0','9').build();
        List<User> users = userRepository.findAll();
        UserForgetPasswordResponse response = new UserForgetPasswordResponse();
        for(User user:users){
            if(!user.getEmail().equals(userForgetPasswordResponse.getEmail())){
                response.setStatus("Email này chưa được đăng ký");
            }
            else {
                response.setEmail(user.getEmail());
                response.setStatus("Đã gởi mã OTP về cho gmail");
                user.setVerificationCode(generator.generate(OTP));
                sendEmail(user.getEmail(),"OTP CODE FOR RESET PASSWORD","Here is your OTP Code: " + user.getVerificationCode());
                userRepository.save(user);
                break;
            }
        }
        return response;
    }

    @Override
    public UserResetPasswordResponse resetPassword(UserResetPasswordResponse userResetPasswordResponse) {
        List<User> users = userRepository.findAll();
        UserResetPasswordResponse response = new UserResetPasswordResponse();
        for(User user:users){
            if(user.getEmail().equals(userResetPasswordResponse.getEmail())){
                if(user.getVerificationCode().equals(userResetPasswordResponse.getOtpCode())){
                    response.setStatus("Đổi mật khẩu thành công");
                    response.setOtpCode(user.getVerificationCode());
                    response.setEmail(user.getEmail());
                    response.setNewPassword(userResetPasswordResponse.getNewPassword());
                    user.setVerificationCode(null);
                    user.setPassword(userResetPasswordResponse.getNewPassword());
                    userRepository.save(user);
                }
                else{
                    response.setStatus("Mã OTP Sai");
                }
                break;
            }

        }
        return response;
    }

    @Override
    public UserDTO getUser(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            throw new RuntimeException("User not found with id: "+id);
        }
        UserDTO userDTO = new UserDTO(user.get());
        return userDTO;
    }
}
