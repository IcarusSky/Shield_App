package com.crec.shield.entity.project.camera;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/22.
 */

public class Camera implements Serializable {
    private Integer isAttention;
    private String id;
    private String ip;
    private Integer port;
    private String username;
    private String password;
    private String channel;
    private String location;
    private String channelname;
    private int mlogId;
    private int logIdSign;

    public int getLogIdSign() {
        return logIdSign;
    }

    public void setLogIdSign(int logIdSign) {
        this.logIdSign = logIdSign;
    }

    public int getMlogId() {
        return mlogId;
    }

    public void setMlogId(int mlogId) {
        this.mlogId = mlogId;
    }

    public Integer getIsAttention() {
        return isAttention;
    }

    public void setIsAttention(Integer isAttention) {
        this.isAttention = isAttention;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getChannelname() {
        return channelname;
    }

    public void setChannelname(String channelname) {
        this.channelname = channelname;
    }
}
