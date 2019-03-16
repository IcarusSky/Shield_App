package com.crec.shield.entity.login;



import android.os.Parcel;
import android.os.Parcelable;


public class DataLogin implements Parcelable {


    private String token;
    private String code;  // 编码
    private String username;  // 用户名
    private String project_id;  // 公司/项目编码
    private Integer type;  // 所属类型
    private String project_name;  //公司名称
    private String company_id;  //公司名称
    private String company_name;  //公司名称


    protected DataLogin(Parcel in) {
        code = in.readString();
        username = in.readString();
        project_id = in.readString();
        type = in.readInt();
        token = in.readString();
        company_id = in.readString();
        company_name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(code);
        dest.writeString(username);
        dest.writeString(project_id);
        dest.writeInt(type);
        dest.writeString(token);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataLogin> CREATOR = new Creator<DataLogin>() {
        @Override
        public DataLogin createFromParcel(Parcel in) {
            return new DataLogin(in);
        }

        @Override
        public DataLogin[] newArray(int size) {
            return new DataLogin[size];
        }
    };


    public String getToken() {return token;}

    public void setToken(String token) {this.token = token;}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
}

