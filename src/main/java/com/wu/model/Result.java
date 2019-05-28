package com.wu.model;/**
 * by wyz on 2019/5/27/027.
 */

import lombok.Data;

/**
 * @program: buildss
 *
 * @description:
 *
 * @author: Mr.Wu
 *
 * @create: 2019-05-27 19:27
 **/
@Data
public class Result {

    private int status;

    private String message;

    private Object data;

    public Result(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static Result build(int status,String message , Object data){
        return new Result(status,message,data);
    }
}
