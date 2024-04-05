package org.example.dianping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dianping.utils.ResultCode;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private Boolean success;
    private int code;
    private String errorMsg;
    private Object data;
    private Long total;

    public static Result ok(){

        return new Result(true, ResultCode.SUCCESS, null, null, null);
    }
    public static Result ok(Object data){
        return new Result(true,ResultCode.SUCCESS, null, data, null);
    }
    public static Result ok(List<?> data, Long total){
        return new Result(true, ResultCode.SUCCESS,null, data, total);
    }
    public static Result fail(String errorMsg){
        return new Result(false,ResultCode.ERROR, errorMsg, null, null);
    }
}
