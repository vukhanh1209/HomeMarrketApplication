package com.example.homemarket.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class BaseResponse {
    @NonNull
    private Boolean success;

    @NonNull
    private String message;

    private List<ItemCheckResultDTO> itemCheckResults;
    public BaseResponse() {
        this.success = true;
        this.message = "";
    }
    public BaseResponse(boolean success, String message, List<ItemCheckResultDTO> itemCheckResults) {
        this.success = success;
        this.message = message;
        this.itemCheckResults = itemCheckResults;
    }
    public BaseResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
