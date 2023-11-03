package com.example.learning_spring.dtos;

import com.example.learning_spring.configuration.Constants;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.yaml.snakeyaml.scanner.Constant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    private String message;
    private Integer code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer page;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer size;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long total;

    public BaseResponse(String message, int code) {
        this.message = message;
        this.code = code;
    }

    //  Generate success code and success message
    public void success() {
        this.message = Constants.ResponseMessage.SUCCESS;
        this.code = HttpStatus.OK.value();
    }


}
