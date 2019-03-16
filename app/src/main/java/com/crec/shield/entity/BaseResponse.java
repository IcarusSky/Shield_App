package com.crec.shield.entity;

/**
 * Created by diaokaibin@gmail.com on 2017/7/17.
 */

public class BaseResponse<T>  {

    private boolean success;
//    private String message;
//    private T data;

    public boolean getStatus() {
        return success;
    }

    public void setStatus(boolean status) {
        this.success = status;
    }

//    public String getMessage() {
//        return message;
////    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }

//    public T getData() {
//        return data;
//    }
//
//    public void setData(T data) {
//        this.data = data;
//    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "success='" + success + '\'' +
//                ", data=" + data +
                '}';
    }
}
