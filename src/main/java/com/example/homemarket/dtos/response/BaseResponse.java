package com.example.homemarket.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;
@Getter
@Setter
@AllArgsConstructor
public class BaseResponse {
    @NonNull
    private Boolean success;

    @NonNull
    private String message;

    public BaseResponse() {
        this.success = true;
        this.message = "";
    }
}
