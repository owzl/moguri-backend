package org.moguri.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.moguri.common.enums.ReturnCode;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private String returnCode;
    private String returnMessage;
    private T data;

    public static <T> ApiResponse of(T data) {
        ApiResponse<T> response = new ApiResponse<>();
        response.returnCode = ReturnCode.SUCCESS.getReturnCode();
        response.returnMessage = ReturnCode.SUCCESS.getReturnMessage();
        response.data = data;

        return response;
    }

    public static <T> ApiResponse of(ReturnCode returnCode) {
        ApiResponse<T> response = new ApiResponse<>();
        response.returnCode = returnCode.getReturnCode();
        response.returnMessage = returnCode.getReturnMessage();

        return response;
    }
}

