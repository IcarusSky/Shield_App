package com.crec.shield.entity.login;


import android.os.Parcel;
import android.os.Parcelable;

import com.crec.shield.entity.BaseResponse;

/**
 * Created by diaokaibin@gmail.com on 2017/7/18.
 */

public class LoginResponse extends BaseResponse implements Parcelable {

    private Integer code;
    private DataLogin data;

    protected LoginResponse(Parcel in) {
        data = in.readParcelable(DataLogin.class.getClassLoader());
    }

    public static final Creator<LoginResponse> CREATOR = new Creator<LoginResponse>() {
        @Override
        public LoginResponse createFromParcel(Parcel in) {
            return new LoginResponse(in);
        }

        @Override
        public LoginResponse[] newArray(int size) {
            return new LoginResponse[size];
        }
    };

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public DataLogin getData() {
        return data;
    }

    public void setData(DataLogin data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "data=" + data +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.data, flags);
    }

}
